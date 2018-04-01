package org.nhsrc.builder;

import org.nhsrc.domain.*;

public class CheckpointScoreBuilder {
    private CheckpointScore checkpointScore;

    public CheckpointScoreBuilder(int score) {
        this(score, true);
    }

    public CheckpointScoreBuilder(int score, boolean na) {
        checkpointScore = new CheckpointScore();
        checkpointScore.setScore(score);
        checkpointScore.setNa(na);
        Checkpoint checkpoint = new Checkpoint();
        MeasurableElement measurableElement = new MeasurableElement();
        Standard standard = new Standard();
        standard.setAreaOfConcern(new AreaOfConcern());
        measurableElement.setStandard(standard);
        checkpoint.setMeasurableElement(measurableElement);
        checkpointScore.setCheckpoint(checkpoint);
    }

    public CheckpointScoreBuilder standard(int id, String ref) {
        Standard standard = checkpointScore.getCheckpoint().getMeasurableElement().getStandard();
        standard.setId(id);
        standard.setReference(ref);
        return this;
    }

    public CheckpointScoreBuilder areaOfConcern(int id, String ref) {
        AreaOfConcern areaOfConcern = checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern();
        areaOfConcern.setId(id);
        areaOfConcern.setReference(ref);
        return this;
    }

    public CheckpointScore get() {
        return checkpointScore;
    }
}