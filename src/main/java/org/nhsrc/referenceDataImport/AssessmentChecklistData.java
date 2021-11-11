package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.FacilityAssessment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssessmentChecklistData extends GunakExcelFile {

    private final List<CheckpointScore> checkpointScores = new ArrayList<>();
    private FacilityAssessment assessment;
    private State state;

    public AssessmentChecklistData(AssessmentTool assessmentTool) {
        super(assessmentTool);
    }

    public void addScore(CheckpointScore checkpointScore) {
        checkpointScores.add(checkpointScore);
    }

    public void setAssessment(FacilityAssessment assessment) {
        this.assessment = assessment;
    }

    public List<CheckpointScore> getCheckpointScores() {
        return checkpointScores;
    }

    public List<CheckpointScore> getCheckpointScores(String checklistName) {
        return checkpointScores.stream().filter(checkpointScore -> checkpointScore.getChecklist().getName().equals(checklistName)).collect(Collectors.toList());
    }

    public FacilityAssessment getAssessment() {
        return assessment;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
