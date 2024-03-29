package org.nhsrc.mapper;

import org.nhsrc.domain.*;
import org.nhsrc.web.contract.BaseToolComponent;
import org.nhsrc.web.contract.ext.AssessmentToolResponse;

import java.util.List;
import java.util.stream.Collectors;

public class AssessmentToolComponentMapper {
    public static AssessmentToolResponse.CheckpointResponse mapCheckpoint(Checkpoint checkpoint) {
        AssessmentToolResponse.CheckpointResponse checkpointResponse = new AssessmentToolResponse.CheckpointResponse();
        updateToolComponent(checkpoint, checkpointResponse);
        checkpointResponse.setName(checkpoint.getName());
        checkpointResponse.setByObservation(checkpoint.getAssessmentMethodObservation());
        checkpointResponse.setByPatientInterview(checkpoint.getAssessmentMethodPatientInterview());
        checkpointResponse.setByStaffInterview(checkpoint.getAssessmentMethodStaffInterview());
        checkpointResponse.setByRecordReview(checkpoint.getAssessmentMethodRecordReview());
        checkpointResponse.setMeansOfVerification(checkpoint.getMeansOfVerification());
        checkpointResponse.setMeasurableElement(checkpoint.getMeasurableElementUUID());
        checkpointResponse.setSortOrder(checkpoint.getSortOrder());
        return checkpointResponse;
    }

    private static void updateReferenceable(ReferencableEntity referencableEntity, AssessmentToolResponse.BaseToolReferenceComponent baseToolReferenceComponent) {
        baseToolReferenceComponent.setName(referencableEntity.getName());
        baseToolReferenceComponent.setReference(referencableEntity.getReference());
    }

    private static void updateToolComponent(AbstractEntity toolComponentEntity, BaseToolComponent baseToolComponent) {
        baseToolComponent.setInactive(toolComponentEntity.getInactive());
        baseToolComponent.setSystemId(toolComponentEntity.getUuidString());
        baseToolComponent.setLastModifiedDate(toolComponentEntity.getLastModifiedDate());
        baseToolComponent.setCreatedDate(toolComponentEntity.getCreatedDate());
    }

    public static AssessmentToolResponse.MeasurableElementResponse mapMeasurableElement(MeasurableElement measurableElement) {
        AssessmentToolResponse.MeasurableElementResponse meResponse = new AssessmentToolResponse.MeasurableElementResponse();
        updateToolComponent(measurableElement, meResponse);
        updateReferenceable(measurableElement, meResponse);
        meResponse.setStandard(measurableElement.getStandardUUID());
        return meResponse;
    }

    public static AssessmentToolResponse.StandardResponse mapStandard(Standard standard) {
        AssessmentToolResponse.StandardResponse stdResponse = new AssessmentToolResponse.StandardResponse();
        updateToolComponent(standard, stdResponse);
        updateReferenceable(standard, stdResponse);
        stdResponse.setAreaOfConcern(standard.getAreaOfConcernUUID());
        return stdResponse;
    }

    public static AssessmentToolResponse.AreaOfConcernResponse mapAreaOfConcern(AreaOfConcern areaOfConcern) {
        AssessmentToolResponse.AreaOfConcernResponse aocResponse = new AssessmentToolResponse.AreaOfConcernResponse();
        updateToolComponent(areaOfConcern, aocResponse);
        updateReferenceable(areaOfConcern, aocResponse);
        List<String> checklists = areaOfConcern.getChecklists().stream().map(AbstractEntity::getUuidString).collect(Collectors.toList());
        aocResponse.setChecklists(checklists);
        return aocResponse;
    }

    public static AssessmentToolResponse.ChecklistResponse mapChecklist(Checklist checklist) {
        AssessmentToolResponse.ChecklistResponse checklistResponse = new AssessmentToolResponse.ChecklistResponse();
        updateToolComponent(checklist, checklistResponse);
        checklistResponse.setName(checklist.getName());
        checklistResponse.setAreaOfConcerns(checklist.getAreasOfConcern().stream().map(AbstractEntity::getUuidString).collect(Collectors.toList()));
        AssessmentTool assessmentTool = checklist.getAssessmentTools().stream().findFirst().orElse(null);
        if (assessmentTool == null)
            throw new RuntimeException(String.format("No assessment tool found for checklist: %s. This may be due to checklist is currently being authored. Please contact the administrator, if you think that may not be case.", checklist.getUuid()));
        checklistResponse.setAssessmentTool(assessmentTool.getUuidString());
        return checklistResponse;
    }

    public static AssessmentToolResponse mapAssessmentTool(AssessmentTool assessmentTool) {
        AssessmentToolResponse atr = new AssessmentToolResponse();
        updateToolComponent(assessmentTool, atr);
        atr.setProgram(assessmentTool.getAssessmentToolMode().getUuidString());
        atr.setAssessmentToolType(assessmentTool.getAssessmentToolType().name());
        atr.setName(assessmentTool.getName());
        if (assessmentTool.getState() != null)
            atr.setState(assessmentTool.getState().getName());
        return atr;
    }

    public static AssessmentToolResponse.ProgramResponse mapProgram(AssessmentToolMode assessmentToolMode) {
        AssessmentToolResponse.ProgramResponse programResponse = new AssessmentToolResponse.ProgramResponse();
        updateToolComponent(assessmentToolMode, programResponse);
        programResponse.setName(assessmentToolMode.getName());
        return programResponse;
    }

    public static AssessmentToolResponse.AssessmentTypeResponse mapAssessmentType(AssessmentType assessmentType) {
        AssessmentToolResponse.AssessmentTypeResponse assessmentTypeResponse = new AssessmentToolResponse.AssessmentTypeResponse();
        updateToolComponent(assessmentType, assessmentTypeResponse);
        assessmentTypeResponse.setName(assessmentType.getName());
        assessmentTypeResponse.setProgram(assessmentType.getAssessmentToolMode().getUuidString());
        return assessmentTypeResponse;
    }

    public static AssessmentToolResponse.IndicatorResponse mapIndicatorDefinitions(IndicatorDefinition indicatorDefinition) {
        AssessmentToolResponse.IndicatorResponse indicatorResponse = new AssessmentToolResponse.IndicatorResponse();
        updateToolComponent(indicatorDefinition, indicatorResponse);
        indicatorResponse.setName(indicatorDefinition.getName());
        indicatorResponse.setCodedValues(indicatorDefinition.getCodedValues());
        indicatorResponse.setDescription(indicatorDefinition.getDescription());
        indicatorResponse.setDataType(indicatorDefinition.getDataType().name());
        indicatorResponse.setAssessmentTool(indicatorDefinition.getAssessmentTool().getUuidString());
        indicatorResponse.setSortOrder(indicatorDefinition.getSortOrder());
        return indicatorResponse;
    }
}
