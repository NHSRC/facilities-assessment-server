package org.nhsrc.web;

import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.dto.OldFacilityAssessmentDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.ExcelImportService;
import org.nhsrc.service.FacilityAssessmentService;
import org.nhsrc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(FacilityAssessmentController.class);
    private ExcelImportService excelImportService;

    @Autowired
    public FacilityAssessmentController(FacilityAssessmentService facilityAssessmentService, UserRepository userRepository, StateRepository stateRepository, FacilityAssessmentRepository facilityAssessmentRepository, UserService userService, ExcelImportService excelImportService) {
        this.facilityAssessmentService = facilityAssessmentService;
        this.userRepository = userRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
        this.userService = userService;
        this.excelImportService = excelImportService;
    }

    @RequestMapping(value = "facility-assessment", method = RequestMethod.POST)
    public ResponseEntity<FacilityAssessment> syncFacilityAssessment(Principal principal, @RequestBody OldFacilityAssessmentDTO oldFacilityAssessmentDTO) {
        logger.info(oldFacilityAssessmentDTO.toString());
        User user = userService.findSubmissionUser(principal);
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(oldFacilityAssessmentDTO, user);
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

    @RequestMapping(value = "facilityAssessments", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public FacilityAssessment submitAssessment(Principal principal,
                                               @RequestBody FacilityAssessmentDTO facilityAssessmentDTO) throws Exception {
        User user = userRepository.findByEmail(principal.getName());
        return facilityAssessmentService.save(facilityAssessmentDTO, user);
    }

    @RequestMapping(value = "facilityAssessments/withFile", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
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
}