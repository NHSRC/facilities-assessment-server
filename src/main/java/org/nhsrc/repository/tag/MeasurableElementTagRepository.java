package org.nhsrc.repository.tag;

import org.nhsrc.domain.tag.MeasurableElementTag;
import org.nhsrc.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "measurableElementTag", path = "measurableElementTag")
public interface MeasurableElementTagRepository extends BaseRepository<MeasurableElementTag> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<MeasurableElementTag> findByLastModifiedDateGreaterThanOrderById(@Param("lastModifiedDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
}