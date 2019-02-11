package org.nhsrc.repository.missing;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.missing.MissingCheckpoint;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingCheckpointRepository extends PagingAndSortingRepository<MissingCheckpoint, Integer> {
    MissingCheckpoint findByNameAndChecklist(@Param("name") String name, @Param("checklist") Checklist checklist);
}