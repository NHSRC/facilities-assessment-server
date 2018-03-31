package org.nhsrc.repository.scores;

import org.nhsrc.domain.scores.AreaOfConcernScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaOfConcernScoreRepository extends PagingAndSortingRepository<AreaOfConcernScore, Integer> {
}