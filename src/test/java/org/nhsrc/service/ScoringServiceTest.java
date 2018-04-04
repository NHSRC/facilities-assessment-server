package org.nhsrc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nhsrc.builder.CheckpointScoreBuilder;
import org.nhsrc.domain.CheckpointScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class ScoringServiceTest {
    @Autowired
    private ScoringService scoreService;

    @Test
    @Transactional
    public void noop() {
        scoreService.scoreAssessments();
    }
}