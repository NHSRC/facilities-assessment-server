package org.nhsrc.repository;

import org.nhsrc.domain.District;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "district", path = "district")
@Transactional
public interface DistrictRepository extends BaseRepository<District> {
    @Query(value = "SELECT entity FROM District entity WHERE entity.uuid=?1")
    District find(UUID uuid);
}
