package org.nhsrc.repository;

import org.nhsrc.domain.Checkpoint;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CheckpointRepository extends BaseRepository<Checkpoint> {
}
