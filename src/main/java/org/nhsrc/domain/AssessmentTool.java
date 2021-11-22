package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "assessment_tool")
@BatchSize(size = 25)
public class AssessmentTool extends AbstractEntity {
    public AssessmentTool() {
    }

    public AssessmentTool(String name, String mode) {
        this.name = name;
        this.assessmentToolMode = new AssessmentToolMode(mode);
    }

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(targetEntity = AssessmentToolMode.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_mode_id")
    @NotNull
    private AssessmentToolMode assessmentToolMode;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "assessment_tool_checklist", joinColumns = @JoinColumn(name = "assessment_tool_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Checklist> checklists = new HashSet<>();

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AssessmentToolType assessmentToolType;

    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "assessmentTool", orphanRemoval = true)
    private Set<ExcludedAssessmentToolState> excludedAssessmentToolStates = new HashSet<>();

    @Column(name = "sort_order")
    @NotNull
    private Integer sortOrder;

    @Column(name = "themed")
    private boolean themed;

    public String getName() {
        return name;
    }

    @JsonProperty("fullName")
    public String _getFullName() {
        return String.format("%s %s %s", this.getAssessmentToolMode().getName(), BaseEntity.QUALIFIED_NAME_SEPARATOR, this.getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return assessmentToolMode.getName();
    }

    public void setAssessmentToolMode(AssessmentToolMode assessmentToolMode) {
        this.assessmentToolMode = assessmentToolMode;
    }

    public void setAssessmentToolType(AssessmentToolType assessmentToolType) {
        this.assessmentToolType = assessmentToolType;
    }

    @JsonIgnore
    public AssessmentToolMode getAssessmentToolMode() {
        return assessmentToolMode;
    }

    @JsonProperty("assessmentToolModeId")
    public Integer _getAssessmentToolModeId() {
        return this.assessmentToolMode.getId();
    }

    @JsonIgnore
    public Set<Checklist> getChecklists() {
        return checklists;
    }

    public List<Integer> getChecklistIds() {
        return checklists.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public AssessmentToolType getAssessmentToolType() {
        return assessmentToolType;
    }

    @JsonIgnore
    public State getState() {
        return state;
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return state == null ? null : state.getId();
    }

    @JsonProperty("stateUUID")
    public String _getStateUUID() {
        return state == null ? null : state.getUuidString();
    }

    @Override
    public String toString() {
        return "AssessmentTool{" +
                "name='" + name + '\'' +
                '}';
    }

    public void removeChecklist(Checklist checklist) {
        checklists.remove(checklist);
    }

    public void addChecklist(Checklist checklist) {
        checklists.add(checklist);
    }

    @JsonIgnore
    public Set<ExcludedAssessmentToolState> getExcludedAssessmentToolStates() {
        return excludedAssessmentToolStates;
    }

    public void addOverride(State state, AssessmentTool assessmentTool) {
        ExcludedAssessmentToolState excludedAssessmentToolState = new ExcludedAssessmentToolState();
        excludedAssessmentToolState.setAssessmentTool(assessmentTool);
        excludedAssessmentToolState.setState(state);
        this.excludedAssessmentToolStates.add(excludedAssessmentToolState);
    }

    public List<Integer> getExcludedStateIds() {
        return excludedAssessmentToolStates.stream().filter(excludedAssessmentToolState -> !excludedAssessmentToolState.getInactive()).map(excludedAssessmentToolState -> excludedAssessmentToolState.getState().getId()).collect(Collectors.toList());
    }

    public void setStateApplicability(State applicableState, Set<ExcludedAssessmentToolState> incidentExcludedAssessmentToolStates) {
        this.state = applicableState;
        if (applicableState != null) {
            this.excludedAssessmentToolStates.forEach(excludedCheckpointState -> excludedCheckpointState.setInactive(true));
        } else {
            this.excludedAssessmentToolStates.forEach(currentExcludedCheckpointState -> {
                if (!incidentExcludedAssessmentToolStates.contains(currentExcludedCheckpointState)) currentExcludedCheckpointState.setInactive(true);
            });
            this.excludedAssessmentToolStates.addAll(incidentExcludedAssessmentToolStates);
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isThemed() {
        return themed;
    }

    public void setThemed(boolean themed) {
        this.themed = themed;
    }
}
