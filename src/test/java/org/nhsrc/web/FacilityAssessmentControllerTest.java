package org.nhsrc.web;

import org.junit.Ignore;
import org.junit.Test;
import org.nhsrc.common.AbstractWebIntegrationTest;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.AssessmentType;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentAppDTO;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.AssessmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

public class FacilityAssessmentControllerTest extends AbstractWebIntegrationTest {
    @Autowired
    private AssessmentToolRepository assessmentToolRepository;
    @Autowired
    private AssessmentTypeRepository assessmentTypeRepository;

    @Test @Ignore
    public void checkFacilityAssessmentSubmit() {
        AssessmentTool assessmentTool = assessmentToolRepository.findByName("District Hospital (DH)");
        AssessmentType assessmentType = assessmentTypeRepository.findByName("Internal");
        FacilityAssessmentAppDTO facilityAssessmentAppDTO = new FacilityAssessmentAppDTO();
        facilityAssessmentAppDTO.setUuid(UUID.randomUUID());
        facilityAssessmentAppDTO.setFacilityName("Foo");
        facilityAssessmentAppDTO.setAssessmentTool(assessmentTool.getUuid());
        facilityAssessmentAppDTO.setAssessmentTypeUUID(assessmentType.getUuid());
        facilityAssessmentAppDTO.setStartDate(new Date());
        facilityAssessmentAppDTO.setEndDate(new Date());
        facilityAssessmentAppDTO.setDeviceId("Bar");
        testRestTemplate.postForEntity("/api/facility-assessment", facilityAssessmentAppDTO, FacilityAssessment.class);
    }
}