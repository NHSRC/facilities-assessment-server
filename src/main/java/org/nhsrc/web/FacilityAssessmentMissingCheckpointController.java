package org.nhsrc.web;

import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.missing.FacilityAssessmentMissingCheckpoint;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.missing.FacilityAssessmentMissingCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class FacilityAssessmentMissingCheckpointController {
    private FacilityAssessmentMissingCheckpointRepository repository;
    private FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public FacilityAssessmentMissingCheckpointController(FacilityAssessmentMissingCheckpointRepository repository, FacilityAssessmentRepository facilityAssessmentRepository) {
        this.repository = repository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    @RequestMapping(value = "facilityAssessmentMissingCheckpoint", method = {RequestMethod.GET})
    public Page<FacilityAssessmentMissingCheckpoint> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @RequestMapping(value = "facilityAssessmentMissingCheckpoint/search/findByFacilityAssessment", method = {RequestMethod.GET})
    public Page<FacilityAssessmentMissingCheckpoint> findByFacilityAssessmentId(@RequestParam("facilityAssessmentId") Integer facilityAssessmentId, Pageable pageable) {
        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findOne(facilityAssessmentId);
        return repository.findAllByFacilityAssessment(facilityAssessment, pageable);
    }
}