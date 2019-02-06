package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.CheckpointScoreDTO;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;
import org.nhsrc.referenceDataImport.ExcelImporter;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.CheckpointRepository;
import org.nhsrc.repository.DepartmentRepository;
import org.nhsrc.web.contract.FacilityAssessmentImportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private DepartmentRepository departmentRepository;
    private CheckpointRepository checkpointRepository;
    private FacilityAssessmentService facilityAssessmentService;
    private static Logger logger = LoggerFactory.getLogger(ExcelImportService.class);

    @Autowired
    public ExcelImportService(AssessmentToolRepository assessmentToolRepository, DepartmentRepository departmentRepository, CheckpointRepository checkpointRepository, FacilityAssessmentService facilityAssessmentService) {
        this.assessmentToolRepository = assessmentToolRepository;
        this.departmentRepository = departmentRepository;
        this.checkpointRepository = checkpointRepository;
        this.facilityAssessmentService = facilityAssessmentService;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveAssessment(InputStream inputStream, FacilityAssessment facilityAssessment, FacilityAssessmentImportResponse facilityAssessmentImportResponse) throws Exception {
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        ExcelImporter excelImporter = new ExcelImporter(assessmentChecklistData);
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessment.getAssessmentTool().getUuid());
        excelImporter.importFile(inputStream, assessmentTool, 0, -1, true, facilityAssessment);

        List<Checklist> checklists = assessmentChecklistData.getChecklists();
        checklists.forEach(checklist -> {
            ChecklistDTO checklistDTO = new ChecklistDTO();
            checklistDTO.setFacilityAssessment(facilityAssessment.getUuid());
            checklistDTO.setDepartment(departmentRepository.findByName(assessmentChecklistData.getDepartments().get(0).getName()).getUuid());
            checklistDTO.setUuid(checklist.getUuid());
            checklistDTO.setCheckpointScores(new ArrayList<>());
            List<CheckpointScore> checkpointScores = assessmentChecklistData.getCheckpointScores(checklist.getName());
            checkpointScores.forEach(checkpointScore -> {
                CheckpointScoreDTO checkpointScoreDTO = new CheckpointScoreDTO();
                String checkpointName = checkpointScore.getCheckpoint().getName();
                String measurableElementReference = checkpointScore.getCheckpoint().getMeasurableElement().getReference();
                List<Checkpoint> checkpoints = checkpointRepository.findAllDistinctByNameAndChecklistUuidAndMeasurableElementReference(checkpointName, checklist.getUuid(), measurableElementReference);
                if (checkpoints.size() == 0) { // additionally try to resolve by looking for checkpoint in Standard
                    String standardRef = checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getReference();
                    checkpoints = checkpointRepository.findAllDistinctByNameAndChecklistUuidAndMeasurableElementStandardReference(checkpointName, checklist.getUuid(), standardRef);
                }

                if (checkpoints.size() == 1) {
                    checkpointScoreDTO.setCheckpoint(checkpoints.get(0).getUuid());
                    checkpointScoreDTO.setNa(false);
                    checkpointScoreDTO.setRemarks(checkpointScore.getRemarks());
                    checkpointScoreDTO.setScore(checkpointScore.getScore());
                    checkpointScoreDTO.setUuid(UUID.randomUUID());
                    checklistDTO.addCheckpointScore(checkpointScoreDTO);
                }
                else {
                    facilityAssessmentImportResponse.addCheckpointInError(new FacilityAssessmentImportResponse.CheckpointInError(checkpointName, measurableElementReference));
                    logger.error(String.format("ME:%s. %d checkpoints with same name=%s, found in checklist:%s", measurableElementReference, checkpoints.size(), checkpointName, checklist.getName()));
                }
            });
            facilityAssessmentService.saveChecklist(checklistDTO);
        });
    }
}
