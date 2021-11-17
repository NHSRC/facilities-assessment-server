package org.nhsrc.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.nhsrc.visitor.GunakChecklistVisitor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "measurable_element")
@BatchSize(size = 25)
public class MeasurableElement extends AbstractEntity implements ReferencableEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "ref_num", nullable = false)
    private double refAsNumber;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "measurableElement")
    private Set<Checkpoint> checkpoints = new HashSet<>();

    @ManyToOne(targetEntity = Standard.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
    @NotNull
    private Standard standard;

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
        int ascii = reference.charAt(0);
        StringTokenizer stringTokenizer = new StringTokenizer(reference.substring(1), ".");
        String wholeNumberPart = stringTokenizer.nextToken();
        String decimalPart = stringTokenizer.nextToken();
        final int BASE_ASCII_VALUE = 'A';
        this.refAsNumber = ((ascii - BASE_ASCII_VALUE + 1) * 1000) + (Integer.parseInt(wholeNumberPart) * 100) + Integer.parseInt(decimalPart);
    }

    public double getRefAsNumber() {
        return refAsNumber;
    }

    @JsonIgnore
    public Set<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Set<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    @JsonIgnore
    public Standard getStandard() {
        return standard;
    }

    @JsonProperty("areaOfConcernId")
    public Integer _getAreaOfConcernId() {
        return this.standard.getAreaOfConcern().getId();
    }

    @JsonProperty("assessmentToolIds")
    public List<Integer> _getAssessmentToolId() {
        return this.standard.getAreaOfConcern()._getAssessmentToolIds();
    }

    @JsonProperty("assessmentToolNames")
    public String getAssessmentToolNames() {
        return this.standard.getAreaOfConcern().getAssessmentToolNames();
    }

    @JsonProperty("checklistId")
    public List<Integer> getChecklistIds() {
        return this.standard.getAreaOfConcern()._getChecklistIds();
    }

    @JsonProperty("standardId")
    public Integer _getStandardId() {
        return this.standard.getId();
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public String getReferenceAndName() {
        String referenceAndName = String.format("%s %s %s", this.getReference(), BaseEntity.QUALIFIED_NAME_SEPARATOR, this.getName());
        if (this.getInactive())
            return String.format("%s (INACTIVE)", referenceAndName);
        return referenceAndName;
    }

    public String toSummary() {
        String problemText = checkpoints.size() == 0 ? "############################" : "";
        return String.format("%sME=%s  #Checkpoint=%d", problemText, name, checkpoints.size());
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.add(checkpoint);
        checkpoint.setMeasurableElement(this);
    }

    public int estimatedCount() {
        return checkpoints.size() - 1;
    }

    @Override
    public String toString() {
        return "MeasurableElement{" +
                "reference='" + reference + '\'' +
                '}';
    }

    public boolean containsCheckpoint(Checkpoint checkpoint) {
        return findCheckpoint(checkpoint.getName(), checkpoint.getChecklist()) != null;
    }

    public Checkpoint findCheckpoint(String name, Checklist checklist) {
        return checkpoints.stream().filter(checkpoint1 -> {
            {
                return checkpoint1.getName().equals(name) && checkpoint1.getChecklist().getName().equals(checklist.getName());
            }
        }).findAny().orElse(null);
    }

    @JsonProperty
    public String getStandardUUID() {
        return getStandard().getUuidString();
    }

    public void accept(GunakChecklistVisitor visitor) {
        visitor.visit(this);
        this.getCheckpoints().stream().sorted(Comparator.comparing(Checkpoint::getSortOrder)).forEach(cp -> cp.accept(visitor));
    }
}
