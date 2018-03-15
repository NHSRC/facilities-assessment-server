package org.nhsrc.web;

import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.State;
import org.nhsrc.domain.security.User;
import org.nhsrc.dto.ChecklistDTO;
import org.nhsrc.dto.FacilityAssessmentDTO;
import org.nhsrc.dto.IndicatorDTO;
import org.nhsrc.dto.IndicatorListDTO;
import org.nhsrc.repository.FacilityAssessmentRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.FacilityAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class FacilityAssessmentController {
    private final FacilityAssessmentService facilityAssessmentService;
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private FacilityAssessmentRepository facilityAssessmentRepository;
    private static Logger logger = LoggerFactory.getLogger(FacilityAssessmentController.class);

    @Autowired
    public FacilityAssessmentController(FacilityAssessmentService facilityAssessmentService, UserRepository userRepository, StateRepository stateRepository, FacilityAssessmentRepository facilityAssessmentRepository) {
        this.facilityAssessmentService = facilityAssessmentService;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
    }

    @RequestMapping(value = "facility-assessment", method = RequestMethod.POST)
    public ResponseEntity<FacilityAssessment> syncFacilityAssessment(@RequestBody FacilityAssessmentDTO facilityAssessmentDTO) {
        logger.info(facilityAssessmentDTO.toString());
        FacilityAssessment facilityAssessment = facilityAssessmentService.save(facilityAssessmentDTO);
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
}