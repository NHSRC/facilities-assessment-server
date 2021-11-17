package org.nhsrc.web;

import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.AssessmentToolExcelFile;
import org.nhsrc.repository.*;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.service.ExcelImportService;
import org.nhsrc.utils.StringUtil;
import org.nhsrc.visitor.HtmlVisitor;
import org.nhsrc.web.contract.AssessmentToolRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class AssessmentToolController {
    private final ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository;
    private final StateRepository stateRepository;
    private final ChecklistService checklistService;
    private final AssessmentToolRepository assessmentToolRepository;
    private final AssessmentToolModeRepository assessmentToolModeRepository;
    private final ChecklistRepository checklistRepository;
    private final ExcelImportService excelImportService;

    @Autowired
    public AssessmentToolController(AssessmentToolRepository assessmentToolRepository, AssessmentToolModeRepository assessmentToolModeRepository, ChecklistRepository checklistRepository, ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository, StateRepository stateRepository, ChecklistService checklistService, ExcelImportService excelImportService) {
        this.assessmentToolRepository = assessmentToolRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
        this.checklistRepository = checklistRepository;
        this.excludedAssessmentToolStateRepository = excludedAssessmentToolStateRepository;
        this.stateRepository = stateRepository;
        this.checklistService = checklistService;
        this.excelImportService = excelImportService;
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

    @RequestMapping(value = "/assessmentTool/withFile", method = {RequestMethod.POST})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public ResponseEntity<Object> update(Principal principal,
                                         @RequestParam("uploadedFile") MultipartFile file,
                                         @RequestParam("id") int id) throws Exception {
        AssessmentTool assessmentTool = Repository.findById(id, assessmentToolRepository);
        if (assessmentTool == null)
            return new ResponseEntity<>(String.format("No assessment tool with id: %d", id), HttpStatus.BAD_REQUEST);

        AssessmentToolExcelFile assessmentToolExcelFile = excelImportService.parseAssessmentTool(assessmentTool, file.getInputStream());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/assessmentTool/asFile", method = {RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public ResponseEntity<Object> create(Principal principal,
                                         @RequestParam("uploadedFile") MultipartFile file,
                                         @RequestParam("assessmentTool") String assessmentToolName,
                                         @RequestParam("assessmentToolMode") String assessmentToolModeName,
                                         @RequestParam(value = "overrideAssessmentToolId", required = false) Integer overrideAssessmentToolId,
                                         @RequestParam(value = "state", required = false) String stateName,
                                         @RequestParam("sortOrder") int sortOrder
                                         ) throws Exception {
        AssessmentToolMode assessmentToolMode = assessmentToolModeRepository.findByName(assessmentToolModeName);
        if (assessmentToolMode == null)
            return new ResponseEntity<>(String.format("No program by name: %s", assessmentToolModeName), HttpStatus.BAD_REQUEST);

        State state = null;
        if (stateName != null && !stateName.isEmpty()) {
            state = stateRepository.findByName(stateName);
            if (state == null)
                return new ResponseEntity<>(String.format("No state by name: %s", stateName), HttpStatus.BAD_REQUEST);
        }

        AssessmentTool assessmentTool = new AssessmentTool();
        assessmentTool.setAssessmentToolMode(assessmentToolMode);
        assessmentTool.setAssessmentToolType(AssessmentToolType.COMPLIANCE);
        assessmentTool.setName(assessmentToolName);
        assessmentTool.setState(state);
        assessmentTool.setSortOrder(sortOrder);
        assessmentTool.setInactive(false);
        if (overrideAssessmentToolId != null) {
            AssessmentTool toOverrideAssessmentTool = Repository.findById(overrideAssessmentToolId, assessmentToolRepository);
            if (toOverrideAssessmentTool == null)
                return new ResponseEntity<>(String.format("No assessment tool found with id: %d", overrideAssessmentToolId), HttpStatus.BAD_REQUEST);
            assessmentTool.addOverride(state, toOverrideAssessmentTool);
        }
        assessmentToolRepository.save(assessmentTool);

        AssessmentToolExcelFile assessmentToolExcelFile = excelImportService.parseAssessmentTool(assessmentTool, file.getInputStream());
        HtmlVisitor visitor = new HtmlVisitor();
        assessmentToolExcelFile.accept(visitor);
        String html = visitor.generateHtml();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
