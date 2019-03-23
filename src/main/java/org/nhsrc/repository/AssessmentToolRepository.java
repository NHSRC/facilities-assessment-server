package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.web.contract.ext.AssessmentSummaryResponse;
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
import java.util.UUID;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "assessmentTool", path = "assessmentTool")
public interface AssessmentToolRepository extends NonTxDataRepository<AssessmentTool> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<AssessmentTool> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "forAssessmentToolMode", rel = "forAssessmentToolMode")
    Page<AssessmentTool> findByAssessmentToolModeUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("assessmentToolModeUuid") UUID assessmentToolModeUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "byAssessmentToolMode", rel = "byAssessmentToolMode")
    Page<AssessmentTool> findByAssessmentToolModeNameOrderByNameAsc(@Param("assessmentToolModeName") String assessmentToolModeName, Pageable pageable);

    AssessmentTool findByName(String name);
    AssessmentTool findByNameAndAssessmentToolModeName(String name, String assessmentToolModeName);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<AssessmentTool> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByAssessmentToolMode", rel = "findByAssessmentToolMode")
    Page<AssessmentTool> findByAssessmentToolModeId(@Param("assessmentToolModeId") Integer assessmentToolModeId, Pageable pageable);
}