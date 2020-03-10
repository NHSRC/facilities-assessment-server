package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "standard")
@BatchSize(size = 25)
public class Standard extends AbstractEntity implements ReferencableEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reference", nullable = false)
    private String reference;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "standard")
    private Set<MeasurableElement> measurableElements = new HashSet<>();

    @ManyToOne(targetEntity = AreaOfConcern.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
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

    @JsonProperty("assessmentToolIds")
    public List<Integer> _getAssessmentToolId() {
        return this.areaOfConcern._getAssessmentToolIds();
    }

    @JsonProperty("assessmentToolNames")
    public String getAssessmentToolNames() {
        return this.areaOfConcern.getAssessmentToolNames();
    }

    @JsonProperty("checklistIds")
    public List<Integer> getChecklistIds() {
        return this.areaOfConcern._getChecklistIds();
    }

    public String getReferenceAndName() {
        return String.format("%s %s %s", this.getReference(), BaseEntity.QUALIFIED_NAME_SEPARATOR, this.getName());
    }

    public void setAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areaOfConcern = areaOfConcern;
    }

    @JsonIgnore
    public Set<MeasurableElement> getMeasurableElements() {
        return measurableElements;
    }

    public void addMeasurableElement(MeasurableElement measurableElement) {
        if (this.measurableElements.stream().noneMatch(std -> std.getReference().equals(measurableElement.getReference()))) {
            this.measurableElements.add(measurableElement);
            measurableElement.setStandard(this);
        }
    }

    public String toSummary() {
        StringBuffer stringBuffer = new StringBuffer();
        measurableElements.stream().sorted(Comparator.comparing(MeasurableElement::getReference)).forEach(measurableElement -> stringBuffer.append(measurableElement.toSummary()).append("\n\t"));
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

    @JsonProperty
    public String getAreaOfConcernUUID() {
        return this.areaOfConcern.getUuidString();
    }
}
