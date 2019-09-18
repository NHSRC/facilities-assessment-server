package org.nhsrc.repository;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.ExcludedCheckpointState;
import org.nhsrc.domain.State;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "excludedCheckpointState", path = "excludedCheckpointState")
public interface ExcludedCheckpointStateRepository extends PagingAndSortingRepository<ExcludedCheckpointState, Integer>, BaseRepository<ExcludedCheckpointState> {
    ExcludedCheckpointState findFirstByCheckpointAndState(Checkpoint checkpoint, State state);
}