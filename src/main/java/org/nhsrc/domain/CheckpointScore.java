package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "checkpoint_score")
public class CheckpointScore extends AbstractScoreEntity {

    public CheckpointScore() {
    }

    public CheckpointScore(FacilityAssessment facilityAssessment, Checklist checklist, Checkpoint checkpoint, Integer score, String remarks, UUID uuid) {
        this.setUuid(uuid);
        this.facilityAssessment = facilityAssessment;
        this.checklist = checklist;
        this.checkpoint = checkpoint;
        this.score = score;
        this.remarks = remarks;
    }

    @JsonIgnore
    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "facility_assessment_id", referencedColumnName = "id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "checkpoint_id")
    @NotNull
    private Checkpoint checkpoint;

    @Column(name = "score")
    @Min(value = 0)
    @Max(value = 2)
    @NotNull
    private Integer score;

    @Column(name = "remarks")
    private String remarks;

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

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
