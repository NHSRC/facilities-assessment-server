package org.nhsrc.web;

import org.jetbrains.annotations.NotNull;
import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.AssessmentToolExcelFile;
import org.nhsrc.referenceDataImport.ExcelImportReport;
import org.nhsrc.repository.*;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.service.ExcelImportService;
import org.nhsrc.utils.HtmlOutputWriter;
import org.nhsrc.utils.StringUtil;
import org.nhsrc.visitor.HtmlVisitor;
import org.nhsrc.web.contract.AssessmentToolRequest;
import org.nhsrc.web.contract.ext.AssessmentToolResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
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
    private final HtmlOutputWriter htmlOutputWriter;
    private final IndicatorDefinitionRepository indicatorDefinitionRepository;
    private static final Logger logger = LoggerFactory.getLogger(AssessmentToolController.class);

    @Autowired
    public AssessmentToolController(AssessmentToolRepository assessmentToolRepository, AssessmentToolModeRepository assessmentToolModeRepository, ChecklistRepository checklistRepository, ExcludedAssessmentToolStateRepository excludedAssessmentToolStateRepository, StateRepository stateRepository, ChecklistService checklistService, ExcelImportService excelImportService, HtmlOutputWriter htmlOutputWriter, IndicatorDefinitionRepository indicatorDefinitionRepository) {
        this.assessmentToolRepository = assessmentToolRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
        this.checklistRepository = checklistRepository;
        this.excludedAssessmentToolStateRepository = excludedAssessmentToolStateRepository;
        this.stateRepository = stateRepository;
        this.checklistService = checklistService;
        this.excelImportService = excelImportService;
        this.htmlOutputWriter = htmlOutputWriter;
        this.indicatorDefinitionRepository = indicatorDefinitionRepository;
    }

    @RequestMapping(value = "/assessmentTools", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentTool save(@RequestBody AssessmentToolRequest request) {
        AssessmentTool assessmentTool = Repository.findByUuidOrCreate(request.getUuid(), assessmentToolRepository, new AssessmentTool());
        AssessmentToolMode assessmentToolMode = Repository.findById(request.getAssessmentToolModeId(), assessmentToolModeRepository);
        assessmentTool.setName(request.getName());
        assessmentTool.setAssessmentToolMode(assessmentToolMode);
        assessmentTool.setInactive(request.isInactive());
        assessmentTool.setSortOrder(request.getSortOrder());
        assessmentTool.setThemed(false);
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
        if (stateId == -1) {
            return assessmentToolModeId == null ? checklistService.findUniversalAssessmentTools() : checklistService.findUniversalAssessmentTools(assessmentToolModeId);
        } else {
            return assessmentToolModeId == null ? findByState(stateId) : findByStateAndAssessmentTool(stateId, assessmentToolModeId);
        }
    }

    @RequestMapping(value = "/assessmentTool/asFile", method = {RequestMethod.POST})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public ResponseEntity<Object> updateToolComponent(Principal principal,
                                                      @RequestParam("uploadedFile") MultipartFile file,
                                                      @RequestParam("id") int id) throws Exception {
        AssessmentTool assessmentTool = Repository.findById(id, assessmentToolRepository);
        if (assessmentTool == null)
            return new ResponseEntity<>(String.format("No assessment tool with id: %d", id), HttpStatus.BAD_REQUEST);

        processExcelFile(file, true, assessmentTool);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/assessmentTool/asFile", method = {RequestMethod.HEAD, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public ResponseEntity<Object> importAssessmentToolViaExcelFile(@RequestParam("uploadedFile") MultipartFile file,
                                                                   @RequestParam("assessmentTool") String assessmentToolName,
                                                                   @RequestParam("assessmentToolMode") String assessmentToolModeName,
                                                                   @RequestParam(value = "overrideAssessmentToolId", required = false) Integer overrideAssessmentToolId,
                                                                   @RequestParam(value = "state", required = false) String stateName,
                                                                   @RequestParam("sortOrder") int sortOrder,
                                                                   @RequestParam(value = "themed", required = false) boolean themed,
                                                                   HttpServletRequest httpServletRequest
    ) throws Exception {
        return processExcelFile(file, assessmentToolName, assessmentToolModeName, overrideAssessmentToolId, stateName, sortOrder, themed, httpServletRequest.getMethod().equals(RequestMethod.PUT.name()));
    }

    @NotNull
    private ResponseEntity<Object> processExcelFile(MultipartFile file, String assessmentToolName, String assessmentToolModeName, Integer overrideAssessmentToolId, String stateName, int sortOrder, boolean themed, boolean persistData) throws Exception {
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
        assessmentTool.setThemed(themed);
        assessmentTool.setState(state);
        assessmentTool.setSortOrder(sortOrder);
        assessmentTool.setInactive(false);
        if (overrideAssessmentToolId != null) {
            AssessmentTool toOverrideAssessmentTool = Repository.findById(overrideAssessmentToolId, assessmentToolRepository);
            if (toOverrideAssessmentTool == null)
                return new ResponseEntity<>(String.format("No assessment tool found with id: %d", overrideAssessmentToolId), HttpStatus.BAD_REQUEST);
            assessmentTool.addOverride(state, toOverrideAssessmentTool);
        }

        processExcelFile(file, persistData, assessmentTool);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean processExcelFile(MultipartFile file, boolean persistData, AssessmentTool assessmentTool) throws Exception {
        AssessmentToolExcelFile assessmentToolExcelFile = excelImportService.parseAssessmentTool(assessmentTool, file.getInputStream());
        assessmentToolExcelFile.validate();
        ExcelImportReport excelImportReport = assessmentToolExcelFile.getExcelImportReport();
        if (excelImportReport.hasErrors()) {
            String errorHtml = htmlOutputWriter.generateErrorHtml(excelImportReport.getContext());
            write(String.format("%s-error", assessmentTool.getName()), errorHtml);
            return false;
        }

        HtmlVisitor visitor = new HtmlVisitor();
        assessmentToolExcelFile.accept(visitor);
        String html = htmlOutputWriter.generateReportHtml(visitor.getContext());

        write(assessmentTool.getName(), html);

        List<Checklist> checklists = assessmentToolExcelFile.getChecklists();
        checklistService.associatedDepartments(checklists);
        assessmentToolRepository.save(assessmentTool);

        if (!persistData) {
            logger.info("Rolling back transaction to avoid persistence");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return true;
    }

    private void write(String fileName, String html) throws IOException {
        FileWriter fileWriter = new FileWriter(String.format("log/%s.html", fileName));
        fileWriter.write(html);
        fileWriter.close();
    }

    @RequestMapping(value = "/ext/assessmentTool", method = {RequestMethod.GET})
    public List<AssessmentToolResponse> getAssessmentTools(
            @RequestParam(value = "fromDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fromDate,
            @RequestParam(value = "stateName", required = false) String stateName) {
        boolean stateSpecified = stateName != null && !stateName.isEmpty();
        List<AssessmentTool> assessmentTools;
        if (stateSpecified) {
            State state = stateRepository.findByName(stateName);
            if (state == null)
                throw new GunakAPIException(GunakAPIException.INVALID_STATE, HttpStatus.BAD_REQUEST);
            assessmentTools = assessmentToolRepository.getStateTools(stateName, fromDate);
        } else {
            assessmentTools = assessmentToolRepository.getUniversalTools(fromDate);
        }
        return assessmentTools.stream().map(assessmentTool -> {
            AssessmentToolResponse atr = createAssessmentToolExternalResponse(assessmentTool);
            if (stateSpecified) atr.setState(stateName);
            atr.setUniversal(!stateSpecified);
            atr.setChecklists(null);
            atr.setLastModifiedDate(assessmentTool.getLastModifiedDate());
            return atr;
        }).collect(Collectors.toList());
    }

    @NotNull
    private AssessmentToolResponse createAssessmentToolExternalResponse(AssessmentTool assessmentTool) {
        AssessmentToolResponse atr = new AssessmentToolResponse();
        atr.setExternalId(assessmentTool.getUuidString());
        atr.setName(assessmentTool.getName());
        atr.setProgram(assessmentTool.getAssessmentToolMode().getName());
        atr.setAssessmentToolType(assessmentTool.getAssessmentToolType().name());
        atr.setInactive(assessmentTool.getInactive());
        return atr;
    }
}
