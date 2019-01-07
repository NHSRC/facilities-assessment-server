package org.nhsrc.web;

import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.repository.AssessmentToolModeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AssessmentToolModeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class AssessmentToolModeController {
    private final AssessmentToolModeRepository assessmentToolModeRepository;

    @Autowired
    public AssessmentToolModeController(AssessmentToolModeRepository assessmentToolModeRepository) {
        this.assessmentToolModeRepository = assessmentToolModeRepository;
    }

    @RequestMapping(value = "/assessmentToolModes", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public AssessmentToolMode save(@RequestBody AssessmentToolModeRequest request) {
        AssessmentToolMode assessmentToolMode = Repository.findByUuidOrCreate(request.getUuid(), assessmentToolModeRepository, new AssessmentToolMode());
        assessmentToolMode.setName(request.getName());
        assessmentToolMode.setInactive(request.getInactive());
        return assessmentToolModeRepository.save(assessmentToolMode);
    }
}