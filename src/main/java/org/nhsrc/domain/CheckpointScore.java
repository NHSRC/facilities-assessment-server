package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "checkpoint_score")
public class CheckpointScore extends AbstractTransactionalEntity {

    @Column(name = "uuid", updatable = false, unique = true)
    @NotNull
    private UUID uuid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = ChecklistAssessment.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "checklist_assessment_id")
    @NotNull
    private ChecklistAssessment checklistAssessment;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ChecklistAssessment getChecklistAssessment() {
        return checklistAssessment;
    }

    public void setChecklistAssessment(ChecklistAssessment checklistAssessment) {
        this.checklistAssessment = checklistAssessment;
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
