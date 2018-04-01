package org.nhsrc.service;

import org.junit.Test;
import org.nhsrc.builder.CheckpointScoreBuilder;
import org.nhsrc.domain.CheckpointScore;

import java.util.ArrayList;

public class ScoringServiceTest {
    @Test
    public void getChangedAreaOfConcernScores() {
        ArrayList<CheckpointScore> checkpointScores = new ArrayList<>();
        checkpointScores.add(new CheckpointScoreBuilder(0).areaOfConcern(1, "A").standard(1, "A1").get());
        checkpointScores.add(new CheckpointScoreBuilder(1).areaOfConcern(1, "A").standard(1, "A1").get());
        ScoringService scoringService = new ScoringService();
        scoringService.getChangedAreaOfConcernScores(checkpointScores);
    }

    @Test
    public void getChangedStandardScores() {
    }

    @Test
    public void getChecklistScores() {
    }
}