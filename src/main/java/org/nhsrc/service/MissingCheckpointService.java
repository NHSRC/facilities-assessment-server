package org.nhsrc.service;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.missing.FacilityAssessmentMissingCheckpoint;
import org.nhsrc.domain.missing.MissingCheckpoint;
import org.nhsrc.repository.missing.FacilityAssessmentMissingCheckpointRepository;
import org.nhsrc.repository.missing.MissingCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissingCheckpointService {
    private MissingCheckpointRepository missingCheckpointRepository;
    private FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository;

    @Autowired
    public MissingCheckpointService(MissingCheckpointRepository missingCheckpointRepository, FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository) {
        this.missingCheckpointRepository = missingCheckpointRepository;
        this.facilityAssessmentMissingCheckpointRepository = facilityAssessmentMissingCheckpointRepository;
    }

    public void saveMissingCheckpoint(String missingCheckpointName, Checklist checklist, FacilityAssessment facilityAssessment) {
        MissingCheckpoint missingCheckpoint = missingCheckpointRepository.findByNameAndChecklist(missingCheckpointName, checklist);
        if (missingCheckpoint == null) {
            missingCheckpoint = new MissingCheckpoint();
            missingCheckpoint.setName(missingCheckpointName);
            missingCheckpoint = missingCheckpointRepository.save(missingCheckpoint);
        }

        FacilityAssessmentMissingCheckpoint facilityAssessmentMissingCheckpoint = new FacilityAssessmentMissingCheckpoint();
        facilityAssessmentMissingCheckpoint.setFacilityAssessment(facilityAssessment);
        facilityAssessmentMissingCheckpoint.setMissingCheckpoint(missingCheckpoint);
        facilityAssessmentMissingCheckpointRepository.save(facilityAssessmentMissingCheckpoint);
    }

    public void clearMissingCheckpoints(FacilityAssessment facilityAssessment, Checklist checklist) {
        List<FacilityAssessmentMissingCheckpoint> missing = facilityAssessmentMissingCheckpointRepository.findAllByFacilityAssessmentAndMissingCheckpointChecklist(facilityAssessment, checklist);
        facilityAssessmentMissingCheckpointRepository.delete(missing);
    }
}