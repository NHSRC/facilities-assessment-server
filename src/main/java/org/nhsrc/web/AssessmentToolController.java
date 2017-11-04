package org.nhsrc.web;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.Facility;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.AssessmentToolRequest;
import org.nhsrc.web.contract.FacilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.UUID;

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

    @RequestMapping(value = "/assessmentTools", method = RequestMethod.POST)
    @Transactional
    void save(@RequestBody AssessmentToolRequest assessmentToolRequest) {
        AssessmentToolMode assessmentToolMode = assessmentToolModeRepository.findByName(assessmentToolRequest.getMode());
        if (assessmentToolMode == null) {
            assessmentToolMode = new AssessmentToolMode();
            assessmentToolMode.setName(assessmentToolRequest.getMode());
            assessmentToolMode.setUuid(UUID.randomUUID());
            assessmentToolModeRepository.save(assessmentToolMode);
        }

        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(UUID.fromString(assessmentToolRequest.getUuid()));
        if (assessmentTool == null) {
            assessmentTool = new AssessmentTool();
            assessmentTool.setUuid(UUID.fromString(assessmentToolRequest.getUuid()));
        }
        assessmentTool.setName(assessmentToolRequest.getName());
        assessmentTool.setAssessmentToolMode(assessmentToolMode);
        assessmentToolRepository.save(assessmentTool);
    }
}