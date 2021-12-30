package org.nhsrc.visitor;

import org.nhsrc.domain.*;
import org.nhsrc.mapper.AssessmentToolComponentMapper;
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
        AssessmentToolResponse.ChecklistResponse checklistResponse = AssessmentToolComponentMapper.mapChecklist(checklist);
        this.currentCl = checklistResponse;
        this.currentChecklist = checklist;
        atr.addChecklist(checklistResponse);
    }

    @Override
    public void visit(AreaOfConcern areaOfConcern) {
        AssessmentToolResponse.AreaOfConcernResponse aocResponse = AssessmentToolComponentMapper.mapAreaOfConcern(areaOfConcern);
        this.currentAOC = aocResponse;
        currentCl.addAreaOfConcern(aocResponse);
    }

    @Override
    public void visit(Standard standard) {
        AssessmentToolResponse.StandardResponse stdResponse = AssessmentToolComponentMapper.mapStandard(standard);
        this.currentStd = stdResponse;
        currentAOC.addStandard(stdResponse);
    }

    @Override
    public void visit(MeasurableElement measurableElement) {
        AssessmentToolResponse.MeasurableElementResponse meResponse = AssessmentToolComponentMapper.mapMeasurableElement(measurableElement);
        this.currentME = meResponse;
        currentStd.addMeasurableElement(meResponse);
    }

    @Override
    public void visit(Checkpoint checkpoint) {
        AssessmentToolResponse.CheckpointResponse checkpointResponse = AssessmentToolComponentMapper.mapCheckpoint(checkpoint);
        currentME.addCheckpoint(checkpointResponse);
    }

    @Override
    public Checklist getCurrentChecklist() {
        return currentChecklist;
    }
}
