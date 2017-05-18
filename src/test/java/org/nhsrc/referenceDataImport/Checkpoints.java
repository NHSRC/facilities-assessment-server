package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.Checkpoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Checkpoints implements Serializable {
    private List<Checkpoint> checkpoints;

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public Checkpoints() {
        this.checkpoints = new ArrayList<Checkpoint>();
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.add(checkpoint);
    }
}
