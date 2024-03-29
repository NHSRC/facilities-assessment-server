package org.nhsrc.web;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.MeasurableElement;
import org.nhsrc.domain.State;
import org.nhsrc.mapper.AssessmentToolComponentMapper;
import org.nhsrc.repository.*;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.web.contract.ChecklistRequest;
import org.nhsrc.web.contract.ErrorResponse;
import org.nhsrc.web.contract.ext.AssessmentToolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class ChecklistController {
    private final DepartmentRepository departmentRepository;
    private final ChecklistRepository checklistRepository;
    private final AssessmentToolRepository assessmentToolRepository;
    private final StateRepository stateRepository;
    private final CheckpointRepository checkpointRepository;
    private final ChecklistService checklistService;

    @Autowired
    public ChecklistController(DepartmentRepository departmentRepository, ChecklistRepository checklistRepository, AssessmentToolRepository assessmentToolRepository, StateRepository stateRepository, CheckpointRepository checkpointRepository, ChecklistService checklistService) {
        this.departmentRepository = departmentRepository;
        this.checklistRepository = checklistRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.stateRepository = stateRepository;
        this.checkpointRepository = checkpointRepository;
        this.checklistService = checklistService;
    }

    @RequestMapping(value = "checklists", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public ResponseEntity save(@RequestBody ChecklistRequest checklistRequest) {
        State state = null;
        Checklist checklist = Repository.findByUuidOrCreate(checklistRequest.getUuid(), checklistRepository, new Checklist());
        // Checklist doesn't belong to a state but is being moved to a state
        if (!checklist.isNew() && checklistRequest.getStateId() != null && checklistRequest.getStateId() != 0) {
            // ...and the checklist has checkpoints belonging to common or other state (or doesn't have all checkpoints belonging to the to be state)
            state = stateRepository.findOne(checklistRequest.getStateId());
            if (checkpointRepository.countAllByChecklist(checklist) != checkpointRepository.countAllByChecklist(checklist))
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(String.format("This checklist cannot be moved to the state: %s because it has checkpoints belonging to common or to another state", state.getName())));
        }
        checklist.setState(state);

        checklist.setName(checklistRequest.getName());
        checklist.setDepartment(Repository.findByUuidOrId(checklistRequest.getDepartmentUUID(), checklistRequest.getDepartmentId(), departmentRepository));
        checklist.setInactive(checklistRequest.isInactive());
        checklistService.mergeAreaOfConcerns(checklist, new HashSet<>(checklistRequest.getAreaOfConcernIds()));
        checklist = checklistRepository.save(checklist);

        Checklist finalChecklist = checklist;
        checklistRequest.getAssessmentToolIds().forEach(assessmentToolId -> {
            AssessmentTool assessmentTool = Repository.findByUuidOrId(checklistRequest.getAssessmentToolUUID(), assessmentToolId, assessmentToolRepository);
            finalChecklist.addAssessmentTool(assessmentTool);
            assessmentToolRepository.save(assessmentTool);
        });
        return new ResponseEntity<>(checklist, HttpStatus.OK);
    }

    @RequestMapping(value = "checklist/search/find", method = {RequestMethod.GET})
    public List<Checklist> findAll(@RequestParam Integer assessmentToolId, @RequestParam Integer stateId, Pageable pageable) {
        Page<Checklist> checklists = checklistRepository.findByAssessmentToolsId(assessmentToolId, pageable);
        return checklists.getContent().stream().filter(checklist -> stateId.equals(checklist._getStateId()) || checklist._getStateId() == null).collect(Collectors.toList());
    }

    @RequestMapping(value = "checklist/search/findByFacilityAssessment", method = {RequestMethod.GET})
    public List<Checklist> findByFacilityAssessment(@RequestParam Integer facilityAssessmentId) {
        return checklistRepository.findUniqueChecklistsMissingInCheckpointsForFacilityAssessment(facilityAssessmentId).stream().distinct().sorted(Comparator.comparing(Checklist::getName)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/checklists/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public Checklist delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, checklistRepository);
    }

    @RequestMapping(value = "/checklist/search/lastModifiedByState", method = {RequestMethod.GET})
    public Page<Checklist> findLastModifiedByState(@RequestParam("name") String name, @RequestParam("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        List<Integer> checklists = checklistService.getChecklistsForState(name);
        return checklistRepository.findAllByIdInAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(checklists, lastModifiedDateTime, pageable);
    }

    @RequestMapping(value = "/ext/checklist", method = {RequestMethod.GET})
    public Page<AssessmentToolResponse.ChecklistResponse> getChecklists(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        Page<Checklist> checklists = checklistRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastModifiedDateTime, pageable);
        return checklists.map(AssessmentToolComponentMapper::mapChecklist);
    }
}
