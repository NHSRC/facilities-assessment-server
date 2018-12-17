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
    Page<Checkpoint> findByInactiveFalseAndLastModifiedDateGreaterThanAndInactiveFalseOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "forMeasurableElementAndChecklist", rel = "forMeasurableElementAndChecklist")
    Page<Checkpoint> findByMeasurableElementUuidAndChecklistUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("checklistUuid") UUID checklistUuid, @Param("measurableElementUuid") UUID measurableElementUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    List<Checkpoint> findAllByNameAndChecklistUuidAndMeasurableElementReference(String name, UUID checklistUuid, String measurableElementReference);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Checkpoint> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByMeasurableElement", rel = "findByMeasurableElement")
    Page<Checkpoint> findByMeasurableElementId(@Param("measurableElementId") Integer measurableElementId, Pageable pageable);

    @RestResource(path = "findByChecklist", rel = "findByChecklist")
    Page<Checkpoint> findByChecklistIdOrderByMeasurableElementRefAsNumberAscSortOrderAsc(@Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<Checkpoint> findByStateIdOrStateIsNullOrderByMeasurableElementRefAsNumberAscSortOrderAsc(@Param("stateId") Integer stateId, Pageable pageable);

    @Query("SELECT c FROM Checkpoint c inner join c.measurableElement as me WHERE (c.state.id = :stateId or c.state is null) and c.checklist.id = :checklistId order by me.refAsNumber")
    Page<Checkpoint> findByChecklistIdAndStateIdOrStateIsNullOrderByMeasurableElementRefAsNumberAscSortOrderAsc(@Param("checklistId") Integer checklistId, @Param("stateId") Integer stateId, Pageable pageable);
}
