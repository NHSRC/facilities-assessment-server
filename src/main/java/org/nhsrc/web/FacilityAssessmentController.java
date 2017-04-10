package org.nhsrc.web;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.service.FacilityAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facility-assessment")
public class FacilityAssessmentController {

    private final FacilityAssessmentService facilityAssessmentService;

    @Autowired
    public FacilityAssessmentController(FacilityAssessmentService facilityAssessmentService) {
        this.facilityAssessmentService = facilityAssessmentService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<FacilityAssessment> syncFacilityAssessment(@RequestBody FacilityAssessmentDTO facilityAssessmentDTO) {
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentDTO);
        return new ResponseEntity<>(facilityAssessment, HttpStatus.CREATED);
    }
}
