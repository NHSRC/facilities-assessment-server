package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.visitor.GunakChecklistVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GunakExcelFile {
    private final List<Checklist> checklists = new ArrayList<>();
    private final AssessmentTool assessmentTool;
    private final Set<AreaOfConcern> areaOfConcerns = new HashSet<>();

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

    public Set<AreaOfConcern> getAreaOfConcerns() {
        return areaOfConcerns;
    }

    public void accept(GunakChecklistVisitor visitor) {
        visitor.visit(this);
        checklists.forEach(checklist -> checklist.accept(visitor));
    }
}
