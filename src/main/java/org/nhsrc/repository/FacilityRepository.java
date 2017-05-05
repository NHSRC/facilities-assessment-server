package org.nhsrc.repository;

import org.nhsrc.domain.Facility;
import org.nhsrc.domain.tag.StandardTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "facility", path = "facility")
public interface FacilityRepository extends BaseRepository<Facility> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Facility> findByLastModifiedDateGreaterThanOrderById(@DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastModifiedDateTime, Pageable pageable);
}
