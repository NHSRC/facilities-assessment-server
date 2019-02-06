package org.nhsrc.web;

import org.nhsrc.domain.*;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.repository.*;
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
import org.springframework.data.domain.Pageable;
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
        User user = userService.findSubmissionUser(principal);
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

//    @RequestMapping(value = "facilityAssessment/byUser", method = RequestMethod.GET)
//    Page<FacilityAssessment> getAssessmentsForState(Principal principal, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDate, @RequestParam int size, @RequestParam int page) {
//        User user = userRepository.findByEmail(principal.getName());
//        State state = null;
//        PageRequest pageable = new PageRequest(page, size);
//        return facilityAssessmentRepository.findByFacilityDistrictStateAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(state, lastModifiedDate, pageable);
//    }

    @RequestMapping(value = "facilityAssessments", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public FacilityAssessmentImportResponse submitAssessment(Principal principal,
                                                             @RequestParam("uploadedFile") MultipartFile file,
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
        User user = userRepository.findByEmail(principal.getName());
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

        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentDTO, user);

        FacilityAssessmentImportResponse facilityAssessmentImportResponse = new FacilityAssessmentImportResponse();
        facilityAssessmentImportResponse.setFacilityAssessment(facilityAssessment);
        excelImportService.saveAssessment(file.getInputStream(), facilityAssessment, facilityAssessmentImportResponse);
        return facilityAssessmentImportResponse;
    }
}