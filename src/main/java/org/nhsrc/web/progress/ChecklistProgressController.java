package org.nhsrc.web.progress;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.dto.ChecklistProgressDTO;
import org.nhsrc.dto.FacilityAssessmentProgressDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.service.AssessmentProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ChecklistProgressDTO>> getFacilityAssessmentProgress(@RequestParam Integer assessmentId) {
        List<FacilityAssessment> facilityAssessments = new ArrayList<>();
        facilityAssessments.add(facilityAssessmentRepository.findById(assessmentId));
        return ResponseEntity.ok(assessmentProgressService.getProgressFor(facilityAssessments).get(0).getChecklistsProgress());
    }
}