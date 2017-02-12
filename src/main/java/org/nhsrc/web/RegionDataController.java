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

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@RestController
@RequestMapping("/api")
public class RegionDataController {

    private final StateRepository stateRepository;
    private final PageRequest page;


    @Autowired
    public RegionDataController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
        page = new PageRequest(0, Math.max(1, Math.toIntExact(this.stateRepository.count())));
    }

    @RequestMapping(value = "/regionData", method = RequestMethod.GET)
    public ResponseEntity<Set<State>> getModifiedEntities(
            @RequestParam(value = "lastSyncedDate", required = false, defaultValue = "01-01-1000 00:00:00")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastSyncedDate) {
        HashSet<State> states = new HashSet<>(
                stateRepository.findByLastModifiedDateGreaterThanOrderById(lastSyncedDate, page).getContent());
        return new ResponseEntity<Set<State>>(states, HttpStatus.OK);
    }
}
