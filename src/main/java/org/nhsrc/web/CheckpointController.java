package org.nhsrc.web;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.MeasurableElement;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.CheckpointRepository;
import org.nhsrc.repository.MeasurableElementRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.CheckpointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/checkpoint/search/find", method = {RequestMethod.GET})
    public Page<Checkpoint> find(@RequestParam(value = "measurableElementId", required = false) Integer measurableElementId,
                                        @RequestParam(value = "standardId", required = false) Integer standardId,
                                        @RequestParam(value = "areaOfConcernId", required = false) Integer areaOfConcernId,
                                        @RequestParam(value = "checklistId", required = false) Integer checklistId,
                                        @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                                        Pageable pageable) {
        if (measurableElementId != null)
            return checkpointRepository.findByMeasurableElementId(measurableElementId, pageable);
        if (standardId != null)
            return checkpointRepository.findByMeasurableElementStandardId(standardId, pageable);
        if (areaOfConcernId != null)
            return checkpointRepository.findByMeasurableElementStandardAreaOfConcernId(areaOfConcernId, pageable);
        if (checklistId != null)
            return checkpointRepository.findByMeasurableElementStandardAreaOfConcernChecklistsId(checklistId, pageable);
        if (assessmentToolId != null)
            return checkpointRepository.findByMeasurableElementStandardAreaOfConcernChecklistsAssessmentToolId(assessmentToolId, pageable);
        return checkpointRepository.findAll(pageable);
    }
}