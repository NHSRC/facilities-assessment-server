package org.nhsrc.repository.scores;

import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.Standard;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.domain.scores.StandardScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardScoreRepository extends PagingAndSortingRepository<StandardScore, Integer> {
    void deleteAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId);
    List<StandardScore> findAllByFacilityAssessmentAndStandardAreaOfConcernReferenceOrderByStandardAreaOfConcernReferenceAsc(FacilityAssessment facilityAssessment, String areaOfConcernReference);
    List<StandardScore> findAllByFacilityAssessmentOrderByStandardAreaOfConcernReferenceAsc(FacilityAssessment facilityAssessment);
}
