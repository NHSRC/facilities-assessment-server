package org.nhsrc.web;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.FacilityAssessmentAppDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.repository.*;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.ExcelImportService;
import org.nhsrc.service.FacilityAssessmentService;
import org.nhsrc.service.UserService;
import org.nhsrc.utils.CollectionUtil;
import org.nhsrc.web.contract.ext.AssessmentResponse;
import org.nhsrc.web.contract.ext.AssessmentSummaryResponse;
import org.nhsrc.web.mapper.AssessmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class FacilityAssessmentController {
    private final FacilityAssessmentService facilityAssessmentService;
    private final UserRepository userRepository;
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private AssessmentToolRepository assessmentToolRepository;
    private UserService userService;
    private AssessmentToolModeRepository assessmentToolModeRepository;
    private static Logger logger = LoggerFactory.getLogger(FacilityAssessmentController.class);
    private ExcelImportService excelImportService;
    private CheckpointScoreRepository checkpointScoreRepository;

    @Autowired
    public FacilityAssessmentController(FacilityAssessmentService facilityAssessmentService, UserRepository userRepository, StateRepository stateRepository, FacilityAssessmentRepository facilityAssessmentRepository, UserService userService, ExcelImportService excelImportService, FacilityRepository facilityRepository, AssessmentToolRepository assessmentToolRepository, AssessmentToolModeRepository assessmentToolModeRepository, CheckpointScoreRepository checkpointScoreRepository) {
        this.facilityAssessmentService = facilityAssessmentService;
        this.userRepository = userRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.userService = userService;
        this.excelImportService = excelImportService;
        this.assessmentToolRepository = assessmentToolRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
        this.checkpointScoreRepository = checkpointScoreRepository;
    }

    @RequestMapping(value = "facility-assessment", method = RequestMethod.POST)
    public ResponseEntity<FacilityAssessment> syncFacilityAssessment(Principal principal, @RequestBody FacilityAssessmentAppDTO facilityAssessmentAppDTO) {
        logger.info(facilityAssessmentAppDTO.toString());
        User user = userService.findSubmissionUser(principal);
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessmentAppDTO.getAssessmentTool());
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentAppDTO, assessmentTool, user);
        return new ResponseEntity<>(facilityAssessment, HttpStatus.CREATED);
    }

    @RequestMapping(value = "facilityAssessment/{facilityAssessmentId}", method = RequestMethod.DELETE)
    @Transactional
    @PreAuthorize("hasRole('Assessment_Write')")
    public ResponseEntity<Object> delete(@PathVariable("facilityAssessmentId") Integer id) {
        facilityAssessmentService.deleteAssessment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Assessment_Read')")
    @RequestMapping(value = "facilityAssessment/{facilityAssessmentId}", method = RequestMethod.GET)
    public ResponseEntity<FacilityAssessment> get(@PathVariable("facilityAssessmentId") Integer id) {
        return new ResponseEntity<>(facilityAssessmentRepository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(value = "facility-assessment/checklist", method = RequestMethod.POST)
    public ResponseEntity<List<CheckpointScore>> syncFacilityAssessment(@RequestBody ChecklistDTO checklist) {
        List<CheckpointScore> checkpointScores = this.facilityAssessmentService.saveChecklist(checklist);
        return new ResponseEntity<>(checkpointScores, HttpStatus.CREATED);
    }

    @RequestMapping(value = "facility-assessment/indicator", method = RequestMethod.POST)
    public ResponseEntity<Object> syncFacilityAssessment(@RequestBody IndicatorListDTO indicatorListDTO) {
        this.facilityAssessmentService.saveIndicatorList(indicatorListDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "facilityAssessments", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    @PreAuthorize("hasRole('Assessment_Write')")
    public FacilityAssessment submitAssessment(Principal principal,
                                               @RequestBody FacilityAssessmentDTO facilityAssessmentDTO) throws Exception {
        User user = userRepository.findByEmail(principal.getName());
        AssessmentTool assessmentTool = assessmentToolRepository.findOne(facilityAssessmentDTO.getAssessmentToolId());
        return facilityAssessmentService.save(facilityAssessmentDTO, assessmentTool, user);
    }

    @RequestMapping(value = "facilityAssessments/withFile", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    @PreAuthorize("hasRole('Assessment_Write')")
    public FacilityAssessment submitAssessment(Principal principal,
                                               @RequestParam("uploadedFile") MultipartFile file,
                                               @RequestParam(value = "id", required = false) Integer id,
                                               @RequestParam(value = "uuid", required = false) UUID uuid,
                                               @RequestParam(value = "facilityId", required = false) int facilityId,
                                               @RequestParam(value = "facilityName", required = false) String nonExistentFacilityName,
                                               @RequestParam("assessmentTypeId") int assessmentTypeId,
                                               @RequestParam("assessmentToolId") int assessmentToolId,
                                               @RequestParam("stateId") int stateId,
                                               @RequestParam("districtId") int districtId,
                                               @RequestParam("facilityTypeId") int facilityTypeId,
                                               @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                               @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws Exception {

        FacilityAssessmentDTO facilityAssessmentDTO = new FacilityAssessmentDTO();
        facilityAssessmentDTO.setAssessmentTypeId(assessmentTypeId);
        facilityAssessmentDTO.setAssessmentToolId(assessmentToolId);
        facilityAssessmentDTO.setStateId(stateId);
        facilityAssessmentDTO.setDistrictId(districtId);
        facilityAssessmentDTO.setFacilityTypeId(facilityTypeId);
        facilityAssessmentDTO.setFacilityId(facilityId);
        facilityAssessmentDTO.setFacilityName(nonExistentFacilityName);
        facilityAssessmentDTO.setUuid(uuid == null ? UUID.randomUUID() : uuid);
        facilityAssessmentDTO.setStartDate(startDate);
        facilityAssessmentDTO.setEndDate(endDate);
        FacilityAssessment facilityAssessment = this.submitAssessment(principal, facilityAssessmentDTO);

        if (Repository.findByUuidOrId(uuid, id, facilityAssessmentRepository) != null && file == null) return facilityAssessment;

        excelImportService.saveAssessment(file.getInputStream(), facilityAssessment);
        return facilityAssessment;
    }

    @RequestMapping(value = "ext/assessmentSummary", method = {RequestMethod.GET})
    // TODO: Find appropriate status code for privilege denied; Test with null values. Fix district/state in the db or put a null check.
    public Page<AssessmentSummaryResponse> listAssessments(Principal principal, @Param("assessmentEndDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date assessmentEndDateTime, @Param("assessmentToolName") @NotNull String assessmentToolName, @Param("programName") @NotNull String programName, Pageable pageable) {
        AssessmentTool assessmentTool = assessmentToolRepository.findByNameAndAssessmentToolModeName(assessmentToolName, programName);
        if (assessmentTool == null) {
            AssessmentToolMode program = assessmentToolModeRepository.findByName(programName);
            throw new GunakAPIException(program == null ? GunakAPIException.INVALID_PROGRAM_NAME : GunakAPIException.INVALID_ASSESSMENT_TOOL_NAME);
        }
        checkAccess(principal, programName);
        return facilityAssessmentRepository.findByAssessmentToolIdAndEndDateGreaterThanEqualOrderByEndDateAscIdAsc(assessmentTool.getId(), assessmentEndDateTime, pageable).map(source -> AssessmentMapper.map(new AssessmentSummaryResponse(), source, assessmentTool));
    }

    private void checkAccess(Principal principal, @NotNull @Param("programName") String programName) {
        User user = userService.findSubmissionUser(principal);
        if (user.hasPrivilege(Privilege.ASSESSMENT_READ, programName)) {
            throw new GunakAPIException("");
        }
    }

    @RequestMapping(value = "ext/assessment/{systemId}", method = {RequestMethod.GET})
    public AssessmentResponse getAssessmentResponse(Principal principal, @PathVariable("systemId") String systemId) {
        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findByUuid(UUID.fromString(systemId));
        if (facilityAssessment == null) throw new GunakAPIException(GunakAPIException.INVALID_ASSESSMENT_SYSTEM_ID);
        checkAccess(principal, facilityAssessment.getAssessmentTool().getAssessmentToolMode().getName());

        AssessmentResponse assessmentResponse = (AssessmentResponse) AssessmentMapper.map(new AssessmentResponse(), facilityAssessment, facilityAssessment.getAssessmentTool());
        List<CheckpointScore> scores = checkpointScoreRepository.findByFacilityAssessmentId(facilityAssessment.getId());
        scores.forEach(checkpointScore -> {
            AssessmentResponse.ChecklistAssessment checklistAssessment = CollectionUtil.addIfNotExists(assessmentResponse.getChecklists(), x -> x.getName().equals(checkpointScore.getCheckpoint().getChecklist().getName()), new AssessmentResponse.ChecklistAssessment());
            AssessmentResponse.AreaOfConcernAssessment areaOfConcernAssessment = CollectionUtil.addIfNotExists(checklistAssessment.getAreaOfConcerns(), x -> x.getReference().equals(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern().getReference()), new AssessmentResponse.AreaOfConcernAssessment());
            AssessmentResponse.StandardAssessment standardAssessment = CollectionUtil.addIfNotExists(areaOfConcernAssessment.getStandards(), x -> x.getReference().equals(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getReference()), new AssessmentResponse.StandardAssessment());
            AssessmentResponse.MeasurableElementAssessment measurableElementAssessment = CollectionUtil.addIfNotExists(standardAssessment.getMeasurableElements(), x -> x.getReference().equals(checkpointScore.getCheckpoint().getMeasurableElement().getReference()), new AssessmentResponse.MeasurableElementAssessment());

            AssessmentResponse.CheckpointAssessment checkpointAssessment = new AssessmentResponse.CheckpointAssessment();
            checkpointAssessment.setCheckpoint(checkpointScore.getCheckpoint().getName());
            checkpointAssessment.setMarkedNotApplicable(checkpointScore.getNa());
            checkpointAssessment.setRemarks(checkpointScore.getRemarks());
            checkpointAssessment.setScore(checkpointScore.getScore());
            measurableElementAssessment.addCheckpointAssessment(checkpointAssessment);
        });
        return assessmentResponse;
    }
}