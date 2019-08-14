package org.nhsrc.web;

import org.nhsrc.domain.State;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.web.contract.StateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class StateController {
    private StateRepository stateRepository;

    @Autowired
    public StateController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
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
}