package org.nhsrc.repository;

import org.nhsrc.domain.District;
import org.nhsrc.domain.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "district", path = "district")
public interface DistrictRepository extends NonTxDataRepository<District> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<District> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "lastModifiedByState", rel = "lastModifiedByState")
    Page<District> findByStateNameAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("name") String name, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "byState", rel = "byState")
    Page<District> findByStateNameOrderByName(@Param("stateName") String stateName, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<District> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByState", rel = "findByState")
    Page<District> findByStateId(@Param("stateId") Integer stateId, Pageable pageable);

    @RestResource(path = "find", rel = "find")
    Page<District> findByNameStartingWithOrderByName(@Param("q") String q, Pageable pageable);

    List<District> findByNameContainingIgnoreCaseAndState(@Param("name") String name, @Param("state") State state);
    List<District> findByNameAndState(@Param("name") String name, @Param("state") State state);
}
