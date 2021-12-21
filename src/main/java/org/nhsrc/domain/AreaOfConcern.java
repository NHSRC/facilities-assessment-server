package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.nhsrc.visitor.GunakChecklistVisitor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "area_of_concern")
@BatchSize(size = 25)
public class AreaOfConcern extends AbstractEntity implements ReferencableEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reference", nullable = false)
    private String reference;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "areaOfConcern")
    @RestResource(exported = false)
    private Set<Standard> standards = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "areasOfConcern")
    @NotNull
    @JsonIgnore
    @RestResource(exported = false)
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

    @Override
    public String getReference() {
        return reference;
    }

    public String getReferenceAndName() {
        String referenceAndName = String.format("%s %s %s", this.getReference(), BaseEntity.QUALIFIED_NAME_SEPARATOR, this.getName());
        if (this.getInactive())
            return String.format("%s (INACTIVE)", referenceAndName);
        return referenceAndName;
    }

    public String getFullyQualifiedName() {
        return String.format("%s %s %s %s %s", this.getAssessmentToolNames(), BaseEntity.QUALIFIED_NAME_SEPARATOR, this.getReference(), BaseEntity.QUALIFIED_NAME_SEPARATOR, this.getName());
    }

    @Override
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

    @JsonProperty("assessmentToolIds")
    public List<Integer> _getAssessmentToolIds() {
        return getChecklists().stream().flatMap(checklist -> checklist.getAssessmentTools().stream()).distinct().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @JsonProperty("assessmentToolNames")
    public String getAssessmentToolNames() {
        return getChecklists().stream().flatMap(checklist -> checklist.getAssessmentTools().stream()).distinct().map(AssessmentTool::getName).collect(Collectors.joining("/"));
    }

    @JsonProperty("checklistIds")
    public List<Integer> _getChecklistIds() {
        return this.getChecklists().stream().map(BaseEntity::getId).collect(Collectors.toList());
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

    public void accept(GunakChecklistVisitor visitor) {
        visitor.visit(this);
        this.getApplicableStandards(visitor.getCurrentChecklist()).stream().sorted(Comparator.comparing(Standard::getReference)).forEach(std -> std.accept(visitor));
    }

    @JsonIgnore
    public Set<Standard> getApplicableStandards(Checklist checklist) {
        return this.getStandards().stream().filter(standard -> standard.getApplicableMeasurableElements(checklist).size() != 0).collect(Collectors.toSet());
    }
}
