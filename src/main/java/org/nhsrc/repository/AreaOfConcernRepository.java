package org.nhsrc.repository;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.District;
import org.nhsrc.domain.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
@RepositoryRestResource(collectionResourceRel = "areaOfConcern", path = "areaOfConcern")
public interface AreaOfConcernRepository extends BaseRepository<AreaOfConcern> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<AreaOfConcern> findDistinctByInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    AreaOfConcern findDistinctByReference(String reference);

    @RestResource(path = "forChecklist", rel = "forChecklist")
    Page<AreaOfConcern> findDistinctByChecklistsUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("checklistUuid") UUID checklistUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<AreaOfConcern> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByChecklist", rel = "findByChecklist")
    Page<AreaOfConcern> findDistinctByChecklistsId(@Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<AreaOfConcern> findDistinctByChecklistsAssessmentToolId(@Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    Page<AreaOfConcern> findAllDistinctByChecklistsId(@Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<AreaOfConcern> findAllDistinctByChecklistsStateIdOrChecklistsStateIdIsNull(@Param("stateId") Integer stateId, Pageable pageable);

    @Query("SELECT distinct aoc FROM AreaOfConcern aoc inner join aoc.checklists c inner join c.assessmentTool at WHERE (c.state.id = :stateId or c.state is null) and c.assessmentTool.id = :assessmentToolId")
    Page<AreaOfConcern> findAllByStateAndAssessmentTool(@Param("assessmentToolId") Integer assessmentToolId, @Param("stateId") Integer stateId, Pageable pageable);
}