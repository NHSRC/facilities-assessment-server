package org.nhsrc.web;

import org.junit.Ignore;
import org.junit.Test;
import org.nhsrc.common.AbstractWebIntegrationTest;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.AssessmentType;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.AssessmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
        FacilityAssessmentDTO facilityAssessmentDTO = new FacilityAssessmentDTO();
        facilityAssessmentDTO.setUuid(UUID.randomUUID());
        facilityAssessmentDTO.setFacilityName("Foo");
        facilityAssessmentDTO.setAssessmentTool(assessmentTool.getUuid());
        facilityAssessmentDTO.setAssessmentTypeUUID(assessmentType.getUuid());
        facilityAssessmentDTO.setStartDate(new Date());
        facilityAssessmentDTO.setEndDate(new Date());
        facilityAssessmentDTO.setDeviceId("Bar");
        testRestTemplate.postForEntity("/api/facility-assessment", facilityAssessmentDTO, FacilityAssessment.class);
    }
}