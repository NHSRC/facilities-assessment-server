package org.nhsrc.web;

import org.nhsrc.domain.State;
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

@RestController
@RequestMapping("/api")
public class RegionDataController {
    @Autowired
    private StateRepository stateRepository;

    @RequestMapping(value = "/states", method = RequestMethod.GET)
    public ResponseEntity<Set<State>> getModifiedEntities(
            @RequestParam("lastSyncedDate")
            @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date lastSyncedDate) {
        PageRequest page = new PageRequest(0, 10);
        HashSet<State> states = new HashSet<>(
                stateRepository.findByLastModifiedDateGreaterThanOrderById(lastSyncedDate, page).getContent());
        return new ResponseEntity<Set<State>>(states, HttpStatus.OK);
    }
}
