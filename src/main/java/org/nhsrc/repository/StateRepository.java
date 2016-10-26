package org.nhsrc.repository;

import org.nhsrc.domain.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "state", path = "state")
@Transactional
public interface StateRepository extends BaseRepository<State> {
    @Query(value = "SELECT entity FROM State entity WHERE entity.uuid=?1")
    State find(UUID uuid);
}
