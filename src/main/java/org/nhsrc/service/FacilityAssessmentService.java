package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.CheckpointScoreDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.mapper.FacilityAssessmentMapper;
import org.nhsrc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    public FacilityAssessmentService(FacilityRepository facilityRepository,
                                     AssessmentToolRepository assessmentToolRepository,
                                     FacilityAssessmentRepository facilityAssessmentRepository,
                                     ChecklistRepository checklistRepository,
                                     CheckpointScoreRepository checkpointScoreRepository,
                                     CheckpointRepository checkpointRepository,
                                     AssessmentMatchingService assessmentMatchingService) {
        this.facilityRepository = facilityRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.checklistRepository = checklistRepository;
        this.checkpointScoreRepository = checkpointScoreRepository;
        this.checkpointRepository = checkpointRepository;
        this.assessmentMatchingService = assessmentMatchingService;
    }

    public FacilityAssessment save(FacilityAssessmentDTO facilityAssessmentDTO) {
        Facility facility = facilityRepository.findByUuid(facilityAssessmentDTO.getFacility());
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessmentDTO.getAssessmentTool());
        FacilityAssessment facilityAssessment = FacilityAssessmentMapper.fromDTO(facilityAssessmentDTO, facility, assessmentTool);
        FacilityAssessment matchingAssessment = this.assessmentMatchingService.findMatching(facilityAssessment);
        return facilityAssessmentRepository.save(matchingAssessment);
    }

    public List<CheckpointScore> saveChecklist(ChecklistDTO checklistDTO) {
        Checklist checklist = checklistRepository.findByUuid(checklistDTO.getUuid());
        FacilityAssessment facilityAssessment = facilityAssessmentRepository
                .findByUuid(checklistDTO.getFacilityAssessment());
        Function<? super CheckpointScoreDTO, CheckpointScore> saveCheckpointScore =
                this.saveCheckpointScoreFactory(checklist, facilityAssessment);
        return checklistDTO.getCheckpointScores()
                .parallelStream()
                .map(saveCheckpointScore)
                .collect(Collectors.toList());
    }

    private Function<? super CheckpointScoreDTO, CheckpointScore> saveCheckpointScoreFactory(
            Checklist checklist,
            FacilityAssessment facilityAssessment) {
        return (checkpointScoreDTO) -> {
            Checkpoint checkpoint = checkpointRepository.findByUuid(checkpointScoreDTO.getCheckpoint());
            CheckpointScore checkpointScore = new CheckpointScore(
                    facilityAssessment,
                    checklist,
                    checkpoint,
                    checkpointScoreDTO.getScore(),
                    checkpointScoreDTO.getRemarks(),
                    checkpointScoreDTO.getUuid());
            return checkpointScoreRepository.save(checkpointScore);
        };
    }
}
