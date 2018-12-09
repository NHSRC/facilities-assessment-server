package org.nhsrc.web;

import org.nhsrc.domain.Checklist;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.ChecklistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        Checklist checklist;
        if (checklistRequest.getUuid() == null) {
            checklist = new Checklist();
            checklist.setUuid(UUID.randomUUID());
        } else {
            checklist = checklistRepository.findByUuid(UUID.fromString(checklistRequest.getUuid()));
        }
        if (checklist == null) {
            checklist = new Checklist();
            checklist.setUuid(UUID.fromString(checklistRequest.getUuid()));
        }

        checklist.setName(checklistRequest.getName());

        checklist.setDepartment(Repository.findByUuidOrId(checklistRequest.getDepartmentUUID(), checklistRequest.getDepartmentId(), departmentRepository));
        checklist.setAssessmentTool(Repository.findByUuidOrId(checklistRequest.getAssessmentToolUUID(), checklistRequest.getAssessmentToolId(), assessmentToolRepository));

        String[] areasOfConcernUUIDs = checklistRequest.getAreasOfConcernUUIDs();
        for (String areaOfConcernUUID : areasOfConcernUUIDs) {
            checklist.addAreaOfConcern(areaOfConcernRepository.findByUuid(UUID.fromString(areaOfConcernUUID)));
        }

        return checklistRepository.save(checklist);
    }
}