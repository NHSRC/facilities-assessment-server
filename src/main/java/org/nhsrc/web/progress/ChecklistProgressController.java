package org.nhsrc.web.progress;

import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.dto.AreaOfConcernProgressDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.service.AssessmentProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/checklistProgress")
public class ChecklistProgressController {
    private AssessmentProgressService assessmentProgressService;
    private FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public ChecklistProgressController(AssessmentProgressService assessmentProgressService, FacilityAssessmentRepository facilityAssessmentRepository) {
        this.assessmentProgressService = assessmentProgressService;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    @RequestMapping(value = "search/findByFacilityAssessment", method = RequestMethod.GET)
    @PreAuthorize("hasRole('Assessment_Read')")
    public ResponseEntity<List<AreaOfConcernProgressDTO>> getFacilityAssessmentProgress(@RequestParam("facilityAssessmentId") Integer assessmentId) {
        List<FacilityAssessment> facilityAssessments = new ArrayList<>();
        facilityAssessments.add(facilityAssessmentRepository.findById(assessmentId));
        return ResponseEntity.ok(assessmentProgressService.getProgressFor(facilityAssessments).get(0).getAreaOfConcernsProgress());
    }
}