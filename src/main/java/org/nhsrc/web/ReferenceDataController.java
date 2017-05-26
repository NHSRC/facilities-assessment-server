package org.nhsrc.web;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Department;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;
import org.nhsrc.repository.AssessmentToolRepository;
import org.nhsrc.repository.DepartmentRepository;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@RestController
@RequestMapping("/api/reference-data")
public class ReferenceDataController {

    private final StateRepository stateRepository;
    private final PageRequest page;
    private final FacilityTypeRepository facilityTypeRepository;
    private final AssessmentToolRepository assessmentToolRepository;
    private final DepartmentRepository departmentRepository;


    @Autowired
    public ReferenceDataController(StateRepository stateRepository,
                                   FacilityTypeRepository facilityTypeRepository,
                                   AssessmentToolRepository assessmentToolRepository,
                                   DepartmentRepository departmentRepository) {
        this.stateRepository = stateRepository;
        this.facilityTypeRepository = facilityTypeRepository;
        this.assessmentToolRepository = assessmentToolRepository;
        this.departmentRepository = departmentRepository;
        page = new PageRequest(0, Math.max(1, Math.toIntExact(this.stateRepository.count())));
    }

    @RequestMapping(value = "/states", method = RequestMethod.GET)
    public ResponseEntity<Set<State>> getModifiedStates(
            @RequestParam(value = "lastSyncedDate", required = false, defaultValue = "01-01-1000 00:00:00")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastSyncedDate) {
        HashSet<State> states = new HashSet<>(
                stateRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastSyncedDate, page).getContent());
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    @RequestMapping(value = "/facilityTypes", method = RequestMethod.GET)
    public ResponseEntity<Set<FacilityType>> getModifiedFacilityTypes(
            @RequestParam(value = "lastSyncedDate", required = false, defaultValue = "01-01-1000 00:00:00")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastSyncedDate) {
        HashSet<FacilityType> facilityTypes = new HashSet<>(
                facilityTypeRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastSyncedDate, page).getContent());
        return new ResponseEntity<>(facilityTypes, HttpStatus.OK);
    }

    @RequestMapping(value = "/assessmentTools", method = RequestMethod.GET)
    public ResponseEntity<Set<AssessmentTool>> getModifiedAssessmentTools(
            @RequestParam(value = "lastSyncedDate", required = false, defaultValue = "01-01-1000 00:00:00")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastSyncedDate) {
        HashSet<AssessmentTool> facilityTypes = new HashSet<>(
                assessmentToolRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastSyncedDate, page).getContent());
        return new ResponseEntity<>(facilityTypes, HttpStatus.OK);
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public ResponseEntity<Set<Department>> getModifiedDepartments(
            @RequestParam(value = "lastSyncedDate", required = false, defaultValue = "01-01-1000 00:00:00")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastSyncedDate) {
        HashSet<Department> facilityTypes = new HashSet<>(
                departmentRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastSyncedDate, page).getContent());
        return new ResponseEntity<>(facilityTypes, HttpStatus.OK);
    }
}
