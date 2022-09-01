package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checkpoint_theme")
@BatchSize(size = 25)
public class CheckpointTheme extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @ManyToOne(targetEntity = Checkpoint.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "checkpoint_id")
    @NotNull
    private Checkpoint checkpoint;

    public CheckpointTheme(Theme theme, Checkpoint checkpoint) {
        this.theme = theme;
        this.checkpoint = checkpoint;
    }

    protected CheckpointTheme() {
    }

    @JsonIgnore
    public Theme getTheme() {
        return theme;
    }

    @JsonProperty("checkpoint")
    public String getCheckpointUuid() {
        return checkpoint.getUuidString();
    }

    @JsonProperty("theme")
    public String getThemeUuid() {
        return theme.getUuidString();
    }

    @JsonProperty("checklist")
    public String getChecklistUuid() {
        return checkpoint.getChecklistUUID();
    }
}
