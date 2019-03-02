package org.nhsrc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nhsrc.builder.CheckpointScoreBuilder;
import org.nhsrc.domain.CheckpointScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
    @WithMockUser(username = "user1", password = "pwd", roles = "Assessment_Read")
    public void noop() {
        scoreService.scoreAssessments();
    }
}