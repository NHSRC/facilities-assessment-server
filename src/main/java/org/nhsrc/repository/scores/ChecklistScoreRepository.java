package org.nhsrc.repository.scores;

import org.nhsrc.domain.scores.ChecklistScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistScoreRepository extends PagingAndSortingRepository<ChecklistScore, Integer> {
}