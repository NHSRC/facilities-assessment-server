package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.domain.scores.ScoringProcessDetail;
import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.dto.assessment.FacilityAssessmentAppDTO;
import org.nhsrc.dto.assessment.FacilityAssessmentDTO;
import org.nhsrc.repository.*;
import org.nhsrc.repository.scores.ScoringProcessDetailRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.ExcelImportService;
import org.nhsrc.service.FacilityAssessmentService;
import org.nhsrc.service.UserService;
import org.nhsrc.utils.JsonUtil;
import org.nhsrc.web.contract.assessment.FacilityAssessmentResponse;
import org.nhsrc.web.contract.ext.AssessmentResponse;
import org.nhsrc.web.contract.ext.AssessmentSummaryResponse;
import org.nhsrc.web.mapper.AssessmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final FacilityAssessmentRepository facilityAssessmentRepository;
    private final AssessmentToolRepository assessmentToolRepository;
    private final UserService userService;
    private final AssessmentToolModeRepository assessmentToolModeRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacilityAssessmentController.class);
    private final ExcelImportService excelImportService;
    private final AssessmentTypeRepository assessmentTypeRepository;
    private final ScoringProcessDetailRepository scoringProcessDetailRepository;
    private final AssessmentMapper assessmentMapper;
    private final CheckpointScoreRepository checkpointScoreRepository;

    @Autowired
    public FacilityAssessmentController(FacilityAssessmentService facilityAssessmentService, UserRepository userRepository, FacilityAssessmentRepository facilityAssessmentRepository, UserService userService, ExcelImportService excelImportService, AssessmentToolRepository assessmentToolRepository, AssessmentToolModeRepository assessmentToolModeRepository, AssessmentTypeRepository assessmentTypeRepository, ScoringProcessDetailRepository scoringProcessDetailRepository, AssessmentMapper assessmentMapper, CheckpointScoreRepository checkpointScoreRepository) {
        this.facilityAssessmentService = facilityAssessmentService;
        this.userRepository = userRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.userService = userService;
        this.excelImportService = excelImportService;
        this.assessmentToolRepository = assessmentToolRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
        this.assessmentTypeRepository = assessmentTypeRepository;
        this.scoringProcessDetailRepository = scoringProcessDetailRepository;
        this.assessmentMapper = assessmentMapper;
        this.checkpointScoreRepository = checkpointScoreRepository;
    }

    @RequestMapping(value = "facility-assessment", method = RequestMethod.POST)
    public ResponseEntity<FacilityAssessmentResponse> syncFacilityAssessment(Principal principal, @RequestBody FacilityAssessmentAppDTO facilityAssessmentAppDTO) {
        logger.info(facilityAssessmentAppDTO.toString());
        User user = userService.findSubmissionUser(principal);
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(facilityAssessmentAppDTO.getAssessmentTool());
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentAppDTO, assessmentTool, user);
        List<String> filledChecklists = checkpointScoreRepository.getFilledChecklists(facilityAssessment.getId());
        return new ResponseEntity<>(AssessmentMapper.map(facilityAssessment, filledChecklists), HttpStatus.CREATED);
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

    @RequestMapping(value = "facilityAssessment/{facilityAssessmentUuid}/summary", method = RequestMethod.GET)
    public ResponseEntity<FacilityAssessmentResponse> getSummary(@PathVariable("facilityAssessmentUuid") String uuid) {
        FacilityAssessment assessment = facilityAssessmentRepository.findByUuid(UUID.fromString(uuid));
        List<String> filledChecklists = checkpointScoreRepository.getFilledChecklists(assessment.getId());
        ;
        return new ResponseEntity<>(AssessmentMapper.map(assessment, filledChecklists), HttpStatus.OK);
    }

    @RequestMapping(value = "facility-assessment/checklist", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<List<CheckpointScore>> syncFacilityAssessment(@RequestBody ChecklistDTO checklist) throws JsonProcessingException {
        try {
            List<CheckpointScore> checkpointScores = this.facilityAssessmentService.saveChecklist(checklist);
            return new ResponseEntity<>(checkpointScores, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.info(JsonUtil.OBJECT_MAPPER.writeValueAsString(checklist));
            throw e;
        }
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
    @PreAuthorize("hasRole('User')")
    // TODO: Find appropriate status code for privilege denied; Test with null values. Fix district/state in the db or put a null check.
    public Page<AssessmentSummaryResponse> listAssessments(Principal principal,
                                                           @RequestParam("assessmentEndDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date assessmentEndDateTime,
                                                           @RequestParam(value = "assessmentTool") @NotNull String assessmentToolUuid,
                                                           @RequestParam(value = "assessmentType", required = false) String assessmentTypeUuid,
                                                           Pageable pageable) {
        AssessmentTool assessmentTool = assessmentToolRepository.findByUuid(UUID.fromString(assessmentToolUuid));
        if (assessmentTool == null) {
            throw new GunakAPIException(GunakAPIException.INVALID_ASSESSMENT_TOOL_UUID, HttpStatus.BAD_REQUEST);
        }

        AssessmentType assessmentType = null;
        if (assessmentTypeUuid != null && !assessmentTypeUuid.isEmpty()) {
            assessmentType = assessmentTypeRepository.findByUuid(UUID.fromString(assessmentTypeUuid));
            if (assessmentType == null) {
                throw new GunakAPIException(GunakAPIException.INVALID_ASSESSMENT_TYPE, HttpStatus.BAD_REQUEST);
            }
        }

        ScoringProcessDetail scoringProcessDetail = scoringProcessDetailRepository.get();
        Date safeLastScoredUntilTime = scoringProcessDetail.getSafeLastScoredUntilTime();

        Page<FacilityAssessment> assessments;
        if (assessmentType == null) {
            assessments = facilityAssessmentRepository.findByAssessmentToolIdAndEndDateIsGreaterThanAndEndDateIsLessThanOrderByEndDateAscIdAsc(assessmentTool.getId(), assessmentEndDateTime, safeLastScoredUntilTime, pageable);
        } else
            assessments = facilityAssessmentRepository.findByAssessmentToolIdAndAssessmentTypeAndEndDateIsGreaterThanAndEndDateIsLessThanOrderByEndDateAscIdAsc(assessmentTool.getId(), assessmentType, assessmentEndDateTime, safeLastScoredUntilTime, pageable);

        return assessments.map(source -> assessmentMapper.map(new AssessmentSummaryResponse(), source, assessmentTool));
    }

    private void checkAccess(Principal principal, String programName) {
        User user = userService.findSubmissionUser(principal);
        if (!user.hasProgramPrivilege(Privilege.ASSESSMENT_READ, programName)) {
            throw new GunakAPIException("Either you have not logged in or you do not have the right permission. If you have logged in please contact support.", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "ext/assessment/{systemId}", method = {RequestMethod.GET})
    @PreAuthorize("hasRole('User')")
    public AssessmentResponse getAssessmentResponse(Principal principal, @PathVariable("systemId") @NotNull String systemId) {
        UUID uuid = null;
        try {
            uuid = UUID.fromString(systemId);
        } catch (IllegalArgumentException e) {
            throw new GunakAPIException(GunakAPIException.INVALID_ASSESSMENT_SYSTEM_ID, HttpStatus.BAD_REQUEST);
        }

        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findByUuid(uuid);
        if (facilityAssessment == null) throw new GunakAPIException(GunakAPIException.INVALID_ASSESSMENT_SYSTEM_ID, HttpStatus.BAD_REQUEST);
        checkAccess(principal, facilityAssessment.getAssessmentTool().getAssessmentToolMode().getName());

        AssessmentResponse assessmentResponse = AssessmentResponse.createNew(facilityAssessment.getAssessmentTool());
        assessmentMapper.map(assessmentResponse, facilityAssessment, facilityAssessment.getAssessmentTool());
        if (facilityAssessment.getAssessmentTool().getAssessmentToolType().equals(AssessmentToolType.COMPLIANCE)) {
            return assessmentMapper.mapAssessmentScores(facilityAssessment, assessmentResponse);
        } else {
            return assessmentMapper.mapIndicators(facilityAssessment, assessmentResponse);
        }
    }
}
