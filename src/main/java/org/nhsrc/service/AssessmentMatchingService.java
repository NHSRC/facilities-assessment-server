package org.nhsrc.service;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssessmentMatchingService {
    private final FacilityAssessmentRepository facilityAssessmentRepository;
    private static Logger logger = LoggerFactory.getLogger(AssessmentMatchingService.class);

    @Autowired
    public AssessmentMatchingService(FacilityAssessmentRepository facilityAssessmentRepository) {
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    public FacilityAssessment findExistingAssessment(String seriesName, UUID facilityAssessmentUUID, Facility facility, AssessmentTool assessmentTool) {
        FacilityAssessment facilityAssessment = null;
        if (facilityAssessmentUUID != null) {
            facilityAssessment = facilityAssessmentRepository.findByUuid(facilityAssessmentUUID);
            logger.info(String.format("%s assessment based on UUID=%s", facilityAssessment == null ? "Not found" : "Found", facilityAssessmentUUID));
        }

        if (facilityAssessment != null) return facilityAssessment;

        if (seriesName != null && !seriesName.isEmpty()) {
            facilityAssessment = facilityAssessmentRepository.findByFacilityAndAssessmentToolAndSeriesName(facility, assessmentTool, seriesName.trim());
            logger.info(String.format("%s assessment based on Series=%s", facilityAssessment == null ? "Not found" : "Found", seriesName));
            return facilityAssessment;
        }

        logger.debug("No matching assessment found");
        return null;
    }
}
