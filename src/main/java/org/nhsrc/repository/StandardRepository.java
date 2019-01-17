package org.nhsrc.repository;

import org.nhsrc.domain.Standard;
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
@RepositoryRestResource(collectionResourceRel = "standard", path = "standard")
public interface StandardRepository extends BaseRepository<Standard> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Standard> findDistinctByInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    Standard findByReference(String reference);

    @RestResource(path = "forAreaOfConcern", rel = "forAreaOfConcern")
    Page<Standard> findDistinctByAreaOfConcernUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("areaOfConcernUuid") UUID areaOfConcernUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Standard> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByAreaOfConcern", rel = "findByAreaOfConcern")
    Page<Standard> findDistinctByAreaOfConcernId(@Param("areaOfConcernId") Integer areaOfConcernId, Pageable pageable);

    @RestResource(path = "findByChecklist", rel = "findByChecklist")
    Page<Standard> findDistinctByAreaOfConcernChecklistsId(@Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<Standard> findDistinctByAreaOfConcernChecklistsStateIdOrAreaOfConcernChecklistsStateIsNull(@Param("stateId") Integer stateId, Pageable pageable);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<Standard> findDistinctByAreaOfConcernChecklistsAssessmentToolId(@Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);
}
