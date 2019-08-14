package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "checklist")
@BatchSize(size = 25)
public class Checklist extends AbstractEntity {
    public Checklist() {
    }

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "checklists")
    @NotNull
    @JsonIgnore
    private Set<AssessmentTool> assessmentTools = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @NotNull
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "checklist_area_of_concern", joinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "area_of_concern_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<AreaOfConcern> areasOfConcern = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "checklist")
    private Set<Checkpoint> checkpoints = new HashSet<>();

    public String getName() {
        return name;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder(this.getAssessmentToolNames());
        stringBuilder.append(BaseEntity.QUALIFIED_NAME_SEPARATOR).append(this.getName());
        if (this.state != null)
            stringBuilder.append(BaseEntity.QUALIFIED_NAME_SEPARATOR).append(this.state.getName());
        return stringBuilder.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<AssessmentTool> getAssessmentTools() {
        return assessmentTools;
    }

    @JsonProperty("assessmentToolNames")
    public String getAssessmentToolNames() {
        return this.getAssessmentTools().stream().map(AssessmentTool::getName).collect(Collectors.joining("/"));
    }

    @JsonProperty("assessmentToolIds")
    public List<Integer> getAssessmentToolIds() {
        return this.assessmentTools.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return this.state == null ? null : this.state.getId();
    }

    public void addAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTools.add(assessmentTool);
        assessmentTool.addChecklist(this);
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

    public List<Integer> getAreaOfConcernIds() {
        return this.areasOfConcern.stream().map(BaseEntity::getId).collect(Collectors.toList());
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
                ", assessmentTool=" + this.getAssessmentToolNames() +
                '}';
    }

    public void removeAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areasOfConcern.remove(areaOfConcern);
    }
}
