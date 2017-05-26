package org.nhsrc.repository;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.AssessmentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Area;
import java.util.Date;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "areaOfConcern", path = "areaOfConcern")
public interface AreaOfConcernRepository extends BaseRepository<AreaOfConcern> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<AreaOfConcern> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date lastModifiedDateTime, Pageable pageable);
    AreaOfConcern findByReference(String reference);
}