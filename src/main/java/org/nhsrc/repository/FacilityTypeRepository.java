package org.nhsrc.repository;

import org.nhsrc.domain.FacilityType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "facilityType", path = "facilityType")
@Transactional
public interface FacilityTypeRepository extends BaseRepository<FacilityType> {
    @Query(value = "SELECT entity FROM FacilityType entity WHERE entity.uuid=?1")
    FacilityType find(UUID uuid);

}
