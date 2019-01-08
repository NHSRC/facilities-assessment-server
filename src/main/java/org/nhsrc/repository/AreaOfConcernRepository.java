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
    Page<AreaOfConcern> findByInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date lastModifiedDateTime, Pageable pageable);
    AreaOfConcern findByReference(String reference);

    @RestResource(path = "forChecklist", rel = "forChecklist")
    Page<AreaOfConcern> findByChecklistsUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("checklistUuid") UUID checklistUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<AreaOfConcern> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByChecklist", rel = "findByChecklist")
    Page<AreaOfConcern> findByChecklistsId(@Param("checklistId") Integer checklistId, Pageable pageable);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<AreaOfConcern> findByChecklistsAssessmentToolId(@Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    @RestResource(path = "find", rel = "find")
    Page<AreaOfConcern> findAllByChecklistsId(@Param("checklistId") Integer checklistId, Pageable pageable);
}