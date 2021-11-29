package org.nhsrc.repository.scores;

import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.domain.scores.ChecklistScore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
@PreAuthorize("hasRole('Assessment_Read')")
public interface ChecklistScoreRepository extends PagingAndSortingRepository<ChecklistScore, Integer> {
    void deleteAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId);
    ChecklistScore findByChecklistNameAndFacilityAssessment(String checklistName, FacilityAssessment facilityAssessment);
}
