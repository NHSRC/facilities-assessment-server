package org.nhsrc.repository;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.MeasurableElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
@RepositoryRestResource(collectionResourceRel = "checkpoint", path = "checkpoint")
public interface CheckpointRepository extends BaseRepository<Checkpoint> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Checkpoint> findDistinctByInactiveFalseAndLastModifiedDateGreaterThanAndInactiveFalseOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "forMeasurableElementAndChecklist", rel = "forMeasurableElementAndChecklist")
    Page<Checkpoint> findDistinctByMeasurableElementUuidAndChecklistUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("checklistUuid") UUID checklistUuid, @Param("measurableElementUuid") UUID measurableElementUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    List<Checkpoint> findAllDistinctByNameAndChecklistUuidAndMeasurableElementReference(String name, UUID checklistUuid, String measurableElementReference);

    List<Checkpoint> findAllDistinctByNameAndChecklistUuidAndMeasurableElementStandardReference(String name, UUID checklistUuid, String standardReference);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Checkpoint> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByMeasurableElement", rel = "findByMeasurableElement")
    Page<Checkpoint> findDistinctByMeasurableElementId(@Param("measurableElementId") Integer measurableElementId, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<Checkpoint> findDistinctByChecklistStateIdOrChecklistStateIsNull(@Param("stateId") Integer stateId, Pageable pageable);

    @RestResource(path = "findByStateAndAssessmentTool", rel = "findByStateAndAssessmentTool")
    Page<Checkpoint> findDistinctByChecklistStateIdOrChecklistStateIsNullAndChecklistAssessmentToolId(@Param("stateId") Integer stateId, @Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    @Query("SELECT distinct c FROM Checkpoint c inner join c.measurableElement as me WHERE (c.state.id = :stateId or c.state is null) and c.checklist.id = :checklistId")
    Page<Checkpoint> findByChecklistIdAndStateIdOrStateIsNullOrderByMeasurableElementRefAsNumberAscSortOrderAsc(@Param("checklistId") Integer checklistId, @Param("stateId") Integer stateId, Pageable pageable);

    @RestResource(path = "findByStandard", rel = "findByStandard")
    Page<Checkpoint> findDistinctByMeasurableElementStandardId(@Param("standardId") Integer standardId, Pageable pageable);

    @RestResource(path = "findByAreaOfConcern", rel = "findByAreaOfConcern")
    Page<Checkpoint> findDistinctByMeasurableElementStandardAreaOfConcernId(@Param("areaOfConcernId") Integer areaOfConcernId, Pageable pageable);

    @RestResource(path = "findByChecklist", rel = "findByChecklist")
    Page<Checkpoint> findDistinctByMeasurableElementStandardAreaOfConcernChecklistsId(@Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<Checkpoint> findDistinctByChecklistAssessmentToolId(@Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);
}
