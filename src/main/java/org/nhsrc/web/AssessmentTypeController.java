package org.nhsrc.web;

import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.AssessmentType;
import org.nhsrc.repository.AssessmentToolModeRepository;
import org.nhsrc.repository.AssessmentTypeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AssessmentTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class AssessmentTypeController {
    private AssessmentTypeRepository assessmentTypeRepository;
    private AssessmentToolModeRepository assessmentToolModeRepository;

    @Autowired
    public AssessmentTypeController(AssessmentTypeRepository assessmentTypeRepository, AssessmentToolModeRepository assessmentToolModeRepository) {
        this.assessmentTypeRepository = assessmentTypeRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
    }

    @RequestMapping(value = "/assessmentTypes", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentType save(@RequestBody AssessmentTypeRequest request) {
        AssessmentType assessmentType = Repository.findByUuidOrCreate(request.getUuid(), assessmentTypeRepository, new AssessmentType());
        assessmentType.setName(request.getName());
        assessmentType.setShortName(request.getShortName());
        assessmentType.setInactive(request.getInactive());
        AssessmentToolMode assessmentToolMode = Repository.findById(request.getAssessmentToolModeId(), assessmentToolModeRepository);
        assessmentType.setAssessmentToolMode(assessmentToolMode);
        return assessmentTypeRepository.save(assessmentType);
    }

    @RequestMapping(value = "/assessmentTypes/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentType delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, assessmentTypeRepository);
    }
}