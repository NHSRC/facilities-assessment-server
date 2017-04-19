package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
public interface FacilityAssessmentRepository extends BaseRepository<FacilityAssessment> {
    FacilityAssessment findByFacilityAndAssessmentToolAndStartDateBeforeAndStartDateAfter(Facility facility, AssessmentTool assessmentTool, Date startDateBefore, Date startDateAfter);
}
