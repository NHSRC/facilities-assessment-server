package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.*;

import java.util.ArrayList;
import java.util.List;

public class AssessmentChecklistData {
    private List<Checklist> checklists = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<AreaOfConcern> areaOfConcerns = new ArrayList<>();
    private AssessmentTool assessmentTool;
    private State state;

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

    public void set(State state) {
        this.state = state;
    }

    public List<Checklist> getChecklists() {
        return this.checklists;
    }

    public State getState() {
        return state;
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
}