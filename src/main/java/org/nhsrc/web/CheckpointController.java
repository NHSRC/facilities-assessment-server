package org.nhsrc.web;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.MeasurableElement;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.CheckpointRepository;
import org.nhsrc.repository.MeasurableElementRepository;
import org.nhsrc.repository.StandardRepository;
import org.nhsrc.web.contract.CheckpointRequest;
import org.nhsrc.web.contract.MeasurableElementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class CheckpointController {
    private CheckpointRepository checkpointRepository;
    private final MeasurableElementRepository measurableElementRepository;
    private ChecklistRepository checklistRepository;

    @Autowired
    public CheckpointController(CheckpointRepository checkpointRepository, MeasurableElementRepository measurableElementRepository, ChecklistRepository checklistRepository) {
        this.checkpointRepository = checkpointRepository;
        this.measurableElementRepository = measurableElementRepository;
        this.checklistRepository = checklistRepository;
    }

    @RequestMapping(value = "/checkpoints", method = RequestMethod.POST)
    @Transactional
    void save(@RequestBody CheckpointRequest checkpointRequest) {
        Checkpoint checkpoint = checkpointRepository.findByUuid(UUID.fromString(checkpointRequest.getUuid()));
        if (checkpoint == null) {
            checkpoint = new Checkpoint();
            checkpoint.setUuid(UUID.fromString(checkpointRequest.getUuid()));
        }
        checkpoint.setName(checkpointRequest.getName());
        checkpoint.setAssessmentMethodObservation(checkpointRequest.getAssessmentMethodObservation());
        checkpoint.setAssessmentMethodPatientInterview(checkpointRequest.getAssessmentMethodStaffInterview());
        checkpoint.setAssessmentMethodRecordReview(checkpointRequest.getAssessmentMethodPatientInterview());
        checkpoint.setAssessmentMethodStaffInterview(checkpointRequest.getAssessmentMethodRecordReview());
        checkpoint.setDefault(checkpointRequest.getDefault());
        checkpoint.setMeansOfVerification(checkpointRequest.getMeansOfVerification());
        checkpoint.setSortOrder(checkpointRequest.getSortOrder());
        checkpoint.setMeasurableElement(measurableElementRepository.findByUuid(UUID.fromString(checkpointRequest.getMeasurableElementUUID())));
        checkpoint.setChecklist(checklistRepository.findByUuid(UUID.fromString(checkpointRequest.getChecklistUUID())));
        checkpointRepository.save(checkpoint);
    }
}