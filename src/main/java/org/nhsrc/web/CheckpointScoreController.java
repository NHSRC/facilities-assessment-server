package org.nhsrc.web;

import org.nhsrc.domain.CheckpointScore;
import org.nhsrc.domain.State;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.CheckpointRepository;
import org.nhsrc.repository.CheckpointScoreRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/")
public class CheckpointScoreController {
    private CheckpointScoreRepository checkpointScoreRepository;
    private UserRepository userRepository;
    private StateRepository stateRepository;

    @Autowired
    public CheckpointScoreController(CheckpointScoreRepository checkpointScoreRepository, UserRepository userRepository, StateRepository stateRepository) {
        this.checkpointScoreRepository = checkpointScoreRepository;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
    }

//    @RequestMapping(value = "checkpointScore", method = RequestMethod.GET)
//    Page<CheckpointScore> getScoresForState(Principal principal, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDate, @RequestParam int size, @RequestParam int page) {
//        String name = principal.getName();
//        User user = userRepository.findByEmail(name);
//        State state = stateRepository.findOne(user.getUserTypeReferenceId());
//        PageRequest pageable = new PageRequest(page, size);
//        return checkpointScoreRepository.findByFacilityAssessmentFacilityDistrictStateAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(state, lastModifiedDate, pageable);
//    }
}