package org.nhsrc.web;

import org.nhsrc.domain.missing.FacilityAssessmentMissingCheckpoint;
import org.nhsrc.repository.missing.FacilityAssessmentMissingCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class MissingCheckpointController {
    private FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository;

    @Autowired
    public MissingCheckpointController(FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository) {
        this.facilityAssessmentMissingCheckpointRepository = facilityAssessmentMissingCheckpointRepository;
    }

    @RequestMapping(value = "/facilityAssessmentMissingCheckpoint/search/findByFacilityAssessmentId", method = {RequestMethod.GET})
    public Page<FacilityAssessmentMissingCheckpoint> find(Integer facilityAssessmentId, Pageable pageable) {
        return facilityAssessmentMissingCheckpointRepository.findAllByFacilityAssessmentId(facilityAssessmentId, pageable);
    }
}