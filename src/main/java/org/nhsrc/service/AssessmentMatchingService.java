package org.nhsrc.service;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AssessmentMatchingService {
    private final FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public AssessmentMatchingService(FacilityAssessmentRepository facilityAssessmentRepository) {
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    public FacilityAssessment findMatching(FacilityAssessment facilityAssessment) {
        FacilityAssessment matchingAssessment =
                this.facilityAssessmentRepository
                        .findByFacilityAndAssessmentToolAndSeriesName(
                                facilityAssessment.getFacility(),
                                facilityAssessment.getAssessmentTool(),
                                facilityAssessment.getSeriesName());
        return matchingAssessment == null ? facilityAssessment : matchingAssessment;
    }
}
