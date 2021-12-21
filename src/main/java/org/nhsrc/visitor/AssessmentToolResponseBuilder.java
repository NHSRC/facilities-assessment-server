package org.nhsrc.visitor;

import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.GunakExcelFile;
import org.nhsrc.web.contract.ext.AssessmentToolResponse;

public class AssessmentToolResponseBuilder implements GunakChecklistVisitor {
    private final AssessmentToolResponse atr;
    private Checklist currentChecklist;
    private AssessmentToolResponse.ChecklistResponse currentCl;
    private AssessmentToolResponse.AreaOfConcernResponse currentAOC;
    private AssessmentToolResponse.StandardResponse currentStd;
    private AssessmentToolResponse.MeasurableElementResponse currentME;

    public AssessmentToolResponseBuilder(AssessmentToolResponse atr) {
        this.atr = atr;
    }

    @Override
    public void visit(GunakExcelFile gunakExcelFile) {
    }

    @Override
    public void visit(Checklist checklist) {
        AssessmentToolResponse.ChecklistResponse checklistResponse = new AssessmentToolResponse.ChecklistResponse();
        updateToolComponent(checklist, checklistResponse);
        checklistResponse.setName(checklist.getName());
        this.currentCl = checklistResponse;
        this.currentChecklist = checklist;
        atr.addChecklist(checklistResponse);
    }

    @Override
    public void visit(AreaOfConcern areaOfConcern) {
        AssessmentToolResponse.AreaOfConcernResponse aocResponse = new AssessmentToolResponse.AreaOfConcernResponse();
        updateToolComponent(areaOfConcern, aocResponse);
        updateReferenceable(areaOfConcern, aocResponse);
        this.currentAOC = aocResponse;
        currentCl.addAreaOfConcern(aocResponse);
    }

    @Override
    public void visit(Standard standard) {
        AssessmentToolResponse.StandardResponse stdResponse = new AssessmentToolResponse.StandardResponse();
        updateToolComponent(standard, stdResponse);
        updateReferenceable(standard, stdResponse);
        this.currentStd = stdResponse;
        currentAOC.addStandard(stdResponse);
    }

    @Override
    public void visit(MeasurableElement measurableElement) {
        AssessmentToolResponse.MeasurableElementResponse meResponse = new AssessmentToolResponse.MeasurableElementResponse();
        updateToolComponent(measurableElement, meResponse);
        updateReferenceable(measurableElement, meResponse);
        this.currentME = meResponse;
        currentStd.addMeasurableElement(meResponse);
    }

    @Override
    public void visit(Checkpoint checkpoint) {
        AssessmentToolResponse.CheckpointResponse checkpointResponse = new AssessmentToolResponse.CheckpointResponse();
        updateToolComponent(checkpoint, checkpointResponse);
        checkpointResponse.setName(checkpoint.getName());
        checkpointResponse.setByObservation(checkpoint.getAssessmentMethodObservation());
        checkpointResponse.setByPatientInterview(checkpoint.getAssessmentMethodPatientInterview());
        checkpointResponse.setByStaffInterview(checkpoint.getAssessmentMethodStaffInterview());
        checkpointResponse.setByRecordReview(checkpoint.getAssessmentMethodRecordReview());
        checkpointResponse.setMeansOfVerification(checkpoint.getMeansOfVerification());
        currentME.addCheckpoint(checkpointResponse);
    }

    @Override
    public Checklist getCurrentChecklist() {
        return currentChecklist;
    }

    private void updateReferenceable(ReferencableEntity referencableEntity, AssessmentToolResponse.BaseToolReferenceComponent baseToolReferenceComponent) {
        baseToolReferenceComponent.setName(referencableEntity.getName());
        baseToolReferenceComponent.setReference(referencableEntity.getReference());
    }

    private void updateToolComponent(AbstractEntity toolComponentEntity, AssessmentToolResponse.BaseToolComponent baseToolComponent) {
        baseToolComponent.setInactive(toolComponentEntity.getInactive());
        baseToolComponent.setSystemId(toolComponentEntity.getUuidString());
    }
}
