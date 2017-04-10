package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checkpoint_score")
public class CheckpointScore extends AbstractScoreEntity {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @OneToOne
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

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
