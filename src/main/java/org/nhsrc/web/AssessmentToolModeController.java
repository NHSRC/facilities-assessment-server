package org.nhsrc.web;

import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.AssessmentToolModeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.AssessmentToolModeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class AssessmentToolModeController {
    private final AssessmentToolModeRepository assessmentToolModeRepository;
    private final UserService userService;

    @Autowired
    public AssessmentToolModeController(AssessmentToolModeRepository assessmentToolModeRepository, UserService userService) {
        this.assessmentToolModeRepository = assessmentToolModeRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/assessmentToolModes", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentToolMode save(@RequestBody AssessmentToolModeRequest request) {
        AssessmentToolMode assessmentToolMode = Repository.findByUuidOrCreate(request.getUuid(), assessmentToolModeRepository, new AssessmentToolMode());
        assessmentToolMode.setName(request.getName());
        assessmentToolMode.setInactive(request.getInactive());
        return assessmentToolModeRepository.save(assessmentToolMode);
    }

    @RequestMapping(value = "/assessmentToolModes/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Metadata_Write')")
    public AssessmentToolMode delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, assessmentToolModeRepository);
    }

    @RequestMapping(value = "/assessmentToolMode/search/find", method = {RequestMethod.GET})
    public List<AssessmentToolMode> find(Principal principal) {
        User user = userService.findUserByPrincipal(principal);
        if (user == null)
            return assessmentToolModeRepository.findAllByInactiveFalse();

        return user.getPrivilegedAssessmentToolModes();
    }
}
