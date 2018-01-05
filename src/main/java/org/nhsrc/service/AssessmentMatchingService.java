package org.nhsrc.service;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssessmentMatchingService {
    private final FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public AssessmentMatchingService(FacilityAssessmentRepository facilityAssessmentRepository) {
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    public FacilityAssessment findExistingAssessment(String seriesName, UUID facilityAssessmentUUID, Facility facility, AssessmentTool assessmentTool) {
        if (facilityAssessmentUUID != null)
            return facilityAssessmentRepository.findByUuid(facilityAssessmentUUID);

        if (seriesName != null && !seriesName.isEmpty())
            return facilityAssessmentRepository.findByFacilityAndAssessmentToolAndSeriesName(facility, assessmentTool, seriesName);

        return null;
    }
}
