package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssessmentChecklistData {
    private List<Checklist> checklists = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<AreaOfConcern> areaOfConcerns = new ArrayList<>();
    private List<CheckpointScore> checkpointScores = new ArrayList<>();
    private AssessmentTool assessmentTool;
    private FacilityAssessment assessment;
    private State state;

    public AssessmentChecklistData() {
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    public void addChecklist(Checklist checklist) {
        checklists.add(checklist);
    }

    public AreaOfConcern findAreaOfConcern(String ref) {
        return areaOfConcerns.stream().filter(aoc -> aoc.getReference().equals(ref)).findAny().orElse(null);
    }

    public void add(AreaOfConcern areaOfConcern) {
        areaOfConcerns.add(areaOfConcern);
    }

    public void set(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public List<Checklist> getChecklists() {
        return this.checklists;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    public List<AreaOfConcern> getAreaOfConcerns() {
        return areaOfConcerns;
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