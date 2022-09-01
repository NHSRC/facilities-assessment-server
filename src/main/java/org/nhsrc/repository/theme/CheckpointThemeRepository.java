package org.nhsrc.repository.theme;

import org.nhsrc.domain.CheckpointTheme;
import org.nhsrc.domain.Theme;
import org.nhsrc.repository.NonTxDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "checkpointTheme", path = "checkpointTheme")
public interface CheckpointThemeRepository extends NonTxDataRepository<CheckpointTheme> {
    Page<CheckpointTheme> findAllByCheckpointChecklistIdInAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(List<Integer> checklistIds, Date lastModifiedDate, Pageable pageable);
}
