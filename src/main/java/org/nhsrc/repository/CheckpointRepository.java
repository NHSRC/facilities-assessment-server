package org.nhsrc.repository;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Checkpoint;
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
public interface CheckpointRepository extends NonTxDataRepository<Checkpoint> {
    List<Checkpoint> findAllByNameAndChecklistUuidAndMeasurableElementReference(String name, UUID checklistUuid, String measurableElementReference);

    List<Checkpoint> findAllDistinctByNameAndChecklistUuidAndMeasurableElementStandardReference(String name, UUID checklistUuid, String standardReference);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Checkpoint> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByMeasurableElement", rel = "findByMeasurableElement")
    Page<Checkpoint> findByMeasurableElementIdAndChecklistId(@Param("measurableElementId") Integer measurableElementId, @Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<Checkpoint> findByChecklistStateIdOrChecklistStateIsNull(@Param("stateId") Integer stateId, Pageable pageable);

    @RestResource(path = "findByStateAndAssessmentTool", rel = "findByStateAndAssessmentTool")
    Page<Checkpoint> findByChecklistStateIdOrChecklistStateIsNullAndChecklistAssessmentToolsId(@Param("stateId") Integer stateId, @Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    @RestResource(path = "findByStandard", rel = "findByStandard")
    Page<Checkpoint> findByMeasurableElementStandardIdAndChecklistId(@Param("standardId") Integer standardId, @Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByAreaOfConcern", rel = "findByAreaOfConcern")
    Page<Checkpoint> findByMeasurableElementStandardAreaOfConcernIdAndChecklistId(@Param("areaOfConcernId") Integer areaOfConcernId, @Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<Checkpoint> findByChecklistAssessmentToolsId(@Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    Page<Checkpoint> findByChecklistIdAndMeasurableElementId(@Param("checklistId") Integer checklistId, @Param("measurableElementId") Integer measurableElementId, Pageable pageable);

    Page<Checkpoint> findAllByChecklistId(@Param("checklistId") Integer checklistId, Pageable pageable);

    Page<Checkpoint> findAllByChecklistIdIn(List<Integer> checklistIds, Pageable pageable);

    int countAllByChecklist(@Param("checklist") Checklist checklist);
}