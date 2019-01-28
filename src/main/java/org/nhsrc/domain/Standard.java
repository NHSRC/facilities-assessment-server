package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "standard")
@BatchSize(size = 25)
public class Standard extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "short_name", nullable = true)
    private String shortName;

    @Column(name = "reference", unique = true, nullable = false)
    private String reference;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "standard")
    private Set<MeasurableElement> measurableElements = new HashSet<>();

    @ManyToOne(targetEntity = AreaOfConcern.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "area_of_concern_id")
    @NotNull
    private AreaOfConcern areaOfConcern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @JsonIgnore
    public AreaOfConcern getAreaOfConcern() {
        return areaOfConcern;
    }

    @JsonProperty("areaOfConcernId")
    public Integer _getAreaOfConcernId() {
        return this.areaOfConcern.getId();
    }

    @JsonProperty("assessmentToolId")
    public Integer _getAssessmentToolId() {
        return this.areaOfConcern._getAssessmentToolId();
    }

    @JsonProperty("checklistId")
    public Integer _getChecklistId() {
        Checklist checklist = this.areaOfConcern.getChecklist();
        if (checklist == null) return null;
        return checklist.getId();
    }

    public void setAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areaOfConcern = areaOfConcern;
    }

    @JsonIgnore
    public Set<MeasurableElement> getMeasurableElements() {
        return measurableElements;
    }

    @JsonProperty("fullReference")
    public String getFullReference() {
        Checklist checklist = this.getAreaOfConcern().getChecklist();
        return String.format("%s - [%s]", this.getReference(), checklist == null ? null : checklist.getAssessmentTool().getName());
    }

    public void addMeasurableElement(MeasurableElement measurableElement) {
        if (this.measurableElements.stream().noneMatch(std -> std.getReference().equals(measurableElement.getReference()))) {
            this.measurableElements.add(measurableElement);
            measurableElement.setStandard(this);
        }
    }

    public String toSummary() {
        StringBuffer stringBuffer = new StringBuffer();
        measurableElements.stream().sorted((o1, o2) -> o1.getReference().compareTo(o2.getReference())).forEach(measurableElement -> stringBuffer.append(measurableElement.toSummary()).append("\n\t"));
        return String.format("EST_COUNT=%d  STD_REF=%s  STD=%s  #ME=%d  \n\t%s", this.estimatedCount(), reference, name, measurableElements.size(), stringBuffer.toString());
    }

    public void removeEmptyOnes() {
        List<MeasurableElement> toRemove = new ArrayList<>();
        measurableElements.forEach(measurableElement -> {
            if (measurableElement.getCheckpoints().size() == 0) toRemove.add(measurableElement);
        });
        measurableElements.removeAll(toRemove);
    }

    public int estimatedCount() {
        int count = measurableElements.size();
        for (MeasurableElement measurableElement : measurableElements)
            count = count + measurableElement.estimatedCount();
        return count;
    }

    public MeasurableElement getMeasurableElement(String reference) {
        return this.measurableElements.stream().filter(std -> std.getReference().equals(reference)).findAny().orElse(null);
    }

    @Override
    public String toString() {
        return "Standard{" +
                "reference='" + reference + '\'' +
                '}';
    }
}
