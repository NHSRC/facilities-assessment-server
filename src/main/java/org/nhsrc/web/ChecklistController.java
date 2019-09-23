package org.nhsrc.web;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.State;
import org.nhsrc.repository.*;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.web.contract.ChecklistRequest;
import org.nhsrc.web.contract.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class ChecklistController {
    private DepartmentRepository departmentRepository;
    private ChecklistRepository checklistRepository;
    private AssessmentToolRepository assessmentToolRepository;
    private StateRepository stateRepository;
    private CheckpointRepository checkpointRepository;
    private ChecklistService checklistService;

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
            if (checkpointRepository.countAllByStateAndChecklist(state, checklist) != checkpointRepository.countAllByChecklist(checklist))
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(String.format("This checklist cannot be moved to the state: %s because it has checkpoints belonging to common or to another state", state.getName())));
        }
        checklist.setState(state);

        checklist.setName(checklistRequest.getName());
        checklist.setDepartment(Repository.findByUuidOrId(checklistRequest.getDepartmentUUID(), checklistRequest.getDepartmentId(), departmentRepository));
        checklist.setInactive(checklistRequest.getInactive());
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
}