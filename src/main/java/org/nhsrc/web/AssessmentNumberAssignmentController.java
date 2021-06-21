package org.nhsrc.web;

import org.nhsrc.domain.assessment.AssessmentNumberAssignment;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.AssessmentTypeRepository;
import org.nhsrc.repository.FacilityRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.assessment.AssessmentNumberAssignmentRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.web.contract.assessment.AssessmentNumberAssignmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class AssessmentNumberAssignmentController {
    private final AssessmentNumberAssignmentRepository repository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final AssessmentTypeRepository assessmentTypeRepository;

    @Autowired
    public AssessmentNumberAssignmentController(AssessmentNumberAssignmentRepository repository, FacilityRepository facilityRepository, UserRepository userRepository, AssessmentTypeRepository assessmentTypeRepository) {
        this.repository = repository;
        this.facilityRepository = facilityRepository;
        this.userRepository = userRepository;
        this.assessmentTypeRepository = assessmentTypeRepository;
    }

    @RequestMapping(value = "/assessmentNumberAssignments", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Users_Write')")
    public ResponseEntity save(@RequestBody AssessmentNumberAssignmentRequest request) {
        AssessmentNumberAssignment assessmentNumberAssignment = Repository.findByIdOrCreate(request.getId(), repository, new AssessmentNumberAssignment());
        assessmentNumberAssignment.setInactive(request.getInactive());
        assessmentNumberAssignment.setAssessmentNumber(request.getAssessmentNumber());
        assessmentNumberAssignment.setFacility(Repository.findById(request.getFacilityId(), facilityRepository));
        assessmentNumberAssignment.setAssessmentType(Repository.findById(request.getAssessmentTypeId(), assessmentTypeRepository));
        Repository.mergeChildren(request.getUserIds(), request.getUserIds(), userRepository, user -> assessmentNumberAssignment.removeUser((User) user), user -> assessmentNumberAssignment.addUser((User) user));
        return new ResponseEntity<>(repository.save(assessmentNumberAssignment), HttpStatus.CREATED);
    }
}
