package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.mapper.FacilityAssessmentMapper;
import org.nhsrc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FacilityAssessmentService {
    private final FacilityRepository facilityRepository;
    private final AssessmentToolRepository assessmentToolRepository;
    private final FacilityAssessmentRepository facilityAssessmentRepository;
    private final ChecklistRepository checklistRepository;
    private final CheckpointScoreRepository checkpointScoreRepository;
    private final CheckpointRepository checkpointRepository;
    private final AssessmentMatchingService assessmentMatchingService;
    private AssessmentTypeRepository assessmentTypeRepository;

    @Autowired
    public FacilityAssessmentService(FacilityRepository facilityRepository,
                                     AssessmentToolRepository assessmentToolRepository,
                                     FacilityAssessmentRepository facilityAssessmentRepository,
                                     ChecklistRepository checklistRepository,
                                     CheckpointScoreRepository checkpointScoreRepository,
                                     CheckpointRepository checkpointRepository,
                                     AssessmentMatchingService assessmentMatchingService,
                                     AssessmentTypeRepository assessmentTypeRepository) {
        this.facilityRepository = facilityRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.checklistRepository = checklistRepository;
        this.checkpointScoreRepository = checkpointScoreRepository;
        this.checkpointRepository = checkpointRepository;
        this.assessmentMatchingService = assessmentMatchingService;
        this.assessmentTypeRepository = assessmentTypeRepository;
    }

    public FacilityAssessment save(FacilityAssessmentDTO facilityAssessmentDTO) {
        Facility facility = facilityRepository.findByUuid(facilityAssessmentDTO.getFacility());
        if (facility == null && (facilityAssessmentDTO.getFacilityName() == null || facilityAssessmentDTO.getFacilityName().isEmpty()))
            throw new ValidationException("Facility not found and facility name is also empty");

        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessmentDTO.getAssessmentTool());
        AssessmentType assessmentType = assessmentTypeRepository.findByUuid(facilityAssessmentDTO.getAssessmentTypeUUID());

        FacilityAssessment facilityAssessment = this.assessmentMatchingService.findExistingAssessment(facilityAssessmentDTO.getSeriesName(), facilityAssessmentDTO.getUuid(), facility, facilityAssessmentDTO.getFacilityName(), assessmentTool);
        if (facilityAssessment == null)
            facilityAssessment = FacilityAssessmentMapper.fromDTO(facilityAssessmentDTO, facility, assessmentTool, assessmentType);
        else
            facilityAssessment.updateEndDate(facilityAssessmentDTO.getEndDate());

        facilityAssessment.incorporateDevice(facilityAssessmentDTO.getDeviceId());
        if (facilityAssessment.getAssessmentCode() == null) {
            facilityAssessment.setupCode();
        }
        return facilityAssessmentRepository.save(facilityAssessment);
    }

    public List<CheckpointScore> saveChecklist(ChecklistDTO checklistDTO) {
        Checklist checklist = checklistRepository.findByUuid(checklistDTO.getUuid());
        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findByUuid(checklistDTO.getFacilityAssessment());
        List<CheckpointScore> checkpointScores = new ArrayList<>();
        checklistDTO.getCheckpointScores().forEach(checkpointScoreDTO -> {
            Checkpoint checkpoint = checkpointRepository.findByUuid(checkpointScoreDTO.getCheckpoint());
            CheckpointScore checkpointScore = checkpointScoreRepository.findByUuid(checkpointScoreDTO.getUuid());
            if (checkpointScore == null)
                checkpointScore = checkpointScoreRepository.findByCheckpointAndFacilityAssessmentAndChecklist(checkpoint, facilityAssessment, checklist);

            if (checkpointScore == null) {
                checkpointScore = new CheckpointScore();
                checkpointScore.setFacilityAssessment(facilityAssessment);
                checkpointScore.setChecklist(checklist);
                checkpointScore.setCheckpoint(checkpoint);
                checkpointScore.setUuid(checkpointScoreDTO.getUuid());
            }
            checkpointScore.setScore(checkpointScoreDTO.getScore());
            checkpointScore.setNa(checkpointScoreDTO.getNa());
            checkpointScore.setRemarks(checkpointScoreDTO.getRemarks());
            checkpointScores.add(checkpointScoreRepository.save(checkpointScore));
        });
        return checkpointScores;
    }
}