package org.nhsrc.domain;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "excluded_checkpoint_state")
@BatchSize(size = 25)
public class ExcludedCheckpointState extends AbstractEntity {
    @ManyToOne(targetEntity = Checkpoint.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "checkpoint_id")
    @NotNull
    private Checkpoint checkpoint;

    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    public ExcludedCheckpointState() {
    }

    public ExcludedCheckpointState(Checkpoint checkpoint, State state) {
        this.checkpoint = checkpoint;
        this.state = state;
        this.setInactive(false);
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "{" +
                    "checkpoint=" + checkpoint.getName() +
                    ", state=" + state.getName() +
                '}';
    }
}