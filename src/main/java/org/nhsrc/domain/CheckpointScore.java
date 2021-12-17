package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.springframework.data.rest.core.annotation.RestResource;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    @RestResource(exported = false)
    private FacilityAssessment facilityAssessment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "checklist_id")
    @NotNull
    @RestResource(exported = false)
    private Checklist checklist;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "checkpoint_id")
    @NotNull
    @RestResource(exported = false)
    private Checkpoint checkpoint;

    @Column(name = "score")
    @Min(value = 0)
    @Max(value = 2)
    private Integer score;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "na")
    private Boolean na = false;

    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    @JsonProperty("facilityAssessmentId")
    public Integer _getFacilityAssessmentId() {
        return this.facilityAssessment.getId();
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

    public Boolean getNa() {
        return na == null ? false : na;
    }

    public void setNa(Boolean na) {
        this.na = na;
    }

    public int getScoreNumerator() {
        return na ? 0 : score;
    }

    public int getScoreDenominator() {
        return na ? 0 : 2;
    }

    @Override
    public String toString() {
        return "{" +
                "checkpoint=" + checkpoint +
                ", score=" + score +
                ", remarks='" + remarks + '\'' +
                ", na=" + na +
                '}';
    }
}
