package org.nhsrc.web;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.CheckpointRepository;
import org.nhsrc.repository.MeasurableElementRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.CheckpointRequest;
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

    @RequestMapping(value = "/checkpoints", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public Checkpoint save(@RequestBody CheckpointRequest request) {
        Checkpoint checkpoint = Repository.findByUuidOrCreate(request.getUuid(), checkpointRepository, new Checkpoint());
        checkpoint.setName(request.getName());
        checkpoint.setAssessmentMethodObservation(request.getAssessmentMethodObservation());
        checkpoint.setAssessmentMethodPatientInterview(request.getAssessmentMethodStaffInterview());
        checkpoint.setAssessmentMethodRecordReview(request.getAssessmentMethodPatientInterview());
        checkpoint.setAssessmentMethodStaffInterview(request.getAssessmentMethodRecordReview());
        checkpoint.setMeansOfVerification(request.getMeansOfVerification());
        checkpoint.setSortOrder(request.getSortOrder());
        checkpoint.setMeasurableElement(Repository.findByUuidOrId(request.getMeasurableElementUUID(), request.getMeasurableElementId(), measurableElementRepository));
        checkpoint.setChecklist(Repository.findByUuidOrId(request.getChecklistUUID(), request.getChecklistId(), checklistRepository));
        return checkpointRepository.save(checkpoint);
    }
}