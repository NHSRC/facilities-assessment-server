package org.nhsrc.service;

import org.nhsrc.domain.*;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.BaseFacilityAssessmentDTO;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.IndicatorListDTO;
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
    private IndicatorDefinitionRepository indicatorDefinitionRepository;
    private IndicatorRepository indicatorRepository;
    private StateRepository stateRepository;
    private DistrictRepository districtRepository;
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    public FacilityAssessmentService(FacilityRepository facilityRepository,
                                     AssessmentToolRepository assessmentToolRepository,
                                     FacilityAssessmentRepository facilityAssessmentRepository,
                                     ChecklistRepository checklistRepository,
                                     CheckpointScoreRepository checkpointScoreRepository,
                                     CheckpointRepository checkpointRepository,
                                     AssessmentMatchingService assessmentMatchingService,
                                     AssessmentTypeRepository assessmentTypeRepository,
                                     IndicatorDefinitionRepository indicatorDefinitionRepository,
                                     IndicatorRepository indicatorRepository,
                                     StateRepository stateRepository,
                                     DistrictRepository districtRepository,
                                     FacilityTypeRepository facilityTypeRepository) {
        this.facilityRepository = facilityRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.checklistRepository = checklistRepository;
        this.checkpointScoreRepository = checkpointScoreRepository;
        this.checkpointRepository = checkpointRepository;
        this.assessmentMatchingService = assessmentMatchingService;
        this.assessmentTypeRepository = assessmentTypeRepository;
        this.indicatorDefinitionRepository = indicatorDefinitionRepository;
        this.indicatorRepository = indicatorRepository;
        this.stateRepository = stateRepository;
        this.districtRepository = districtRepository;
        this.facilityTypeRepository = facilityTypeRepository;
    }

    public FacilityAssessment save(BaseFacilityAssessmentDTO facilityAssessmentDTO, User user) {
        Facility facility = Repository.findByUuidOrId(facilityAssessmentDTO.getFacility(), facilityAssessmentDTO.getFacilityId(), facilityRepository);
        if (facility == null && (facilityAssessmentDTO.getFacilityName() == null || facilityAssessmentDTO.getFacilityName().isEmpty()))
            throw new ValidationException("Facility not found and facility name is also empty");

        AssessmentTool assessmentTool = Repository.findByUuidOrId(facilityAssessmentDTO.getAssessmentTool(), facilityAssessmentDTO.getAssessmentToolId(), assessmentToolRepository);
        AssessmentType assessmentType = Repository.findByUuidOrId(facilityAssessmentDTO.getAssessmentTypeUUID(), facilityAssessmentDTO.getAssessmentToolId(), assessmentTypeRepository);

        State state;
        District district;
        if (facilityAssessmentDTO.getStateId() == 0 && facility != null) {
            state = facility.getDistrict().getState();
            district = facility.getDistrict();
        } else {
            state = Repository.findByUuidOrId(facilityAssessmentDTO.getState(), facilityAssessmentDTO.getStateId(), stateRepository);
            district = Repository.findByUuidOrId(facilityAssessmentDTO.getDistrict(), facilityAssessmentDTO.getDistrictId(), districtRepository);
        }

        FacilityType facilityType = Repository.findByUuidOrId(facilityAssessmentDTO.getFacilityTypeUUID(), facilityAssessmentDTO.getFacilityId(), facilityTypeRepository);

        String lockString = String.format("%s-%d", facility == null ? facilityAssessmentDTO.getFacilityName() : facility.getId(), assessmentTool.getId());
        synchronized (lockString.intern()) {
            FacilityAssessment facilityAssessment = this.assessmentMatchingService.findExistingAssessment(facilityAssessmentDTO.getSeriesName(), facilityAssessmentDTO.getUuid(), facility, facilityAssessmentDTO.getFacilityName(), assessmentTool);
            if (facilityAssessment == null)
                facilityAssessment = FacilityAssessmentMapper.fromDTO(facilityAssessmentDTO, facility, assessmentTool, assessmentType, state, district, facilityType);
            else
                facilityAssessment.updateEndDate(facilityAssessmentDTO.getEndDate());

            facilityAssessment.incorporateDevice(facilityAssessmentDTO.getDeviceId());
            facilityAssessment.setUser(user);
            if (facilityAssessment.getAssessmentCode() == null) {
                facilityAssessment.setupCode();
            }
            return facilityAssessmentRepository.save(facilityAssessment);
        }
    }

    public List<CheckpointScore> saveChecklist(ChecklistDTO checklistDTO) {
        Checklist checklist = checklistRepository.findByUuid(checklistDTO.getUuid());
        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findByUuid(checklistDTO.getFacilityAssessment());
        this.clearCheckpointScores(facilityAssessment.getId(), checklist.getName());
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
        checkpointScores.forEach(checkpointScore -> {
            checkpointScore.getScoreNumerator();
            checkpointScore.getScoreDenominator();
        });
        return checkpointScores;
    }

    public void saveIndicatorList(IndicatorListDTO indicatorListDTO) {
        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findByUuid(indicatorListDTO.getFacilityAssessment());
        List<Indicator> indicators = new ArrayList<>();
        indicatorListDTO.getIndicators().forEach(indicatorDTO -> {
            Indicator indicator = indicatorRepository.findByUuid(indicatorDTO.getUuid());
            IndicatorDefinition indicatorDefinition = indicatorDefinitionRepository.findByUuid(indicatorDTO.getIndicatorDefinition());
            if (indicator == null)
                indicator = indicatorRepository.findByIndicatorDefinitionAndFacilityAssessment(indicatorDefinition, facilityAssessment);

            if (indicator == null) {
                indicator = new Indicator();
                indicator.setFacilityAssessment(facilityAssessment);
                indicator.setIndicatorDefinition(indicatorDefinition);
                indicator.setUuid(indicatorDTO.getUuid());
            }
            if (indicatorDefinition.getDataType().equals(IndicatorDataType.Coded))
                indicator.setCodedValue(indicatorDTO.getCodedValue());
            else if (indicatorDefinition.getDataType().equals(IndicatorDataType.Date) || indicatorDefinition.getDataType().equals(IndicatorDataType.Month))
                indicator.setDateValue(indicatorDTO.getDateValue());
            else
                indicator.setNumericValue(indicatorDTO.getNumericValue());

            indicators.add(indicator);
        });
        indicatorRepository.save(indicators);
    }

    public void clearCheckpointScores(int facilityAssessmentId, String checklistName) {
        List<CheckpointScore> checkpointScores = checkpointScoreRepository.findByFacilityAssessmentIdAndChecklistName(facilityAssessmentId, checklistName);
        checkpointScoreRepository.delete(checkpointScores);
    }
}