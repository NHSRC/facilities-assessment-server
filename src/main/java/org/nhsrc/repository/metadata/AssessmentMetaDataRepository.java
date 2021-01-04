package org.nhsrc.repository.metadata;

import org.nhsrc.domain.metadata.AssessmentMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "assessmentMetaData", path = "assessmentMetaData")
public interface AssessmentMetaDataRepository extends PagingAndSortingRepository<AssessmentMetaData, Integer> {
    AssessmentMetaData findByUuid(UUID uuid);
    AssessmentMetaData findByName(String name);

    @RestResource(path = "lastModified", rel = "lastModified")
    Page<AssessmentMetaData> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
}