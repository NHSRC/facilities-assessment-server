package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "checklist")
@BatchSize(size = 25)
public class Checklist extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = AssessmentTool.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_id")
    @NotNull
    private AssessmentTool assessmentTool;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @NotNull
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "checklist_area_of_concern", inverseJoinColumns = @JoinColumn(name = "area_of_concern_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<AreaOfConcern> areasOfConcern = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "checklist")
    private Set<Checkpoint> checkpoints = new HashSet<>();

    public String getName() {
        return name;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return String.format("%s - [%s]", this.getName(), this.state == null ? "ALL" : this.state.getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    @JsonProperty("assessmentToolId")
    public Integer _getAssessmentToolId() {
        return this.assessmentTool.getId();
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return this.state == null ? null : this.state.getId();
    }

    public void setAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    @JsonIgnore
    public Department getDepartment() {
        return department;
    }

    @JsonProperty("departmentId")
    public long getDepartmentId() {
        return this.department.getId();
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @JsonIgnore
    public Set<AreaOfConcern> getAreasOfConcern() {
        return areasOfConcern;
    }

    @JsonIgnore
    public Set<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @JsonProperty("fullReference")
    public String getFullReference() {
        return String.format("%s - [%s]", this.getName(), this.getAssessmentTool().getName());
    }

    public List<Integer> getAreaOfConcernIds() {
        return this.areasOfConcern.stream().map(AbstractTransactionalEntity::getId).collect(Collectors.toList());
    }

    public void addAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areasOfConcern.add(areaOfConcern);
//        areaOfConcern.addChecklist(this);
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
        checkpoint.setChecklist(this);
    }


    @Override
    public String toString() {
        return "Checklist{" +
                "name='" + name + '\'' +
                ", assessmentTool=" + assessmentTool +
                '}';
    }

    public void removeAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areasOfConcern.remove(areaOfConcern);
    }
}
