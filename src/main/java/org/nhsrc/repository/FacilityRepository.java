package org.nhsrc.repository;

import org.nhsrc.domain.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "facility", path = "facility")
public interface FacilityRepository extends BaseRepository<Facility> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Facility> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "lastModifiedByState", rel = "lastModifiedByState")
    Page<Facility> findByDistrictStateNameAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("name") String name, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

}
