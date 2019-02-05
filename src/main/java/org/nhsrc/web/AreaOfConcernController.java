package org.nhsrc.web;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.ReferencableEntity;
import org.nhsrc.repository.AreaOfConcernRepository;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AreaOfConcernRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/")
public class AreaOfConcernController {
    private final AreaOfConcernRepository areaOfConcernRepository;

    @Autowired
    public AreaOfConcernController(AreaOfConcernRepository areaOfConcernRepository) {
        this.areaOfConcernRepository = areaOfConcernRepository;
    }

    @RequestMapping(value = "/areaOfConcerns", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public ResponseEntity save(@RequestBody AreaOfConcernRequest request) {
        AreaOfConcern areaOfConcern = Repository.findByUuidOrCreate(request.getUuid(), areaOfConcernRepository, new AreaOfConcern());
        areaOfConcern.setName(request.getName());
        areaOfConcern.setReference(request.getReference().trim());
        areaOfConcern = areaOfConcernRepository.save(areaOfConcern);
        return new ResponseEntity<>(areaOfConcern, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/areaOfConcern/search/find", method = {RequestMethod.GET})
    public Page<AreaOfConcern> find(@RequestParam(value = "stateId", required = false) Integer stateId,
                                 @RequestParam(value = "checklistId", required = false) Integer checklistId,
                                 @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                                 Pageable pageable) {
        if (checklistId != null)
            return areaOfConcernRepository.findDistinctByChecklistsId(checklistId, pageable);
        if (assessmentToolId != null && stateId == null)
            return areaOfConcernRepository.findDistinctByChecklistsAssessmentToolId(assessmentToolId, pageable);
        if (assessmentToolId == null && stateId != null)
            return areaOfConcernRepository.findAllDistinctByChecklistsStateIdOrChecklistsStateIdIsNull(stateId, pageable);
        return areaOfConcernRepository.findAllByStateAndAssessmentTool(assessmentToolId, stateId, pageable);
    }
}