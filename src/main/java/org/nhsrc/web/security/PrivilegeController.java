package org.nhsrc.web.security;

import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.AssessmentToolModeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.security.PrivilegeRepository;
import org.nhsrc.web.contract.PrivilegeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class PrivilegeController {
    private PrivilegeRepository privilegeRepository;
    private AssessmentToolModeRepository assessmentToolModeRepository;
    private StateRepository stateRepository;

    @Autowired
    public PrivilegeController(PrivilegeRepository privilegeRepository, AssessmentToolModeRepository assessmentToolModeRepository, StateRepository stateRepository) {
        this.privilegeRepository = privilegeRepository;
        this.assessmentToolModeRepository = assessmentToolModeRepository;
        this.stateRepository = stateRepository;
    }

    @RequestMapping(value = "privileges", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Users_Write')")
    public Privilege save(@RequestBody PrivilegeRequest privilegeRequest) {
        Privilege privilege = Repository.findByIdOrCreate(privilegeRequest.getId(), privilegeRepository, new Privilege());
        privilege.setName(privilegeRequest.getName());
        privilege.setAssessmentToolMode(Repository.findById(privilegeRequest.getAssessmentToolModeId(), assessmentToolModeRepository));
        privilege.setState(Repository.findById(privilegeRequest.getStateId(), stateRepository));
        return privilegeRepository.save(privilege);
    }
}