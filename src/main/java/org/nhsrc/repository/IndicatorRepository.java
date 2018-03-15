package org.nhsrc.repository;

import org.nhsrc.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "indicator", path = "indicator")
public interface IndicatorRepository extends BaseRepository<Indicator> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Indicator> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    Indicator findByIndicatorDefinitionAndFacilityAssessment(IndicatorDefinition indicatorDefinition, FacilityAssessment facilityAssessment);
}
