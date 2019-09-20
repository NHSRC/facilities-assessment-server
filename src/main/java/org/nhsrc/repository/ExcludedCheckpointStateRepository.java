package org.nhsrc.repository;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.ExcludedCheckpointState;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@RepositoryRestResource(collectionResourceRel = "excludedCheckpointState", path = "excludedCheckpointState")
public interface ExcludedCheckpointStateRepository extends PagingAndSortingRepository<ExcludedCheckpointState, Integer>, BaseRepository<ExcludedCheckpointState> {
    ExcludedCheckpointState findFirstByCheckpointAndState(Checkpoint checkpoint, State state);

    @RestResource(path = "lastModifiedByState", rel = "lastModifiedByState")
    Page<ExcludedCheckpointState> findByStateNameAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("name") String name, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
}