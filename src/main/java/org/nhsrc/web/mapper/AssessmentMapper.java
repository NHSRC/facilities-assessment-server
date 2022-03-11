package org.nhsrc.web.mapper;

import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.domain.scores.AreaOfConcernScore;
import org.nhsrc.domain.scores.StandardScore;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.CheckpointScoreRepository;
import org.nhsrc.repository.IndicatorRepository;
import org.nhsrc.repository.scores.AreaOfConcernScoreRepository;
import org.nhsrc.repository.scores.ChecklistScoreRepository;
import org.nhsrc.repository.scores.StandardScoreRepository;
import org.nhsrc.utils.CollectionUtil;
import org.nhsrc.web.contract.UserResponse;
import org.nhsrc.web.contract.assessment.AssessmentCustomInfoResponse;
import org.nhsrc.web.contract.assessment.FacilityAssessmentResponse;
import org.nhsrc.web.contract.ext.AssessmentResponse;
import org.nhsrc.web.contract.ext.AssessmentSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssessmentMapper {
    private final CheckpointScoreRepository checkpointScoreRepository;
    private final AreaOfConcernScoreRepository areaOfConcernScoreRepository;
    private final StandardScoreRepository standardScoreRepository;
    private final ChecklistScoreRepository checklistScoreRepository;
    private final IndicatorRepository indicatorRepository;

    @Autowired
    public AssessmentMapper(CheckpointScoreRepository checkpointScoreRepository, ChecklistScoreRepository checklistScoreRepository, AreaOfConcernScoreRepository areaOfConcernScoreRepository, StandardScoreRepository standardScoreRepository, IndicatorRepository indicatorRepository) {
        this.checkpointScoreRepository = checkpointScoreRepository;
        this.areaOfConcernScoreRepository = areaOfConcernScoreRepository;
        this.standardScoreRepository = standardScoreRepository;
        this.checklistScoreRepository = checklistScoreRepository;
        this.indicatorRepository = indicatorRepository;
    }

    public AssessmentSummaryResponse map(AssessmentSummaryResponse summary, FacilityAssessment source, AssessmentTool assessmentTool) {
        summary.setAssessmentEndDate(source.getEndDate());
        summary.setAssessmentSeries(source.getSeriesName());
        summary.setAssessmentStartDate(source.getStartDate());
        summary.setAssessmentTool(assessmentTool.getUuidString());
        summary.setAssessmentType(source.getAssessmentType().getUuidString());
        District district = source.getDistrict();
        summary.setDistrict(district == null ? "" : district.getName());
        State state = source.getState();
        summary.setState(state == null ? "" : state.getName());
        summary.setFacility(source.getEffectiveFacilityName());
        FacilityType facilityType = source.getFacilityType();
        summary.setFacilityType(facilityType == null ? "" : facilityType.getName());
        summary.setProgram(source.getAssessmentTool().getAssessmentToolMode().getUuidString());
        summary.setSystemId(source.getUuidString());
        summary.setFacilityNIN(source.getFacility() == null ? null : source.getFacility().getRegistryUniqueId());
        summary.setAssessmentNumber(source.getAssessmentNumber());
        summary.setInactive(source.getInactive());
        return summary;
    }

    public AssessmentResponse mapAssessmentScores(FacilityAssessment facilityAssessment, AssessmentResponse assessmentResponse) {
        List<CheckpointScore> scores = checkpointScoreRepository.findByFacilityAssessmentId(facilityAssessment.getId());
        scores.forEach(checkpointScore -> {
            Checklist checklist = checkpointScore.getCheckpoint().getChecklist();
            AssessmentResponse.ChecklistAssessment checklistAssessment = CollectionUtil.addIfNotExists(assessmentResponse.getChecklists(), x -> x.getSystemId().equals(checkpointScore.getCheckpoint().getChecklist().getUuidString()), new AssessmentResponse.ChecklistAssessment(checklist.getUuidString(), checkpointScore.getChecklist().getId()));
            AssessmentResponse.AreaOfConcernAssessment areaOfConcernAssessment = CollectionUtil.addIfNotExists(checklistAssessment.getAreaOfConcerns(), x -> x.getSystemId().equals(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern().getUuidString()), new AssessmentResponse.AreaOfConcernAssessment(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern().getUuidString()));
            AssessmentResponse.StandardAssessment standardAssessment = CollectionUtil.addIfNotExists(areaOfConcernAssessment.getStandards(), x -> x.getSystemId().equals(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getUuidString()), new AssessmentResponse.StandardAssessment(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getUuidString()));
            AssessmentResponse.MeasurableElementAssessment measurableElementAssessment = CollectionUtil.addIfNotExists(standardAssessment.getMeasurableElements(), x -> x.getSystemId().equals(checkpointScore.getCheckpoint().getMeasurableElement().getUuidString()), new AssessmentResponse.MeasurableElementAssessment(checkpointScore.getCheckpoint().getMeasurableElement().getUuidString()));

            AssessmentResponse.CheckpointAssessment checkpointAssessment = new AssessmentResponse.CheckpointAssessment();
            checkpointAssessment.setSystemId(checkpointScore.getCheckpoint().getUuidString());
            checkpointAssessment.setMarkedNotApplicable(checkpointScore.getNa());
            checkpointAssessment.setRemarks(checkpointScore.getRemarks());
            checkpointAssessment.setScore(checkpointScore.getScore());
            measurableElementAssessment.addCheckpointAssessment(checkpointAssessment);
        });
        assessmentResponse.getChecklists().forEach(checklistAssessment -> {
            checklistAssessment.setScore(checklistScoreRepository.getChecklistScore(checklistAssessment.getChecklistId(), facilityAssessment.getId()));
        });

        List<AreaOfConcernScore> aocScores = areaOfConcernScoreRepository.findAllByFacilityAssessmentOrderByAreaOfConcernReferenceAsc(facilityAssessment);
        aocScores.forEach(areaOfConcernScore -> {
            AreaOfConcern areaOfConcern = areaOfConcernScore.getAreaOfConcern();
            String aocReference = areaOfConcern.getReference();
            AssessmentResponse.AreaOfConcernAssessmentScore areaOfConcernAssessmentScore = assessmentResponse.addAreaOfConcernScore(areaOfConcern.getUuidString(), areaOfConcernScore.getScore());
            List<StandardScore> standardScores = standardScoreRepository.findAllByFacilityAssessmentAndStandardAreaOfConcernReferenceOrderByStandardAreaOfConcernReferenceAsc(facilityAssessment, aocReference);
            standardScores.forEach(standardScore -> areaOfConcernAssessmentScore.addStandardScore(standardScore.getStandard().getUuidString(), standardScore.getScore()));
        });

        assessmentResponse.updateCounts(scores);
        return assessmentResponse;
    }

    public AssessmentResponse mapIndicators(FacilityAssessment facilityAssessment, AssessmentResponse assessmentResponse) {
        List<Indicator> indicators = indicatorRepository.findByFacilityAssessmentIdOrderByIndicatorDefinitionSortOrder(facilityAssessment.getId());
        indicators.forEach(indicator -> {
            AssessmentResponse.IndicatorAssessment indicatorAssessment = new AssessmentResponse.IndicatorAssessment();
            indicatorAssessment.setSystemId(indicator.getUuidString());
            indicatorAssessment.setIndicatorDefinition(indicator.getIndicatorDefinition().getUuidString());
            indicatorAssessment.setValue(indicator.getValue());
            assessmentResponse.getIndicators().add(indicatorAssessment);
        });
        assessmentResponse.setNumberOfIndicators(indicators.size());
        return assessmentResponse;
    }

    public static FacilityAssessmentResponse map(FacilityAssessment facilityAssessment, List<String> filledChecklist) {
        FacilityAssessmentResponse facilityAssessmentResponse = new FacilityAssessmentResponse();
        facilityAssessmentResponse.setUuid(facilityAssessment.getUuidString());
        facilityAssessmentResponse.setCustomInfos(facilityAssessment.getCustomInfos().stream().map(assessmentCustomInfo -> new AssessmentCustomInfoResponse(assessmentCustomInfo.getAssessmentMetaData().getName(), assessmentCustomInfo.getValueString())).collect(Collectors.toList()));
        facilityAssessmentResponse.setDepartmentsAssessed(filledChecklist);
        facilityAssessmentResponse.setAssessmentEndDate(facilityAssessment.getEndDate());
        facilityAssessmentResponse.setAssessmentNumber(facilityAssessment.getSeriesName());
        facilityAssessmentResponse.setAssessmentStartDate(facilityAssessment.getStartDate());
        facilityAssessmentResponse.setFacilityName(facilityAssessment.getFacilityName());
        facilityAssessmentResponse.setAssessmentToolName(facilityAssessment.getAssessmentTool().getName());
        facilityAssessmentResponse.setAssessmentTypeName(facilityAssessment.getAssessmentType().getName());
        List<UserResponse> users = facilityAssessment.getUsers().stream().filter(user -> !user.getEmail().equals(User.ANONYMOUS_USERS_EMAIL)).map(UserResponse::fromUser).collect(Collectors.toList());
        facilityAssessmentResponse.setAssessors(users);
        return facilityAssessmentResponse;
    }
}
