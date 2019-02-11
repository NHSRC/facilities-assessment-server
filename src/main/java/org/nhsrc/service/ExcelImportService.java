package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.CheckpointScoreDTO;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;
import org.nhsrc.referenceDataImport.ExcelImporter;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelImportService {
    private AssessmentToolRepository assessmentToolRepository;
    private CheckpointRepository checkpointRepository;
    private FacilityAssessmentService facilityAssessmentService;
    private ChecklistRepository checklistRepository;
    private MissingCheckpointService missingCheckpointService;

    @Autowired
    public ExcelImportService(AssessmentToolRepository assessmentToolRepository, CheckpointRepository checkpointRepository, FacilityAssessmentService facilityAssessmentService, ChecklistRepository checklistRepository, MissingCheckpointService missingCheckpointService) {
        this.assessmentToolRepository = assessmentToolRepository;
        this.checkpointRepository = checkpointRepository;
        this.facilityAssessmentService = facilityAssessmentService;
        this.checklistRepository = checklistRepository;
        this.missingCheckpointService = missingCheckpointService;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveAssessment(InputStream inputStream, FacilityAssessment facilityAssessment) throws Exception {
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        ExcelImporter excelImporter = new ExcelImporter(assessmentChecklistData);
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessment.getAssessmentTool().getUuid());
        excelImporter.importFile(inputStream, assessmentTool, 0, -1, true, facilityAssessment);

        List<Checklist> checklists = assessmentChecklistData.getChecklists();
        checklists.forEach(x -> {
            Checklist checklist = checklistRepository.findByNameAndAssessmentTool(x.getName(), assessmentTool);
            missingCheckpointService.clearMissingCheckpoints(facilityAssessment, checklist);
            if (checklist != null) {
                ChecklistDTO checklistDTO = new ChecklistDTO();
                checklistDTO.setFacilityAssessment(facilityAssessment.getUuid());
                checklistDTO.setUuid(checklist.getUuid());
                checklistDTO.setDepartment(checklist.getDepartment().getUuid());
                checklistDTO.setCheckpointScores(new ArrayList<>());
                List<CheckpointScore> checkpointScores = assessmentChecklistData.getCheckpointScores(x.getName());
                checkpointScores.forEach(checkpointScore -> {
                    CheckpointScoreDTO checkpointScoreDTO = new CheckpointScoreDTO();
                    String checkpointName = checkpointScore.getCheckpoint().getName();
                    String measurableElementReference = checkpointScore.getCheckpoint().getMeasurableElement().getReference();
                    List<Checkpoint> foundCheckpoints = checkpointRepository.findAllDistinctByNameAndChecklistUuidAndMeasurableElementReference(checkpointName, x.getUuid(), measurableElementReference);
                    if (foundCheckpoints.size() == 0) { // additionally try to resolve by looking for checkpoint in Standard
                        String standardRef = checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getReference();
                        foundCheckpoints = checkpointRepository.findAllDistinctByNameAndChecklistUuidAndMeasurableElementStandardReference(checkpointName, x.getUuid(), standardRef);
                    }

                    if (foundCheckpoints.size() == 1) {
                        checkpointScoreDTO.setCheckpoint(foundCheckpoints.get(0).getUuid());
                        checkpointScoreDTO.setNa(false);
                        checkpointScoreDTO.setRemarks(checkpointScore.getRemarks());
                        checkpointScoreDTO.setScore(checkpointScore.getScore());
                        checkpointScoreDTO.setUuid(UUID.randomUUID());
                        checklistDTO.addCheckpointScore(checkpointScoreDTO);
                    } else {
                        missingCheckpointService.saveMissingCheckpoint(checkpointName, checklist, facilityAssessment);
                    }
                });
                facilityAssessmentService.saveChecklist(checklistDTO);
            }
        });
    }
}