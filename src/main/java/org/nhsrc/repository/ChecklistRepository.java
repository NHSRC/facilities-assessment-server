package org.nhsrc.repository;

import org.nhsrc.domain.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "checklist", path = "checklist")
public interface ChecklistRepository extends BaseRepository<Checklist> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Checklist> findByInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "forAssessmentTool", rel = "forAssessmentTool")
    Page<Checklist> findByAssessmentToolUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("assessmentToolUuid") UUID assessmentToolUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    Checklist findByNameAndAssessmentToolUuid(String name, UUID assessmentToolUuid);
}
