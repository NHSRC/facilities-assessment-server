package org.nhsrc.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "measurable_element")
public class MeasurableElement extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "reference", unique = true, nullable = false)
    private String reference;

    @Column(name = "ref_num", nullable = false)
    private double refAsNumber;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "measurableElement")
    private Set<Checkpoint> checkpoints = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = Standard.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "standard_id")
    @NotNull
    private Standard standard;

    private static int BASE_ASCII_VALUE = 'A';

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
        this.refAsNumber = ((ascii - BASE_ASCII_VALUE + 1) * 1000) + (Integer.parseInt(wholeNumberPart) * 100) + Integer.parseInt(decimalPart);
    }

    public double getRefAsNumber() {
        return refAsNumber;
    }

    public Set<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Set<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public Standard getStandard() {
        return standard;
    }

    @JsonProperty("standardId")
    public long _getStandardId() {
        return this.standard.getId();
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
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
        Checklist checklist = checkpoint.getChecklist();
        return findCheckpoint(checkpoint.getName(), checkpoint.getChecklist()) != null;
    }

    public Checkpoint findCheckpoint(String name, Checklist checklist) {
        return checkpoints.stream().filter(checkpoint1 -> {
            {
                return checkpoint1.getName().equals(name) && checkpoint1.getChecklist().getName().equals(checklist.getName());
            }
        }).findAny().orElse(null);
    }
}
