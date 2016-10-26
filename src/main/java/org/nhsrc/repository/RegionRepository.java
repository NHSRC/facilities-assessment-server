package org.nhsrc.repository;

import org.nhsrc.domain.Region;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Repository
@RepositoryRestResource(collectionResourceRel = "region", path = "region")
@Transactional
public interface RegionRepository extends BaseRepository<Region> {
    @Query(value = "SELECT entity FROM Region entity WHERE entity.uuid=?1")
    Region find(UUID uuid);
}
