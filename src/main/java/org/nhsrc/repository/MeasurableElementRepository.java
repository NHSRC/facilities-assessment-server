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
import java.util.List;
import java.util.UUID;

@Transactional
@Repository
@RepositoryRestResource(collectionResourceRel = "measurableElement", path = "measurableElement")
public interface MeasurableElementRepository extends NonTxDataRepository<MeasurableElement> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<MeasurableElement> findDistinctByInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    MeasurableElement findByReference(String reference);

    @RestResource(path = "forMeasurableElement", rel = "forMeasurableElement")
    Page<MeasurableElement> findDistinctByStandardUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("measurableElementUuid") UUID measurableElementUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<MeasurableElement> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByStandard", rel = "findByStandard")
    Page<MeasurableElement> findDistinctByStandardId(@Param("standardId") Integer standardId, Pageable pageable);

    Page<MeasurableElement> findDistinctByStandardIdAndCheckpointsChecklistId(@Param("standardId") Integer standardId, @Param("checklistId") Integer checklistId, Pageable pageable);

    MeasurableElement findByStandardIdAndReference(@Param("standardId") Integer standardId, @Param("reference") String reference);

    @RestResource(path = "findByAreaOfConcern", rel = "findByAreaOfConcern")
    Page<MeasurableElement> findDistinctByStandardAreaOfConcernId(@Param("areaOfConcernId") Integer areaOfConcernId, Pageable pageable);

    @RestResource(path = "findByChecklist", rel = "findByChecklist")
    Page<MeasurableElement> findDistinctByStandardAreaOfConcernChecklistsId(@Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<MeasurableElement> findDistinctByStandardAreaOfConcernChecklistsAssessmentToolId(@Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    @RestResource(path = "find", rel = "find")
    Page<MeasurableElement> findDistinctByReferenceContaining(@Param("q") String q, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<MeasurableElement> findDistinctByStandardAreaOfConcernChecklistsStateIdOrStandardAreaOfConcernChecklistsStateIsNull(@Param("stateId") Integer stateId, Pageable pageable);

    Page<MeasurableElement> findDistinctByStandardAreaOfConcernChecklistsStateIdOrStandardAreaOfConcernChecklistsStateIsNullAndStandardAreaOfConcernChecklistsAssessmentToolId(@Param("stateId") Integer stateId, @Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);
}
