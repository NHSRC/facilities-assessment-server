package org.nhsrc.repository;

import org.nhsrc.domain.MeasurableElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@Transactional
@Repository
@RepositoryRestResource(collectionResourceRel = "measurableElement", path = "measurableElement")
public interface MeasurableElementRepository extends BaseRepository<MeasurableElement> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<MeasurableElement> findByLastModifiedDateGreaterThanOrderById(@Param("lastModifiedDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
}