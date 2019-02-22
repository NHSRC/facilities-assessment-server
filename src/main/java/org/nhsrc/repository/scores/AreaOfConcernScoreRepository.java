package org.nhsrc.repository.scores;

import org.nhsrc.domain.scores.AreaOfConcernScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaOfConcernScoreRepository extends PagingAndSortingRepository<AreaOfConcernScore, Integer> {
    void deleteAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId);
}