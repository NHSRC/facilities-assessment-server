package org.nhsrc.web;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.Checklist;
import org.nhsrc.repository.AreaOfConcernRepository;
import org.nhsrc.repository.ChecklistRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.service.ChecklistService;
import org.nhsrc.web.contract.AreaOfConcernRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class AreaOfConcernController {
    private final AreaOfConcernRepository areaOfConcernRepository;
    private ChecklistRepository checklistRepository;
    private ChecklistService checklistService;

    @Autowired
    public AreaOfConcernController(AreaOfConcernRepository areaOfConcernRepository, ChecklistRepository checklistRepository, ChecklistService checklistService) {
        this.areaOfConcernRepository = areaOfConcernRepository;
        this.checklistRepository = checklistRepository;
        this.checklistService = checklistService;
    }

    @RequestMapping(value = "/areaOfConcerns", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public ResponseEntity save(@RequestBody AreaOfConcernRequest request) {
        AreaOfConcern areaOfConcern = Repository.findByUuidOrCreate(request.getUuid(), areaOfConcernRepository, new AreaOfConcern());
        areaOfConcern.setName(request.getName());
        areaOfConcern.setReference(request.getReference().trim());
        areaOfConcern.setInactive(request.getInactive());
        areaOfConcern = areaOfConcernRepository.save(areaOfConcern);

        Set<Checklist> checklists = areaOfConcern.getChecklists();

        addAreaOfConcernToChosenChecklists(request, areaOfConcern);
        removeAreaOfConcernFromRemovedChecklists(checklists, request, areaOfConcern);
        return new ResponseEntity<>(areaOfConcern, HttpStatus.CREATED);
    }

    private void removeAreaOfConcernFromRemovedChecklists(Set<Checklist> existingAssociatedChecklists, AreaOfConcernRequest request, AreaOfConcern areaOfConcern) {
        List<Integer> checklistIdsNotAssociated = existingAssociatedChecklists.stream().map(BaseEntity::getId).filter(existingAssociatedChecklistId -> request.getChecklistIds().contains(existingAssociatedChecklistId)).collect(Collectors.toList());
        checklistIdsNotAssociated.forEach(checklistIdNotAssociated -> {
            Checklist checklistNotAssociated = checklistRepository.findOne(checklistIdNotAssociated);
            checklistNotAssociated.removeAreaOfConcern(areaOfConcern);
            checklistRepository.save(checklistNotAssociated);
        });
    }

    private void addAreaOfConcernToChosenChecklists(@RequestBody AreaOfConcernRequest request, AreaOfConcern areaOfConcern) {
        List<Integer> checklistIds = request.getChecklistIds();
        checklistIds.forEach(checklistId -> {
            Checklist checklist = Repository.findById(checklistId, checklistRepository);
            checklist.addAreaOfConcern(areaOfConcern);
            checklistRepository.save(checklist);
        });
    }

    @RequestMapping(value = {"/areaOfConcern/search/findByAssessmentTool", "/areaOfConcern/search/find"}, method = {RequestMethod.GET})
    public Page<AreaOfConcern> find(@RequestParam(value = "stateId", required = false) Integer stateId,
                                 @RequestParam(value = "checklistId", required = false) Integer checklistId,
                                 @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                                 @RequestParam(value = "q", required = false) String searchParam,
                                 Pageable pageable) {
        if (searchParam != null) {
            Set<AreaOfConcern> areaOfConcerns = areaOfConcernRepository.findDistinctByChecklistsAssessmentToolsNameContainingIgnoreCase(searchParam);
            areaOfConcerns.addAll(areaOfConcernRepository.findDistinctByNameContainingIgnoreCase(searchParam));
            return new PageImpl<>(new ArrayList<>(areaOfConcerns));
        }

        if (checklistId != null) {
            Page<AreaOfConcern> areaOfConcerns = areaOfConcernRepository.findByChecklistsIdAndChecklistsAssessmentToolsId(checklistId, assessmentToolId, pageable);
            return areaOfConcerns;
        }
        if (assessmentToolId != null && stateId == null)
            return areaOfConcernRepository.findDistinctByChecklistsAssessmentToolsIdOrChecklistsIsNull(assessmentToolId, pageable);
        if (assessmentToolId == null && stateId != null)
            return areaOfConcernRepository.findAllDistinctByChecklistsStateIdOrChecklistsStateIdIsNull(stateId, pageable);
        return areaOfConcernRepository.findAllByStateAndAssessmentTool(assessmentToolId, stateId, pageable);
    }

    @RequestMapping(value = "/areaOfConcerns/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public AreaOfConcern delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, areaOfConcernRepository);
    }

    @RequestMapping(value = "/areaOfConcern/search/lastModifiedByState", method = {RequestMethod.GET})
    public Page<AreaOfConcern> findLastModifiedByState(@RequestParam("name") String name, @RequestParam("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        List<Integer> checklists = checklistService.getChecklistsForState(name);
        return areaOfConcernRepository.findAllByChecklistsIdInAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(checklists, lastModifiedDateTime, pageable);
    }
}