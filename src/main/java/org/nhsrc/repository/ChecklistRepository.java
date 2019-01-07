package org.nhsrc.repository;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.Standard;
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

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "checklist", path = "checklist")
public interface ChecklistRepository extends BaseRepository<Checklist> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Checklist> findByInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "forAssessmentTool", rel = "forAssessmentTool")
    Page<Checklist> findByAssessmentToolUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("assessmentToolUuid") UUID assessmentToolUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "byAssessmentTool", rel = "byAssessmentTool")
    Page<Checklist> findByAssessmentToolNameAndInactiveFalseOrderByName(@Param("assessmentToolName") String assessmentToolName, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Checklist> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<Checklist> findByAssessmentToolId(@Param("assessmentToolId") int assessmentToolId, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<Checklist> findByStateId(@Param("stateId") int stateId, Pageable pageable);

    @RestResource(path = "find", rel = "find")
    Page<Checklist> findByStateIdAndAssessmentToolId(@Param("stateId") Integer stateId, @Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    @Query("SELECT c FROM Checklist c inner join c.assessmentTool as at WHERE (c.state.id = :stateId or c.state is null) and c.assessmentTool.id = :assessmentToolId order by at.name, c.name")
    Page<Checklist> findByAssessmentToolIdAndStateIdOrStateIsNull(@Param("assessmentToolId") Integer assessmentToolId, @Param("stateId") Integer stateId, Pageable pageable);
}
