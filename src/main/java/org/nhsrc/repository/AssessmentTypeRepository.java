package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "assessmentType", path = "assessmentType")
public interface AssessmentTypeRepository extends NonTxDataRepository<AssessmentType> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<AssessmentType> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date lastModifiedDateTime, Pageable pageable);

    AssessmentType findByName(String name);

    @RestResource(path = "findAllById", rel = "findAllById")
    Page<AssessmentType> findByIdIn(@Param("ids") Integer[] ids, Pageable pageable);

    @RestResource(path = "findByAssessmentToolMode", rel = "findByAssessmentToolMode")
    List<AssessmentType> findAllByAssessmentToolModeId(@Param("assessmentToolModeId") Integer assessmentToolModeId);
}
