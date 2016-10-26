package org.nhsrc.repository;

import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "facility", path = "facility")
@Transactional
public interface FacilityRepository extends BaseRepository<Facility> {
    @Query(value = "SELECT entity FROM Facility entity WHERE entity.uuid=?1")
    Facility find(UUID uuid);

}
