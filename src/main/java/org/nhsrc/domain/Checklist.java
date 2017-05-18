package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "checklist")
public class Checklist extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = AssessmentTool.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "assessment_tool_id")
    @NotNull
    private AssessmentTool assessmentTool;

    @OneToOne
    @JoinColumn(name = "department_id")
    @NotNull
    private Department department;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "checklist_area_of_concern", inverseJoinColumns = @JoinColumn(name = "area_of_concern_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "id"))
    private Set<AreaOfConcern> areasOfConcern = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "checklist")
    private Set<Checkpoint> checkpoints = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<AreaOfConcern> getAreasOfConcern() {
        return areasOfConcern;
    }

    public void setAreasOfConcern(Set<AreaOfConcern> areasOfConcern) {
        this.areasOfConcern = areasOfConcern;
    }

    public Set<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Set<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public String toSummary() {
        StringBuffer stringBuffer = new StringBuffer();
        areasOfConcern.stream().sorted((o1, o2) -> o1.getReference().compareTo(o2.getReference())).forEach(areaOfConcern -> stringBuffer.append(areaOfConcern.toSummary()).append("\n\t"));
        return String.format("ESTIMATED_COUNT=%d  Checklist=%s  #AOC=%d  #Checkpoints=%d. \n\t%s", this.estimatedCount(), name, areasOfConcern.size(), checkpoints.size(), stringBuffer.toString());
    }

    public void addAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areasOfConcern.add(areaOfConcern);
        areaOfConcern.addChecklist(this);
    }

    public void removeEmptyOnes() {
        areasOfConcern.forEach(AreaOfConcern::removeEmptyOnes);

        List<AreaOfConcern> toRemove = new ArrayList<>();
        areasOfConcern.forEach(areaOfConcern -> {
            if (areaOfConcern.getStandards().size() == 0) toRemove.add(areaOfConcern);
        });
        areasOfConcern.removeAll(toRemove);
    }

    public int estimatedCount() {
        int count = areasOfConcern.size();
        for (AreaOfConcern areaOfConcern : areasOfConcern) count = count + areaOfConcern.estimatedCount();
        return count;
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
        checkpoint.setChecklist(this);
    }
}
