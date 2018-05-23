package org.nhsrc.repository;

import org.nhsrc.domain.Checkpoint;
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
@RepositoryRestResource(collectionResourceRel = "checkpoint", path = "checkpoint")
public interface CheckpointRepository extends BaseRepository<Checkpoint> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Checkpoint> findByInactiveFalseAndLastModifiedDateGreaterThanAndInactiveFalseOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "forMeasurableElementAndChecklist", rel = "forMeasurableElementAndChecklist")
    Page<Checkpoint> findByMeasurableElementUuidAndChecklistUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("checklistUuid") UUID checklistUuid, @Param("measurableElementUuid") UUID measurableElementUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    List<Checkpoint> findAllByNameAndChecklistUuidAndMeasurableElementName(String name, UUID checklistUuid, String measurableElementName);
}
