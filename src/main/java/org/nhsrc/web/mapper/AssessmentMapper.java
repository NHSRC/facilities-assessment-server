package org.nhsrc.web.mapper;

import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.repository.CheckpointScoreRepository;
import org.nhsrc.repository.IndicatorRepository;
import org.nhsrc.utils.CollectionUtil;
import org.nhsrc.web.contract.ext.AssessmentResponse;
import org.nhsrc.web.contract.ext.AssessmentSummaryResponse;

import java.util.List;

public class AssessmentMapper {
    public static AssessmentSummaryResponse map(AssessmentSummaryResponse summary, FacilityAssessment source, AssessmentTool assessmentTool) {
        summary.setAssessmentEndDate(source.getEndDate());
        summary.setAssessmentSeries(source.getSeriesName());
        summary.setAssessmentStartDate(source.getStartDate());
        summary.setAssessmentTool(assessmentTool.getName());
        summary.setAssessmentType(source.getAssessmentType().getName());
        District district = source.getDistrict();
        summary.setDistrict(district == null ? "" : district.getName());
        State state = source.getState();
        summary.setState(state == null ? "" : state.getName());
        summary.setFacility(source.getEffectiveFacilityName());
        FacilityType facilityType = source.getFacilityType();
        summary.setFacilityType(facilityType == null ? "" : facilityType.getName());
        summary.setProgram(source.getAssessmentTool().getAssessmentToolMode().getName());
        summary.setSystemId(source.getUuidString());
        return summary;
    }

    public static AssessmentResponse mapAssessmentScores(FacilityAssessment facilityAssessment, AssessmentResponse assessmentResponse, CheckpointScoreRepository checkpointScoreRepository) {
        List<CheckpointScore> scores = checkpointScoreRepository.findByFacilityAssessmentId(facilityAssessment.getId());
        scores.forEach(checkpointScore -> {
            AssessmentResponse.ChecklistAssessment checklistAssessment = CollectionUtil.addIfNotExists(assessmentResponse.getChecklists(), x -> x.getName().equals(checkpointScore.getCheckpoint().getChecklist().getName()), new AssessmentResponse.ChecklistAssessment(checkpointScore.getCheckpoint().getChecklist().getName()));
            AssessmentResponse.AreaOfConcernAssessment areaOfConcernAssessment = CollectionUtil.addIfNotExists(checklistAssessment.getAreaOfConcerns(), x -> x.getReference().equals(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern().getReference()), new AssessmentResponse.AreaOfConcernAssessment(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern().getReference()));
            AssessmentResponse.StandardAssessment standardAssessment = CollectionUtil.addIfNotExists(areaOfConcernAssessment.getStandards(), x -> x.getReference().equals(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getReference()), new AssessmentResponse.StandardAssessment(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getReference()));
            AssessmentResponse.MeasurableElementAssessment measurableElementAssessment = CollectionUtil.addIfNotExists(standardAssessment.getMeasurableElements(), x -> x.getReference().equals(checkpointScore.getCheckpoint().getMeasurableElement().getReference()), new AssessmentResponse.MeasurableElementAssessment(checkpointScore.getCheckpoint().getMeasurableElement().getReference()));

            AssessmentResponse.CheckpointAssessment checkpointAssessment = new AssessmentResponse.CheckpointAssessment();
            checkpointAssessment.setCheckpoint(checkpointScore.getCheckpoint().getName());
            checkpointAssessment.setMarkedNotApplicable(checkpointScore.getNa());
            checkpointAssessment.setRemarks(checkpointScore.getRemarks());
            checkpointAssessment.setScore(checkpointScore.getScore());
            measurableElementAssessment.addCheckpointAssessment(checkpointAssessment);
        });
        assessmentResponse.updateCounts(scores.size());
        return assessmentResponse;
    }

    public static AssessmentResponse mapIndicators(FacilityAssessment facilityAssessment, AssessmentResponse assessmentResponse, IndicatorRepository indicatorRepository) {
        List<Indicator> indicators = indicatorRepository.findByFacilityAssessmentIdOrderByIndicatorDefinitionSortOrder(facilityAssessment.getId());
        indicators.forEach(indicator -> {
            IndicatorDefinition indicatorDefinition = indicator.getIndicatorDefinition();
            AssessmentResponse.IndicatorAssessment indicatorAssessment = new AssessmentResponse.IndicatorAssessment();
            indicatorAssessment.setDataType(indicatorDefinition.getDataType().toString());
            indicatorAssessment.setName(indicatorDefinition.getName());
            indicatorAssessment.setValue(indicator.getValue());
            assessmentResponse.getIndicators().add(indicatorAssessment);
        });
        assessmentResponse.setNumberOfIndicators(indicators.size());
        return assessmentResponse;
    }
}