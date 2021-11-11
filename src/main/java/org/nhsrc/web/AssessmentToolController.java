package org.nhsrc.web;

import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.repository.*;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.utils.StringUtil;
import org.nhsrc.web.contract.AssessmentToolRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
        assessmentTool.setSortOrder(request.getSortOrder());
        if (StringUtil.isEmpty(request.getAssessmentType())) {
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

    @RequestMapping(value = "/assessmentTool/search/findByStateAndAssessmentToolMode", method = {RequestMethod.GET})
    public List<AssessmentTool> findByStateAndAssessmentTool(@RequestParam(value = "stateId") Integer stateId,
                                                             @RequestParam(value = "assessmentToolModeId") Integer assessmentToolModeId) {
        return checklistService.getAssessmentToolsForState(stateId).stream().filter(assessmentTool -> assessmentTool.getAssessmentToolMode().getId().equals(assessmentToolModeId)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/assessmentTool/search/findByAssessmentToolType", method = {RequestMethod.GET})
    public List<AssessmentTool> findByAssessmentToolType(@RequestParam(value = "assessmentToolType") String assessmentToolType) {
        return assessmentToolRepository.findByAssessmentToolType(AssessmentToolType.valueOf(assessmentToolType));
    }

    // Always provide default assessment tools for the convenience of client
    @RequestMapping(value = "/assessmentTool/search/lastModifiedByState", method = {RequestMethod.GET})
    public Page<AssessmentTool> findLastModifiedByState(@RequestParam("name") String name, @RequestParam("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        State state = stateRepository.findByName(name);
        return new PageImpl<>(assessmentToolRepository.findByStateOrStateIsNullOrderByAssessmentToolModeNameAscNameAsc(state));
    }

    @RequestMapping(value = "/assessmentTool/search/find", method = {RequestMethod.GET})
    public List<AssessmentTool> find(@RequestParam(value = "state") Integer stateId,
                                     @RequestParam(value = "assessment_tool_mode", required = false) Integer assessmentToolModeId) {
        return assessmentToolModeId == null ? findByState(stateId) : findByStateAndAssessmentTool(stateId, assessmentToolModeId);
    }

    @RequestMapping(value = "/assessmentTool/withFile", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentTool importFile(Principal principal,
                                               @RequestParam("uploadedFile") MultipartFile file,
                                               @RequestParam(value = "id", required = false) Integer id,
                                               @RequestParam("assessmentType") String assessmentTypeName,
                                               @RequestParam("assessmentTool") String assessmentToolName,
                                               @RequestParam("assessmentToolMode") String assessmentToolMode,
                                               @RequestParam(value = "state", required = false) String stateName
                                               ) throws Exception {
        return null;
    }
}
