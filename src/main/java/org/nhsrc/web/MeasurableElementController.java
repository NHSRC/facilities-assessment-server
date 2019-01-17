package org.nhsrc.web;

import org.nhsrc.domain.MeasurableElement;
import org.nhsrc.domain.Standard;
import org.nhsrc.repository.MeasurableElementRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StandardRepository;
import org.nhsrc.web.contract.MeasurableElementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class MeasurableElementController {
    private final StandardRepository standardRepository;
    private final MeasurableElementRepository measurableElementRepository;

    @Autowired
    public MeasurableElementController(StandardRepository standardRepository, MeasurableElementRepository measurableElementRepository) {
        this.standardRepository = standardRepository;
        this.measurableElementRepository = measurableElementRepository;
    }

    @RequestMapping(value = "/measurableElements", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public MeasurableElement save(@RequestBody MeasurableElementRequest request) {
        MeasurableElement measurableElement = Repository.findByUuidOrCreate(request.getUuid(), measurableElementRepository, new MeasurableElement());
        measurableElement.setName(request.getName());
        measurableElement.setReference(request.getReference());
        measurableElement.setStandard(Repository.findByUuidOrId(request.getStandardUUID(), request.getStandardId(), standardRepository));
        return measurableElementRepository.save(measurableElement);
    }

    @RequestMapping(value = "/measurableElement/search/find", method = {RequestMethod.GET})
    public Page<MeasurableElement> find(@RequestParam(value = "standardId", required = false) Integer standardId,
                                        @RequestParam(value = "stateId", required = false) Integer stateId,
                                        @RequestParam(value = "areaOfConcernId", required = false) Integer areaOfConcernId,
                                        @RequestParam(value = "checklistId", required = false) Integer checklistId,
                                        @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                                        Pageable pageable) {
        if (standardId != null)
            return measurableElementRepository.findDistinctByStandardId(standardId, pageable);
        if (areaOfConcernId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernId(areaOfConcernId, pageable);
        if (checklistId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsId(checklistId, pageable);
        if (assessmentToolId != null && stateId == null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsAssessmentToolId(assessmentToolId, pageable);
        if (assessmentToolId == null && stateId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsStateIdOrStandardAreaOfConcernChecklistsStateIsNull(stateId, pageable);
        if (assessmentToolId != null && stateId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsStateIdOrStandardAreaOfConcernChecklistsStateIsNullAndStandardAreaOfConcernChecklistsAssessmentToolId(stateId, assessmentToolId, pageable);
        return measurableElementRepository.findAll(pageable);
    }
}