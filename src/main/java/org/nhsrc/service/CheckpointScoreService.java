package org.nhsrc.service;

import org.nhsrc.repository.CheckpointScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class CheckpointScoreService {
    private CheckpointScoreRepository checkpointScoreRepository;
    private EntityManager entityManager;

    @Autowired
    public CheckpointScoreService(CheckpointScoreRepository checkpointScoreRepository, EntityManager entityManager) {
        this.checkpointScoreRepository = checkpointScoreRepository;
        this.entityManager = entityManager;
    }

    public void deleteDuplicates() {

    }
}