package org.nhsrc.web;

import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.security.User;
import org.nhsrc.mapper.AssessmentToolComponentMapper;
import org.nhsrc.repository.AssessmentToolModeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.AssessmentToolModeRequest;
import org.nhsrc.web.contract.ext.AssessmentToolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Date;
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

    @RequestMapping(value = "/assessmentToolMode/assessmentPrivilege", method = {RequestMethod.GET})
    @PreAuthorize("hasRole('User')")
    public List<AssessmentToolMode> find(Principal principal) {
        User user = userService.findUserByPrincipal(principal);
        if (user == null)
            return assessmentToolModeRepository.findAllByInactiveFalse();

        return user.getPrivilegedAssessmentToolModes();
    }

    @RequestMapping(value = "/ext/program", method = {RequestMethod.GET})
    public Page<AssessmentToolResponse.ProgramResponse> getPrograms(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        Page<AssessmentToolMode> programs = assessmentToolModeRepository.findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(lastModifiedDateTime, pageable);
        return programs.map(AssessmentToolComponentMapper::mapProgram);
    }
}
