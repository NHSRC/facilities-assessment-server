package org.nhsrc.web;

import org.nhsrc.domain.Standard;
import org.nhsrc.repository.AreaOfConcernRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StandardRepository;
import org.nhsrc.web.contract.StandardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class StandardController {
    private final StandardRepository standardRepository;
    private final AreaOfConcernRepository areaOfConcernRepository;

    @Autowired
    public StandardController(StandardRepository standardRepository, AreaOfConcernRepository areaOfConcernRepository) {
        this.standardRepository = standardRepository;
        this.areaOfConcernRepository = areaOfConcernRepository;
    }

    @RequestMapping(value = "/standards", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public Standard save(@RequestBody StandardRequest standardRequest) {
        Standard standard = Repository.findByUuidOrCreate(standardRequest.getUuid(), standardRepository, new Standard());
        standard.setName(standardRequest.getName());
        standard.setReference(standardRequest.getReference());
        standard.setAreaOfConcern(Repository.findByUuidOrId(standardRequest.getAreaOfConcernUUID(), standardRequest.getAreaOfConcernId(), areaOfConcernRepository));
        return standardRepository.save(standard);
    }

    @RequestMapping(value = "/standard/search/find", method = {RequestMethod.GET})
    public Page<Standard> find(@RequestParam(value = "areaOfConcernId", required = false) Integer areaOfConcernId,
                               @RequestParam(value = "checklistId", required = false) Integer checklistId,
                               @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                               Pageable pageable) {
        if (areaOfConcernId != null)
            return standardRepository.findByAreaOfConcernId(areaOfConcernId, pageable);
        if (checklistId != null)
            return standardRepository.findByAreaOfConcernChecklistsId(checklistId, pageable);
        if (assessmentToolId != null)
            return standardRepository.findByAreaOfConcernChecklistsAssessmentToolId(assessmentToolId, pageable);
        return standardRepository.findAll(pageable);
    }
}