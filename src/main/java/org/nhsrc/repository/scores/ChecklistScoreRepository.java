package org.nhsrc.repository.scores;

import org.nhsrc.domain.scores.ChecklistScore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
@PreAuthorize("hasRole('Assessment_Read')")
public interface ChecklistScoreRepository extends PagingAndSortingRepository<ChecklistScore, Integer> {
    void deleteAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId);

    @Query("SELECT SUM((cs.numerator * 100.0) /cs.denominator) from ChecklistScore cs join cs.checklist c join cs.facilityAssessment fa where c.name = ? and cs.facilityAssessment.id = ?")
    double getChecklistScore(String checklistName, int facilityAssessmentId);
}
