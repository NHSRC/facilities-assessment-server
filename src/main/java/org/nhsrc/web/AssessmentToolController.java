package org.nhsrc.web;

import org.nhsrc.domain.*;
import org.nhsrc.repository.*;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.web.contract.AssessmentToolRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class AssessmentToolController {
    private final ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository;
    private StateRepository stateRepository;
    private ChecklistService checklistService;
    private AssessmentToolRepository assessmentToolRepository;
    private AssessmentToolModeRepository assessmentToolModeRepository;
    private ChecklistRepository checklistRepository;

    @Autowired
    public AssessmentToolController(AssessmentToolRepository assessmentToolRepository, AssessmentToolModeRepository assessmentToolModeRepository, ChecklistRepository checklistRepository, ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository, StateRepository stateRepository, ChecklistService checklistService) {
        this.assessmentToolRepository = assessmentToolRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
        this.checklistRepository = checklistRepository;
        this.excludedAssessmentToolStateRepository = excludedAssessmentToolStateRepository;
        this.stateRepository = stateRepository;
        this.checklistService = checklistService;
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
        Repository.mergeChildren(request.getChecklistIds(), assessmentTool.getChecklistIds(), checklistRepository, checklist -> assessmentTool.removeChecklist((Checklist) checklist), checklist -> assessmentTool.addChecklist((Checklist) checklist));

        Set<State> incidentExcludedStates = Repository.findByIds(request.getExcludedStateIds(), stateRepository);
        Set<ExcludedAssessmentToolState> excludedAssessmentToolStates;
        if (assessmentTool.isNew()) {
            excludedAssessmentToolStates = incidentExcludedStates.stream().map(state -> new ExcludedAssessmentToolState(assessmentTool, state)).collect(Collectors.toSet());
        } else {
            excludedAssessmentToolStates = incidentExcludedStates.stream().map(state -> {
                ExcludedAssessmentToolState excludedAssessmentToolState = excludedAssessmentToolStateRepository.findFirstByAssessmentToolAndState(assessmentTool, state);
                if (excludedAssessmentToolState == null) excludedAssessmentToolState = new ExcludedAssessmentToolState(assessmentTool, state);
                else excludedAssessmentToolState.setInactive(false);
                return excludedAssessmentToolState;
            }).collect(Collectors.toSet());
        }
        assessmentTool.setStateApplicability(Repository.findById(request.getStateId(), stateRepository), excludedAssessmentToolStates);

        return assessmentToolRepository.save(assessmentTool);
    }

    @RequestMapping(value = "/assessmentTool/search/findByState", method = {RequestMethod.GET})
    public List<AssessmentTool> findByState(@RequestParam(value = "stateId", required = false) Integer stateId) {
        return checklistService.getAssessmentToolsForState(stateId);
    }
}