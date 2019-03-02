package org.nhsrc.web;

import org.nhsrc.domain.AssessmentType;
import org.nhsrc.repository.AssessmentTypeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AssessmentTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class AssessmentTypeController {
    private AssessmentTypeRepository assessmentTypeRepository;

    @Autowired
    public AssessmentTypeController(AssessmentTypeRepository assessmentTypeRepository) {
        this.assessmentTypeRepository = assessmentTypeRepository;
    }

    @RequestMapping(value = "/assessmentTypes", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentType save(@RequestBody AssessmentTypeRequest request) {
        AssessmentType assessmentType = Repository.findByUuidOrCreate(request.getUuid(), assessmentTypeRepository, new AssessmentType());
        assessmentType.setName(request.getName());
        assessmentType.setShortName(request.getShortName());
        assessmentType.setInactive(request.getInactive());
        return assessmentTypeRepository.save(assessmentType);
    }
}