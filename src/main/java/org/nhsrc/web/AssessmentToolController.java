package org.nhsrc.web;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.AssessmentToolType;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.AssessmentToolRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class AssessmentToolController {
    private AssessmentToolRepository assessmentToolRepository;
    private AssessmentToolModeRepository assessmentToolModeRepository;

    @Autowired
    public AssessmentToolController(AssessmentToolRepository assessmentToolRepository, AssessmentToolModeRepository assessmentToolModeRepository) {
        this.assessmentToolRepository = assessmentToolRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
    }

    @RequestMapping(value = "/assessmentTools", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentTool save(@RequestBody AssessmentToolRequest request) {
        AssessmentTool assessmentTool = Repository.findByUuidOrCreate(request.getUuid(), assessmentToolRepository, new AssessmentTool());
        AssessmentToolMode assessmentToolMode = Repository.findById(request.getAssessmentToolModeId(), assessmentToolModeRepository);
        assessmentTool.setName(request.getName());
        assessmentTool.setAssessmentToolMode(assessmentToolMode);
        assessmentTool.setInactive(request.getInactive());
        if (request.getAssessmentType() == null || request.getAssessmentType().isEmpty()) {
            assessmentTool.setAssessmentToolType(AssessmentToolType.COMPLIANCE);
        } else {
            assessmentTool.setAssessmentToolType(AssessmentToolType.INDICATOR);
        }
        return assessmentToolRepository.save(assessmentTool);
    }
}