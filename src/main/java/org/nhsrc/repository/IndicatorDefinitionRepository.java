package org.nhsrc.repository;

import org.nhsrc.domain.IndicatorDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
@Repository
@RepositoryRestResource(collectionResourceRel = "indicatorDefinition", path = "indicatorDefinition")
public interface IndicatorDefinitionRepository extends BaseRepository<IndicatorDefinition> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<IndicatorDefinition> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
}