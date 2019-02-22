package org.nhsrc.repository.scores;

import org.nhsrc.domain.scores.ChecklistScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistScoreRepository extends PagingAndSortingRepository<ChecklistScore, Integer> {
    void deleteAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId);
}