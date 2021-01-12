package org.nhsrc.service;

import com.bugsnag.Bugsnag;
import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.AssessmentCustomInfo;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.domain.metadata.AssessmentMetaData;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.assessment.BaseFacilityAssessmentDTO;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.mapper.FacilityAssessmentMapper;
import org.nhsrc.repository.*;
import org.nhsrc.repository.metadata.AssessmentMetaDataRepository;
import org.nhsrc.repository.missing.FacilityAssessmentMissingCheckpointRepository;
import org.nhsrc.repository.scores.AreaOfConcernScoreRepository;
import org.nhsrc.repository.scores.ChecklistScoreRepository;
import org.nhsrc.repository.scores.StandardScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FacilityAssessmentService {
    private final FacilityRepository facilityRepository;
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
    private StandardScoreRepository standardScoreRepository;
    private AreaOfConcernScoreRepository areaOfConcernScoreRepository;
    private ChecklistScoreRepository checklistScoreRepository;
    private FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository;
    private AssessmentMetaDataRepository assessmentMetaDataRepository;
    private Bugsnag bugsnag;

    @Autowired
    public FacilityAssessmentService(FacilityRepository facilityRepository,
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
                                     FacilityTypeRepository facilityTypeRepository, StandardScoreRepository standardScoreRepository, AreaOfConcernScoreRepository areaOfConcernScoreRepository, ChecklistScoreRepository checklistScoreRepository, FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository, AssessmentMetaDataRepository assessmentMetaDataRepository, Bugsnag bugsnag) {
        this.facilityRepository = facilityRepository;
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
        this.standardScoreRepository = standardScoreRepository;
        this.areaOfConcernScoreRepository = areaOfConcernScoreRepository;
        this.checklistScoreRepository = checklistScoreRepository;
        this.facilityAssessmentMissingCheckpointRepository = facilityAssessmentMissingCheckpointRepository;
        this.assessmentMetaDataRepository = assessmentMetaDataRepository;
        this.bugsnag = bugsnag;
    }

    public FacilityAssessment save(BaseFacilityAssessmentDTO facilityAssessmentDTO, AssessmentTool assessmentTool, User user) {
        Facility facility = Repository.findByUuidOrId(facilityAssessmentDTO.getFacility(), facilityAssessmentDTO.getFacilityId(), facilityRepository);
        if (facility == null && (facilityAssessmentDTO.getFacilityName() == null || facilityAssessmentDTO.getFacilityName().isEmpty()))
            throw new ValidationException("Facility not found and facility name is also empty");

        AssessmentType assessmentType = Repository.findByUuidOrId(facilityAssessmentDTO.getAssessmentTypeUUID(), facilityAssessmentDTO.getAssessmentTypeId(), assessmentTypeRepository);
        FacilityType facilityType = Repository.findByUuidOrId(facilityAssessmentDTO.getFacilityTypeUUID(), facilityAssessmentDTO.getFacilityTypeId(), facilityTypeRepository);

        State state;
        District district;
        if (facilityAssessmentDTO.getStateId() == 0 && facility != null) {
            state = facility.getDistrict().getState();
            district = facility.getDistrict();
        } else {
            state = Repository.findByUuidOrId(facilityAssessmentDTO.getState(), facilityAssessmentDTO.getStateId(), stateRepository);
            district = Repository.findByUuidOrId(facilityAssessmentDTO.getDistrict(), facilityAssessmentDTO.getDistrictId(), districtRepository);
        }

        String lockString = String.format("%s-%d", facility == null ? facilityAssessmentDTO.getFacilityName() : facility.getId(), assessmentTool.getId());
        synchronized (lockString.intern()) {
            FacilityAssessment facilityAssessment = this.assessmentMatchingService.findExistingAssessment(facilityAssessmentDTO.getSeriesName(), facilityAssessmentDTO.getUuid(), facility, facilityAssessmentDTO.getFacilityName(), assessmentTool);
            if (facilityAssessment == null)
                facilityAssessment = FacilityAssessmentMapper.fromDTO(facilityAssessmentDTO);
            else
                facilityAssessment.updateEndDate(facilityAssessmentDTO.getEndDate());

            facilityAssessment.setFacility(facility);
            facilityAssessment.setState(state);
            facilityAssessment.setDistrict(district);
            facilityAssessment.setFacilityType(facilityType);
            facilityAssessment.setFacilityName(facilityAssessmentDTO.getFacilityName());

            facilityAssessment.setAssessmentTool(assessmentTool);
            facilityAssessment.setStartDate(facilityAssessmentDTO.getStartDate());
            facilityAssessment.setSeriesName(facilityAssessmentDTO.getSeriesName());
            if (facilityAssessmentDTO.getAssessorName() != null && !facilityAssessmentDTO.getAssessorName().isEmpty()) {
                AssessmentMetaData assessmentMetaData = assessmentMetaDataRepository.findByName("Assessor name");
                facilityAssessment.addCustomInfo(assessmentMetaData, facilityAssessmentDTO.getAssessorName(), facilityAssessmentDTO.getDeviceId());
            }
            if (facilityAssessmentDTO.getCustomInfos() != null && facilityAssessmentDTO.getCustomInfos().size() > 1) {
                FacilityAssessment finalFacilityAssessment = facilityAssessment;
                facilityAssessmentDTO.getCustomInfos().forEach(assessmentCustomInfoDTO -> {
                    AssessmentMetaData assessmentMetaData = assessmentMetaDataRepository.findByUuid(UUID.fromString(assessmentCustomInfoDTO.getUuid()));
                    if (assessmentMetaData == null) assessmentMetaData = assessmentMetaDataRepository.findByName(assessmentCustomInfoDTO.getName());
                    finalFacilityAssessment.addCustomInfo(assessmentMetaData, assessmentCustomInfoDTO.getValueString(), facilityAssessmentDTO.getDeviceId());
                });
            }
            facilityAssessment.setAssessmentType(assessmentType);
            facilityAssessment.setInactive(facilityAssessmentDTO.getInactive());

            facilityAssessment.incorporateDevice(facilityAssessmentDTO.getDeviceId());
            facilityAssessment.setUser(user);
            if (facilityAssessment.getAssessmentCode() == null) {
                facilityAssessment.setupCode();
            }
            if (facilityAssessmentDTO.getUuid() == null) {
                facilityAssessment.setUuid(UUID.randomUUID());
            }
            return facilityAssessmentRepository.save(facilityAssessment);
        }
    }

    public List<CheckpointScore> saveChecklist(ChecklistDTO checklistDTO) {
        synchronized (checklistDTO.getFacilityAssessment().toString().intern()) {
            Checklist checklist = checklistRepository.findByUuid(checklistDTO.getUuid());
            FacilityAssessment facilityAssessment = facilityAssessmentRepository.findByUuid(checklistDTO.getFacilityAssessment());
            this.clearCheckpointScores(facilityAssessment.getId(), checklist.getName());
            List<CheckpointScore> checkpointScores = new ArrayList<>();
            checklistDTO.getCheckpointScores().forEach(checkpointScoreDTO -> {
                Checkpoint checkpoint = checkpointRepository.findByUuid(checkpointScoreDTO.getCheckpoint());
                if (checkpoint == null) {
                    bugsnag.notify(new Exception(String.format("Couldn't find the checkpoint=%s in the request. Continuing", checkpointScoreDTO.getCheckpoint())));
                } else {
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
                }
            });
            return checkpointScores;
        }
    }

    public void saveIndicatorList(IndicatorListDTO indicatorListDTO) {
        synchronized (indicatorListDTO.getFacilityAssessment().toString().intern()) {
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
    }

    public void clearCheckpointScores(int facilityAssessmentId, String checklistName) {
        List<CheckpointScore> checkpointScores = checkpointScoreRepository.findByFacilityAssessmentIdAndChecklistName(facilityAssessmentId, checklistName);
        checkpointScoreRepository.delete(checkpointScores);
    }

    public void deleteAssessment(Integer facilityAssessmentId) {
        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findOne(facilityAssessmentId);
        checkpointScoreRepository.deleteAllByFacilityAssessmentId(facilityAssessmentId);
        standardScoreRepository.deleteAllByFacilityAssessmentId(facilityAssessmentId);
        areaOfConcernScoreRepository.deleteAllByFacilityAssessmentId(facilityAssessmentId);
        checklistScoreRepository.deleteAllByFacilityAssessmentId(facilityAssessmentId);
        indicatorRepository.deleteAllByFacilityAssessmentId(facilityAssessmentId);
        facilityAssessmentMissingCheckpointRepository.deleteAllByFacilityAssessment(facilityAssessment);
        facilityAssessmentRepository.delete(facilityAssessmentId);
    }
}