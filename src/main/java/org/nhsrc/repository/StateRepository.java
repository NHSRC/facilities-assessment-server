package org.nhsrc.repository;

import org.nhsrc.domain.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface StateRepository extends PagingAndSortingRepository<State, Long> {
    @Query(value = "SELECT s FROM State s WHERE s.uuid=?1")
    State find(UUID uuid);
}
