package org.nhsrc.web;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Checkpoint;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.ChecklistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class ChecklistController {
    private DepartmentRepository departmentRepository;
    private ChecklistRepository checklistRepository;
    private AssessmentToolRepository assessmentToolRepository;
    private AreaOfConcernRepository areaOfConcernRepository;

    @Autowired
    public ChecklistController(DepartmentRepository departmentRepository, ChecklistRepository checklistRepository, AssessmentToolRepository assessmentToolRepository, AreaOfConcernRepository areaOfConcernRepository) {
        this.departmentRepository = departmentRepository;
        this.checklistRepository = checklistRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.areaOfConcernRepository = areaOfConcernRepository;
    }

    @RequestMapping(value = "checklists", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public Checklist save(@RequestBody ChecklistRequest checklistRequest) {
        Checklist checklist = Repository.findByUuidOrCreate(checklistRequest.getUuid(), checklistRepository, new Checklist());
        checklist.setName(checklistRequest.getName());
        checklist.setDepartment(Repository.findByUuidOrId(checklistRequest.getDepartmentUUID(), checklistRequest.getDepartmentId(), departmentRepository));
        checklist.setAssessmentTool(Repository.findByUuidOrId(checklistRequest.getAssessmentToolUUID(), checklistRequest.getAssessmentToolId(), assessmentToolRepository));

        String[] areasOfConcernUUIDs = checklistRequest.getAreasOfConcernUUIDs();
        for (String areaOfConcernUUID : areasOfConcernUUIDs) {
            checklist.addAreaOfConcern(areaOfConcernRepository.findByUuid(UUID.fromString(areaOfConcernUUID)));
        }

        return checklistRepository.save(checklist);
    }

    @RequestMapping(value = "/checklist/search/find", method = {RequestMethod.GET})
    public Page<Checklist> findAll(@RequestParam Integer assessmentToolId, @RequestParam Integer stateId, Pageable pageable) {
        return checklistRepository.findByAssessmentToolIdAndStateIdOrStateIsNull(assessmentToolId, stateId, pageable);
    }
}