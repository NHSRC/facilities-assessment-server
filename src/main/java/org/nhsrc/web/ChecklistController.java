package org.nhsrc.web;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.State;
import org.nhsrc.repository.*;
import org.nhsrc.repository.missing.FacilityAssessmentMissingCheckpointRepository;
import org.nhsrc.web.contract.ChecklistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class ChecklistController {
    private DepartmentRepository departmentRepository;
    private ChecklistRepository checklistRepository;
    private AssessmentToolRepository assessmentToolRepository;
    private AreaOfConcernRepository areaOfConcernRepository;
    private StateRepository stateRepository;
    private CheckpointRepository checkpointRepository;

    @Autowired
    public ChecklistController(DepartmentRepository departmentRepository, ChecklistRepository checklistRepository, AssessmentToolRepository assessmentToolRepository, AreaOfConcernRepository areaOfConcernRepository, FacilityAssessmentMissingCheckpointRepository facilityAssessmentMissingCheckpointRepository, StateRepository stateRepository, CheckpointRepository checkpointRepository) {
        this.departmentRepository = departmentRepository;
        this.checklistRepository = checklistRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.areaOfConcernRepository = areaOfConcernRepository;
        this.stateRepository = stateRepository;
        this.checkpointRepository = checkpointRepository;
    }

    @RequestMapping(value = "checklists", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public ResponseEntity save(@RequestBody ChecklistRequest checklistRequest) {
        State state = null;
        Checklist checklist = Repository.findByUuidOrCreate(checklistRequest.getUuid(), checklistRepository, new Checklist());
        // Checklist doesn't belong to a state but is being moved to a state
        if (!checklist.isNew() && checklistRequest.getStateId() != null && checklistRequest.getStateId() != 0) {
            // ...and the checklist has checkpoints belonging to common or other state (or doesn't have all checkpoints belonging to the to be state)
            state = stateRepository.findOne(checklistRequest.getStateId());
            if (checkpointRepository.countAllByStateAndChecklist(state, checklist) != checkpointRepository.countAllByChecklist(checklist))
                return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format("This checklist cannot be moved to the state: %s because it has checkpoints belonging to common or to another state", state.getName()));
        }
        checklist.setState(state);

        checklist.setName(checklistRequest.getName());
        checklist.setDepartment(Repository.findByUuidOrId(checklistRequest.getDepartmentUUID(), checklistRequest.getDepartmentId(), departmentRepository));
        checklist.setAssessmentTool(Repository.findByUuidOrId(checklistRequest.getAssessmentToolUUID(), checklistRequest.getAssessmentToolId(), assessmentToolRepository));

        Set<Integer> newAreasOfConcernIds = new HashSet<>(checklistRequest.getAreaOfConcernIds());
        HashSet<Integer> existingAreaOfConcernIds = new HashSet<>(checklist.getAreaOfConcernIds());
        existingAreaOfConcernIds.removeAll(newAreasOfConcernIds);
        existingAreaOfConcernIds.forEach(removedAreaOfConcern -> checklist.removeAreaOfConcern(areaOfConcernRepository.findOne(removedAreaOfConcern)));
        for (Integer areaOfConcernId : newAreasOfConcernIds) {
            checklist.addAreaOfConcern(areaOfConcernRepository.findOne(areaOfConcernId));
        }
        checklist.setInactive(checklistRequest.getInactive());

        return new ResponseEntity<>(checklistRepository.save(checklist), HttpStatus.OK);
    }

    @RequestMapping(value = "checklist/search/find", method = {RequestMethod.GET})
    public Page<Checklist> findAll(@RequestParam Integer assessmentToolId, @RequestParam Integer stateId, Pageable pageable) {
        return checklistRepository.findByAssessmentToolIdAndStateIdOrStateIsNull(assessmentToolId, stateId, pageable);
    }

    @RequestMapping(value = "checklist/search/findByFacilityAssessment", method = {RequestMethod.GET})
    public List<Checklist> findByFacilityAssessment(@RequestParam Integer facilityAssessmentId) {
        return checklistRepository.findUniqueChecklistsMissingInCheckpointsForFacilityAssessment(facilityAssessmentId).stream().distinct().sorted(Comparator.comparing(Checklist::getName)).collect(Collectors.toList());
    }
}