package org.nhsrc.web;

import org.nhsrc.domain.State;
import org.nhsrc.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/state")
@RestController
public class StateController {
    private final StateRepository stateRepository;

    @Autowired
    public StateController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<State> create(@RequestBody State state) {
        return new ResponseEntity<>(stateRepository.save(state), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<State> get(@PathVariable String uuid) {
        State state = stateRepository.find(UUID.fromString(uuid));
        return new ResponseEntity<>(state, HttpStatus.OK);
    }
}
