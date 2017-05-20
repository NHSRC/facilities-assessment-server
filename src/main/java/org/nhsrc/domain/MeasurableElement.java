package org.nhsrc.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "measurable_element")
public class MeasurableElement extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "reference", unique = true, nullable = false)
    private String reference;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "measurableElement")
    private Set<Checkpoint> checkpoints = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = Standard.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
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
}
