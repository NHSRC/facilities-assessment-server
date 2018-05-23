package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.CheckpointScoreDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;
import org.nhsrc.referenceDataImport.ExcelImporter;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.CheckpointRepository;
import org.nhsrc.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelImportService {
    private AssessmentToolRepository assessmentToolRepository;
    private DepartmentRepository departmentRepository;
    private ChecklistRepository checklistRepository;
    private CheckpointRepository checkpointRepository;
    private static Logger logger = LoggerFactory.getLogger(ExcelImportService.class);

    @Autowired
    public ExcelImportService(AssessmentToolRepository assessmentToolRepository, DepartmentRepository departmentRepository, ChecklistRepository checklistRepository, CheckpointRepository checkpointRepository) {
        this.assessmentToolRepository = assessmentToolRepository;
        this.departmentRepository = departmentRepository;
        this.checklistRepository = checklistRepository;
        this.checkpointRepository = checkpointRepository;
    }

    public void saveAssessment(InputStream inputStream, FacilityAssessmentDTO facilityAssessmentDTO, FacilityAssessment facilityAssessment) throws Exception {
        AssessmentChecklistData assessmentChecklistData = new AssessmentChecklistData();
        ExcelImporter excelImporter = new ExcelImporter(assessmentChecklistData);
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessmentDTO.getAssessmentTool());
        excelImporter.importFile(inputStream, assessmentTool, 0, 1, true, facilityAssessment);

        List<CheckpointScore> checkpointScores = assessmentChecklistData.getCheckpointScores();
        ChecklistDTO checklistDTO = new ChecklistDTO();
        checklistDTO.setFacilityAssessment(facilityAssessment.getUuid());
        checklistDTO.setDepartment(departmentRepository.findByName(assessmentChecklistData.getDepartments().get(0).getName()).getUuid());
        Checklist checklist = checklistRepository.findByNameAndAssessmentToolUuid(assessmentChecklistData.getChecklists().get(0).getName(), assessmentTool.getUuid());
        checklistDTO.setUuid(checklist.getUuid());
        checklistDTO.setCheckpointScores(new ArrayList<>());
        checkpointScores.forEach(checkpointScore -> {
            CheckpointScoreDTO checkpointScoreDTO = new CheckpointScoreDTO();
            String checkpointName = checkpointScore.getCheckpoint().getName();
            String measurableElementName = checkpointScore.getCheckpoint().getMeasurableElement().getName();
            List<Checkpoint> checkpoints = checkpointRepository.findAllByNameAndChecklistUuidAndMeasurableElementName(checkpointName, checklist.getUuid(), measurableElementName);
            if (checkpoints.size() != 1) {
                logger.error(String.format("%d checkpoints with same name=%s, found in checklist:%s, for ME:%s", checkpoints.size(), checkpointName, checklist.getUuid(), measurableElementName));
            }
            else {
                checkpointScoreDTO.setCheckpoint(checkpoints.get(0).getUuid());
                checkpointScoreDTO.setNa(false);
                checkpointScoreDTO.setRemarks(checkpointScore.getRemarks());
                checkpointScoreDTO.setScore(checkpointScore.getScore());
                checkpointScoreDTO.setUuid(UUID.randomUUID());
                checklistDTO.addCheckpointScore(checkpointScoreDTO);
            }
        });
    }
}