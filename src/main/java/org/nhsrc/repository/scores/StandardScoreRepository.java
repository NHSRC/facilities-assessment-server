package org.nhsrc.repository.scores;

import org.nhsrc.domain.scores.StandardScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardScoreRepository extends PagingAndSortingRepository<StandardScore, Integer> {
}