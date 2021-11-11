package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;

import java.util.ArrayList;
import java.util.List;

public class GunakExcelFile {
    private final List<Checklist> checklists = new ArrayList<>();
    private final AssessmentTool assessmentTool;
    private final List<AreaOfConcern> areaOfConcerns = new ArrayList<>();

    public GunakExcelFile(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public void addChecklist(Checklist checklist) {
        checklists.add(checklist);
    }

    public List<Checklist> getChecklists() {
        return this.checklists;
    }

    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    public AreaOfConcern findAreaOfConcern(String ref) {
        return areaOfConcerns.stream().filter(aoc -> aoc.getReference().equals(ref)).findAny().orElse(null);
    }

    public void add(AreaOfConcern areaOfConcern) {
        areaOfConcerns.add(areaOfConcern);
    }

    public List<AreaOfConcern> getAreaOfConcerns() {
        return areaOfConcerns;
    }
}
