package org.nhsrc.repository;

import org.nhsrc.domain.CheckpointScore;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CheckpointScoreRepository extends BaseRepository<CheckpointScore> {
}
