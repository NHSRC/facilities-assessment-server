package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "assessment_tool")
public class AssessmentTool extends AbstractEntity {
    public AssessmentTool() {
    }

    public AssessmentTool(String name, String mode) {
        this.name = name;
        this.assessmentToolMode = new AssessmentToolMode(mode);
    }

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = AssessmentToolMode.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "assessment_tool_mode_id")
    @NotNull
    private AssessmentToolMode assessmentToolMode;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "assessmentTool")
    private Set<Checklist> checklists = new HashSet<>();

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AssessmentToolType assessmentToolType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(Set<Checklist> checklists) {
        this.checklists = checklists;
    }

    public void addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
    }

    public void addChecklists(ArrayList<Checklist> checklists) {
        this.checklists.addAll(checklists);
    }

    public String getMode() {
        return assessmentToolMode.getName();
    }

    public void setAssessmentToolMode(AssessmentToolMode assessmentToolMode) {
        this.assessmentToolMode = assessmentToolMode;
    }

    public AssessmentToolType getAssessmentToolType() {
        return assessmentToolType;
    }

    public void setAssessmentToolType(AssessmentToolType assessmentToolType) {
        this.assessmentToolType = assessmentToolType;
    }

    @JsonIgnore
    public AssessmentToolMode getAssessmentToolMode() {
        return assessmentToolMode;
    }

    @JsonProperty("assessmentToolModeId")
    public long _getAssessmentToolModeId() {
        return this.assessmentToolMode.getId();
    }

    @Override
    public String toString() {
        return "AssessmentTool{" +
                "name='" + name + '\'' +
                '}';
    }
}
