package org.nhsrc.web;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.CheckpointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class CheckpointController {
    private CheckpointRepository checkpointRepository;
    private final MeasurableElementRepository measurableElementRepository;
    private ChecklistRepository checklistRepository;
    private StateRepository stateRepository;

    @Autowired
    public CheckpointController(CheckpointRepository checkpointRepository, MeasurableElementRepository measurableElementRepository, ChecklistRepository checklistRepository, StateRepository stateRepository) {
        this.checkpointRepository = checkpointRepository;
        this.measurableElementRepository = measurableElementRepository;
        this.checklistRepository = checklistRepository;
        this.stateRepository = stateRepository;
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
        checkpoint.setInactive(request.getInactive());
        checkpoint.setState(Repository.findById(request.getStateId(), stateRepository));
        return checkpointRepository.save(checkpoint);
    }

    @RequestMapping(value = "/checkpoint/search/find", method = {RequestMethod.GET})
    public Page<Checkpoint> find(@RequestParam(value = "stateId", required = false) Integer stateId,
                                 @RequestParam(value = "measurableElementId", required = false) Integer measurableElementId,
                                 @RequestParam(value = "standardId", required = false) Integer standardId,
                                 @RequestParam(value = "areaOfConcernId", required = false) Integer areaOfConcernId,
                                 @RequestParam(value = "checklistId", required = false) Integer checklistId,
                                 @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                                 Pageable pageable) {
        if (measurableElementId != null && checklistId != null)
            return checkpointRepository.findByMeasurableElementIdAndChecklistId(measurableElementId, checklistId, pageable);
        if (standardId != null && checklistId != null)
            return checkpointRepository.findByMeasurableElementStandardIdAndChecklistId(standardId, checklistId, pageable);
        if (areaOfConcernId != null && checklistId != null)
            return checkpointRepository.findByMeasurableElementStandardAreaOfConcernIdAndChecklistId(areaOfConcernId, checklistId, pageable);
        if (checklistId != null)
            return checkpointRepository.findAllByChecklistId(checklistId, pageable);
        if (assessmentToolId != null && stateId == null)
            return checkpointRepository.findByChecklistAssessmentToolId(assessmentToolId, pageable);
        if (assessmentToolId == null && stateId != null)
            return checkpointRepository.findByChecklistStateIdOrChecklistStateIsNull(stateId, pageable);
        if (assessmentToolId != null && stateId != null)
            return checkpointRepository.findByChecklistStateIdOrChecklistStateIsNullAndChecklistAssessmentToolId(stateId, assessmentToolId, pageable);
        return checkpointRepository.findAll(pageable);
    }

    @RequestMapping(value = "/checkpoint/search/findByCheckpointMeasurableElementIdAndChecklist", method = RequestMethod.GET)
    public Page<Checkpoint> findSiblings(@RequestParam(value = "checkpointMeasurableElementIdAndChecklistId") Integer checkpointId, Pageable pageable) {
        Checkpoint checkpoint = checkpointRepository.findOne(checkpointId);
        return checkpointRepository.findByChecklistIdAndMeasurableElementId(checkpoint.getChecklist().getId(), checkpoint.getMeasurableElement().getId(), pageable);
    }
}