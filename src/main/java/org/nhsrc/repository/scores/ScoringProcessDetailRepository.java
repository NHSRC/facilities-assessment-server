package org.nhsrc.repository.scores;

import org.nhsrc.domain.scores.ScoringProcessDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScoringProcessDetailRepository extends PagingAndSortingRepository<ScoringProcessDetail, Integer> {
    ScoringProcessDetail findByUuid(UUID uuid);
}