package org.nhsrc.web;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.State;
import org.nhsrc.repository.*;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.web.contract.CheckpointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class CheckpointController {
    private CheckpointRepository checkpointRepository;
    private final MeasurableElementRepository measurableElementRepository;
    private ChecklistRepository checklistRepository;
    private StateRepository stateRepository;
    private ChecklistService checklistService;

    @Autowired
    public CheckpointController(CheckpointRepository checkpointRepository, MeasurableElementRepository measurableElementRepository, ChecklistRepository checklistRepository, StateRepository stateRepository, ChecklistService checklistService) {
        this.checkpointRepository = checkpointRepository;
        this.measurableElementRepository = measurableElementRepository;
        this.checklistRepository = checklistRepository;
        this.stateRepository = stateRepository;
        this.checklistService = checklistService;
    }

    @RequestMapping(value = "/checkpoints", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public Checkpoint save(@RequestBody CheckpointRequest request) {
        Checkpoint checkpoint = Repository.findByUuidOrCreate(request.getUuid(), checkpointRepository, new Checkpoint());
        checkpoint.setName(request.getName());
        checkpoint.setAssessmentMethodObservation(request.getAssessmentMethodObservation());
        checkpoint.setAssessmentMethodPatientInterview(request.getAssessmentMethodPatientInterview());
        checkpoint.setAssessmentMethodRecordReview(request.getAssessmentMethodRecordReview());
        checkpoint.setAssessmentMethodStaffInterview(request.getAssessmentMethodStaffInterview());
        checkpoint.setMeansOfVerification(request.getMeansOfVerification());
        checkpoint.setSortOrder(request.getSortOrder());
        checkpoint.setMeasurableElement(Repository.findByUuidOrId(request.getMeasurableElementUUID(), request.getMeasurableElementId(), measurableElementRepository));
        checkpoint.setChecklist(Repository.findByUuidOrId(request.getChecklistUUID(), request.getChecklistId(), checklistRepository));
        checkpoint.setOptional(request.isOptional());
        checkpoint.setInactive(request.getInactive());
        return checkpointRepository.save(checkpoint);
    }

    @RequestMapping(value = "/checkpoints", method = {RequestMethod.PATCH})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public List<Checkpoint> save(@RequestBody List<CheckpointRequest> requests) {
        List<Checkpoint> checkpoints = requests.stream().map(request -> {
            Checkpoint checkpoint = checkpointRepository.findOne(request.getId());
            checkpoint.setInactive(request.getInactive());
            return checkpoint;
        }).collect(Collectors.toList());
        checkpointRepository.save(checkpoints);
        return checkpoints;
    }

    @RequestMapping(value = "/checkpoint/search/find", method = {RequestMethod.GET})
    public Page<Checkpoint> find(@RequestParam(value = "stateId", required = false) Integer stateId,
                                 @RequestParam(value = "measurableElementId", required = false) Integer measurableElementId,
                                 @RequestParam(value = "standardId", required = false) Integer standardId,
                                 @RequestParam(value = "areaOfConcernId", required = false) Integer areaOfConcernId,
                                 @RequestParam(value = "checklistId", required = false) Integer checklistId,
                                 @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                                 @RequestParam(value = "inactive", required = false) Boolean inactive,
                                 Pageable pageable) {
        if (measurableElementId != null && checklistId != null)
            return checkpointRepository.findByMeasurableElementIdAndChecklistIdAndInactive(measurableElementId, checklistId, inactive, pageable);
        if (standardId != null && checklistId != null)
            return checkpointRepository.findByMeasurableElementStandardIdAndChecklistIdAndInactive(standardId, checklistId, inactive, pageable);
        if (areaOfConcernId != null && checklistId != null)
            return checkpointRepository.findByMeasurableElementStandardAreaOfConcernIdAndChecklistIdAndInactive(areaOfConcernId, checklistId, inactive, pageable);
        if (checklistId != null)
            return checkpointRepository.findAllByChecklistIdAndInactive(checklistId, inactive, pageable);
        if (assessmentToolId != null && stateId == null)
            return checkpointRepository.findByChecklistAssessmentToolsIdAndInactive(assessmentToolId, inactive, pageable);
        if (assessmentToolId == null && stateId != null)
            return checkpointRepository.findByInactiveAndChecklistStateIdOrChecklistStateIsNull(inactive, stateId, pageable);
        if (assessmentToolId != null && stateId != null)
            return checkpointRepository.findByChecklistStateIdOrChecklistStateIsNullAndChecklistAssessmentToolsIdAndInactive(stateId, assessmentToolId, inactive, pageable);
        return checkpointRepository.findAll(pageable);
    }

    @RequestMapping(value = "/checkpoint/search/findByCheckpointMeasurableElementIdAndChecklist", method = RequestMethod.GET)
    public Page<Checkpoint> findSiblings(@RequestParam(value = "checkpointMeasurableElementIdAndChecklistId") Integer checkpointId, Pageable pageable) {
        Checkpoint checkpoint = checkpointRepository.findOne(checkpointId);
        return checkpointRepository.findByChecklistIdAndMeasurableElementId(checkpoint.getChecklist().getId(), checkpoint.getMeasurableElement().getId(), pageable);
    }
    
    @RequestMapping(value = "/checkpoints/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public Checkpoint delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, checkpointRepository);
    }

    @RequestMapping(value = "/checkpoint/search/lastModifiedByState", method = {RequestMethod.GET})
    public Page<Checkpoint> findLastModifiedByState(@RequestParam("name") String name, @RequestParam("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        List<Integer> checklists = checklistService.getChecklistsForState(name);
        return checkpointRepository.findAllByChecklistIdInAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(checklists, lastModifiedDateTime, pageable);
    }
}