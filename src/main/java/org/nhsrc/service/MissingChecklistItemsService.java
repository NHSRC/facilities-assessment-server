package org.nhsrc.service;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.missing.FacilityAssessmentMissingCheckpoint;
import org.nhsrc.domain.missing.MissingChecklist;
import org.nhsrc.domain.missing.MissingCheckpoint;
import org.nhsrc.repository.missing.FacilityAssessmentMissingCheckpointRepository;
import org.nhsrc.repository.missing.MissingChecklistRepository;
import org.nhsrc.repository.missing.MissingCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissingChecklistItemsService {
    private MissingCheckpointRepository missingCheckpointRepository;
    private FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository;
    private MissingChecklistRepository missingChecklistRepository;

    @Autowired
    public MissingChecklistItemsService(MissingCheckpointRepository missingCheckpointRepository, FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository, MissingChecklistRepository missingChecklistRepository) {
        this.missingCheckpointRepository = missingCheckpointRepository;
        this.facilityAssessmentMissingCheckpointRepository = facilityAssessmentMissingCheckpointRepository;
        this.missingChecklistRepository = missingChecklistRepository;
    }

    void saveMissingCheckpoint(String missingCheckpointName, String measurableElementReference, Checklist checklist, FacilityAssessment facilityAssessment) {
        MissingCheckpoint missingCheckpoint = missingCheckpointRepository.findByNameAndMeasurableElementReferenceAndChecklist(missingCheckpointName, measurableElementReference, checklist);
        if (missingCheckpoint == null)
            missingCheckpoint = missingCheckpointRepository.findByNameAndChecklist(missingCheckpointName, checklist);

        if (missingCheckpoint == null) {
            missingCheckpoint = new MissingCheckpoint();
            missingCheckpoint.setName(missingCheckpointName);
            missingCheckpoint.setChecklist(checklist);
            missingCheckpoint.setMeasurableElementReference(measurableElementReference);
            missingCheckpoint = missingCheckpointRepository.save(missingCheckpoint);
        }

        FacilityAssessmentMissingCheckpoint facilityAssessmentMissingCheckpoint = facilityAssessmentMissingCheckpointRepository.findByFacilityAssessmentAndMissingCheckpoint(facilityAssessment, missingCheckpoint);
        if (facilityAssessmentMissingCheckpoint == null) {
            facilityAssessmentMissingCheckpoint = new FacilityAssessmentMissingCheckpoint();
            facilityAssessmentMissingCheckpoint.setFacilityAssessment(facilityAssessment);
            facilityAssessmentMissingCheckpoint.setMissingCheckpoint(missingCheckpoint);
            facilityAssessmentMissingCheckpointRepository.save(facilityAssessmentMissingCheckpoint);
        }
    }

    void clearMissingCheckpoints(FacilityAssessment facilityAssessment, Checklist checklist) {
        List<FacilityAssessmentMissingCheckpoint> missing = facilityAssessmentMissingCheckpointRepository.findAllByFacilityAssessmentAndMissingCheckpointChecklist(facilityAssessment, checklist);
        facilityAssessmentMissingCheckpointRepository.delete(missing);
    }

    void clearMissingChecklists(FacilityAssessment facilityAssessment) {
        missingChecklistRepository.delete(missingChecklistRepository.findAllByFacilityAssessment(facilityAssessment));
    }
}