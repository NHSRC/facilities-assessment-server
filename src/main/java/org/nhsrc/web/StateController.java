package org.nhsrc.web;

import org.nhsrc.domain.State;
import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.StateResponse;
import org.nhsrc.web.contract.StateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class StateController {
    private final StateRepository stateRepository;
    private final UserService userService;

    @Autowired
    public StateController(StateRepository stateRepository, UserService userService) {
        this.stateRepository = stateRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/states", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Facility_Metadata_Write')")
    public State save(@RequestBody StateRequest request) {
        State state = Repository.findByUuidOrCreate(request.getUuid(), stateRepository, new State());
        state.setName(request.getName());
        state.setShortName(request.getShortName());
        state.setInactive(request.getInactive());
        return stateRepository.save(state);
    }

    @RequestMapping(value = "/states/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Facility_Metadata_Write')")
    public State delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, stateRepository);
    }

    @RequestMapping(value = "/state/assessmentPrivilege", method = {RequestMethod.GET})
    @PreAuthorize("hasRole('User')")
    public List<StateResponse> find(Principal principal) {
        User user = userService.findUserByPrincipal(principal);
        List<StateResponse> states = user.getPrivilegedStates().stream().map(state -> new StateResponse(state.getId(), state.getName())).collect(Collectors.toList());
        if (userService.hasAllStatesDashboardPrivilege()) states.add(new StateResponse(-1, Privilege.ALL_STATES_DASHBOARD.getName()));
        return states;
    }
}
