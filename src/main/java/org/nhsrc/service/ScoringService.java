package org.nhsrc.service;

import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.scores.AreaOfConcernScore;
import org.nhsrc.domain.scores.StandardScore;

import java.util.ArrayList;
import java.util.List;

public class ScoringService {
    public List<AreaOfConcernScore> getChangedAreaOfConcernScores(List<CheckpointScore> checkpointScores) {
        return new ArrayList<>();
    }

    public List<StandardScore> getChangedStandardScores(List<CheckpointScore> checkpointScores) {
        return new ArrayList<>();
    }

    public List<StandardScore> getChecklistScores(List<CheckpointScore> checkpointScores) {
        return new ArrayList<>();
    }
}