package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
}
