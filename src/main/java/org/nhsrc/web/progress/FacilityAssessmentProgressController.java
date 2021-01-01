package org.nhsrc.web.progress;

import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.dto.FacilityAssessmentProgressDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.service.AssessmentProgressService;
import org.nhsrc.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/facilityAssessmentProgress/")
public class FacilityAssessmentProgressController {

    private AssessmentProgressService assessmentProgressService;
    private FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public FacilityAssessmentProgressController(AssessmentProgressService assessmentProgressService, FacilityAssessmentRepository facilityAssessmentRepository) {
        this.assessmentProgressService = assessmentProgressService;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    @RequestMapping(value = "search/lastModifiedByDeviceId", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public ResponseEntity getFacilityAssessmentProgress(@RequestParam String lastModifiedDate, @RequestParam String deviceId, Pageable pageable) throws ParseException {
        Page<FacilityAssessment> facilityAssessments = facilityAssessmentRepository.findByFacilityAssessmentDevicesDeviceIdAndLastModifiedDateGreaterThan(deviceId, DateUtils.ISO_8601_DATE_FORMAT.parse(lastModifiedDate), pageable);
        PageImpl body = new PageImpl<>(assessmentProgressService.getProgressFor(facilityAssessments.getContent()), pageable, facilityAssessments.getTotalElements());
        return ResponseEntity.ok(body);
    }

    @RequestMapping(value = "search/byAssessmentId", method = RequestMethod.GET)
    public ResponseEntity<List<FacilityAssessmentProgressDTO>> getFacilityAssessmentProgress(@RequestParam int assessmentId) {
        List<FacilityAssessment> facilityAssessments = new ArrayList<>();
        facilityAssessments.add(facilityAssessmentRepository.findById(assessmentId));
        return ResponseEntity.ok(assessmentProgressService.getProgressFor(facilityAssessments));
    }
}