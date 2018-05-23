package org.nhsrc.web;

import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.State;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.dto.IndicatorDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.referenceDataImport.AssessmentChecklistData;
import org.nhsrc.referenceDataImport.ExcelImporter;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.ExcelImportService;
import org.nhsrc.service.FacilityAssessmentService;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.FacilityAssessmentExcelRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(FacilityAssessmentController.class);
    private ExcelImportService excelImportService;

    @Autowired
    public FacilityAssessmentController(FacilityAssessmentService facilityAssessmentService, UserRepository userRepository, StateRepository stateRepository, FacilityAssessmentRepository facilityAssessmentRepository, UserService userService, ExcelImportService excelImportService) {
        this.facilityAssessmentService = facilityAssessmentService;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
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

    @RequestMapping(value = "facility-assessment/excel", method = RequestMethod.POST)
    public ResponseEntity<?> submitAssessment(Principal principal, @RequestParam("assessmentFile") MultipartFile file, @RequestParam("facilityUuid") UUID facilityUuid, @RequestParam("assessmentTypeUuid") UUID assessmentTypeUuid, @RequestParam("assessmentToolUuid") UUID assessmentToolUuid) throws Exception {
        User user = userRepository.findByEmail(principal.getName());
        FacilityAssessmentDTO facilityAssessmentDTO = new FacilityAssessmentDTO();
        facilityAssessmentDTO.setAssessmentTypeUUID(assessmentTypeUuid);
        facilityAssessmentDTO.setAssessmentTool(assessmentToolUuid);
        facilityAssessmentDTO.setFacility(facilityUuid);
        facilityAssessmentDTO.setUuid(UUID.randomUUID());
        Date date = new Date();
        facilityAssessmentDTO.setStartDate(date);
        facilityAssessmentDTO.setEndDate(date);
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentDTO, user);

        AssessmentChecklistData assessmentChecklistData = excelImportService.parseAssessment(file.getInputStream(), facilityAssessmentDTO, facilityAssessment);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
}