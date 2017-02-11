package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "checklist_assessment")
public class ChecklistAssessment extends AbstractTransactionalEntity {
    @Column(name = "uuid", updatable = false, unique = true)
    @NotNull
    private UUID uuid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @OneToOne
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "checklistAssessment")
    private Set<CheckpointScore> checkpointScores = new HashSet<>();


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public Set<CheckpointScore> getCheckpointScores() {
        return checkpointScores;
    }

    public void setCheckpointScores(Set<CheckpointScore> checkpointScores) {
        this.checkpointScores = checkpointScores;
    }
}
