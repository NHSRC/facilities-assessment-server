package org.nhsrc.domain;

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
}
