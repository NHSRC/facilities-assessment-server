package org.nhsrc.repository;

import org.nhsrc.domain.FacilityAssessment;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FacilityAssessmentRepository extends BaseRepository<FacilityAssessment> {
}
