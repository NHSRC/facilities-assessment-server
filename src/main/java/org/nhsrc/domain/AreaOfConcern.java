package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "area_of_concern")
@BatchSize(size = 25)
public class AreaOfConcern extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "reference", unique = true, nullable = false)
    private String reference;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "areaOfConcern")
    private Set<Standard> standards = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "checklist_area_of_concern", joinColumns = @JoinColumn(name = "area_of_concern_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "id"))
    @NotNull
    @JsonIgnore
    private Set<Checklist> checklists = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(Set<Checklist> checklists) {
        this.checklists = checklists;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @JsonIgnore
    public Set<Standard> getStandards() {
        return standards;
    }

    public void setStandards(Set<Standard> standards) {
        this.standards = standards;
    }

    @JsonProperty("assessmentToolId")
    public Integer _getAssessmentToolId() {
        Checklist checklist = getChecklist();
        if (checklist == null) return null;
        return checklist._getAssessmentToolId();
    }

    @JsonIgnore
    public Checklist getChecklist() {
        return this.getChecklists().stream().findFirst().orElse(null);
    }

    @JsonProperty("checklistId")
    public Integer _getChecklistId() {
        Checklist checklist = this.getChecklist();
        return checklist == null ? null : checklist.getId();
    }

    @JsonProperty("fullReference")
    public String getFullReference() {
        Checklist checklist = this.getChecklist();
        return String.format("%s - [%s]", this.getReference(), checklist == null ? null : checklist.getAssessmentTool().getName());
    }

    public void addStandard(Standard standard) {
        this.standards.add(standard);
        standard.setAreaOfConcern(this);
    }

    public String toSummary() {
        StringBuffer stringBuffer = new StringBuffer();
        standards.stream().sorted((o1, o2) -> o1.getReference().compareTo(o2.getReference())).forEach(standard -> stringBuffer.append(standard.toSummary()).append("\n\t"));
        return String.format("EST_COUNT=%d  REF_AOC=%s  AOC=%s  #Standard=%d  \n\t%s", this.estimatedCount(), reference, name, standards.size(), stringBuffer.toString());
    }

    public void removeEmptyOnes() {
        standards.forEach(Standard::removeEmptyOnes);

        List<Standard> toRemove = new ArrayList<>();
        standards.forEach(standard -> {
            if (standard.getMeasurableElements().size() == 0) toRemove.add(standard);
        });
        standards.removeAll(toRemove);
    }

    public int estimatedCount() {
        int count = standards.size();
        for (Standard standard : standards) count = count + standard.estimatedCount();
        return count;
    }

    public void addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
    }

    @JsonIgnore
    public Standard getStandard(String reference) {
        return this.standards.stream().filter(std -> std.getReference().equals(reference)).findAny().orElse(null);
    }

    @Override
    public String toString() {
        return "AreaOfConcern{" +
                "reference='" + reference + '\'' +
                '}';
    }
}
