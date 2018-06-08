package org.nhsrc.web;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.State;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.CheckpointScoreRepository;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.ExcelImportService;
import org.nhsrc.service.FacilityAssessmentService;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.FacilityAssessmentImportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class FacilityAssessmentController {
    private final FacilityAssessmentService facilityAssessmentService;
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private ChecklistRepository checklistRepository;
    private CheckpointScoreRepository checkpointScoreRepository;
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(FacilityAssessmentController.class);
    private ExcelImportService excelImportService;

    @Autowired
    public FacilityAssessmentController(FacilityAssessmentService facilityAssessmentService, UserRepository userRepository, StateRepository stateRepository, FacilityAssessmentRepository facilityAssessmentRepository, ChecklistRepository checklistRepository, CheckpointScoreRepository checkpointScoreRepository, UserService userService, ExcelImportService excelImportService) {
        this.facilityAssessmentService = facilityAssessmentService;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.checklistRepository = checklistRepository;
        this.checkpointScoreRepository = checkpointScoreRepository;
        this.userService = userService;
        this.excelImportService = excelImportService;
    }

    @RequestMapping(value = "facility-assessment", method = RequestMethod.POST)
    public ResponseEntity<FacilityAssessment> syncFacilityAssessment(Principal principal, @RequestBody FacilityAssessmentDTO facilityAssessmentDTO) {
        logger.info(facilityAssessmentDTO.toString());
        User user = userService.findSubmissionUser(principal == null ? null : principal.getName());
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentDTO, user);
        return new ResponseEntity<>(facilityAssessment, HttpStatus.CREATED);
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

    @RequestMapping(value = "facilityAssessment", method = RequestMethod.GET)
    Page<FacilityAssessment> getAssessmentsForState(Principal principal, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDate, @RequestParam int size, @RequestParam int page) {
        User user = userRepository.findByEmail(principal.getName());
        State state = stateRepository.findOne(user.getUserTypeReferenceId());
        PageRequest pageable = new PageRequest(page, size);
        return facilityAssessmentRepository.findByFacilityDistrictStateAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(state, lastModifiedDate, pageable);
    }

    @RequestMapping(value = "facility-assessment/excel/new", method = RequestMethod.POST)
    @Transactional
    public FacilityAssessmentImportResponse submitAssessment(Principal principal, @RequestParam("assessmentFile") MultipartFile file, @RequestParam("facilityUuid") UUID facilityUuid, @RequestParam("nonExistentFacilityName") String nonExistentFacilityName, @RequestParam("assessmentTypeUuid") UUID assessmentTypeUuid, @RequestParam("assessmentToolUuid") UUID assessmentToolUuid, @RequestParam("checklistUuid") UUID checklistUuid) throws Exception {
        User user = userRepository.findByEmail(principal.getName());
        FacilityAssessmentDTO facilityAssessmentDTO = new FacilityAssessmentDTO();
        facilityAssessmentDTO.setAssessmentTypeUUID(assessmentTypeUuid);
        facilityAssessmentDTO.setAssessmentTool(assessmentToolUuid);
        facilityAssessmentDTO.setFacility(facilityUuid);
        facilityAssessmentDTO.setFacilityName(nonExistentFacilityName);
        facilityAssessmentDTO.setUuid(UUID.randomUUID());
        Date date = new Date();
        facilityAssessmentDTO.setStartDate(date);
        facilityAssessmentDTO.setEndDate(date);
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentDTO, user);

        FacilityAssessmentImportResponse facilityAssessmentImportResponse = new FacilityAssessmentImportResponse();
        facilityAssessmentImportResponse.setFacilityAssessment(facilityAssessment);
        Checklist checklist = checklistRepository.findByUuid(checklistUuid);
        excelImportService.saveAssessment(file.getInputStream(), facilityAssessment, checklist, facilityAssessmentImportResponse);
        return facilityAssessmentImportResponse;
    }

    @RequestMapping(value = "facility-assessment/excel/update", method = RequestMethod.POST)
    @Transactional
    public FacilityAssessmentImportResponse submitAssessment(Principal principal, @RequestParam("assessmentFile") MultipartFile file, @RequestParam("facilityAssessmentUuid") UUID facilityAssessmentUuid) throws Exception {
        User user = userRepository.findByEmail(principal.getName());
        FacilityAssessmentImportResponse response = new FacilityAssessmentImportResponse();
        FacilityAssessment facilityAssessment = facilityAssessmentRepository.findByUuid(facilityAssessmentUuid);
        if (facilityAssessment == null) return response;

        //Implemented only for laqshya right now, hence upload is one checklist at a time
        CheckpointScore checkpointScore = checkpointScoreRepository.findFirstByFacilityAssessmentId(facilityAssessment.getId());

        response.setFacilityAssessment(facilityAssessment);
        facilityAssessmentService.clearCheckpointScores(facilityAssessment);
        facilityAssessment.setUser(user);
        facilityAssessmentRepository.save(facilityAssessment);

        excelImportService.saveAssessment(file.getInputStream(), facilityAssessment, checkpointScore.getChecklist(), response);
        return response;
    }
}