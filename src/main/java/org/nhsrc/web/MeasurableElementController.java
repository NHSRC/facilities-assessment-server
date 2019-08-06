package org.nhsrc.web;

import org.nhsrc.domain.MeasurableElement;
import org.nhsrc.domain.ReferencableEntity;
import org.nhsrc.repository.MeasurableElementRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StandardRepository;
import org.nhsrc.web.contract.MeasurableElementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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
    @PreAuthorize("hasRole('Checklist_Write')")
    public ResponseEntity save(@RequestBody MeasurableElementRequest request) {
        MeasurableElement measurableElement = Repository.findByUuidOrCreate(request.getUuid(), measurableElementRepository, new MeasurableElement());
        MeasurableElement existingMeasurableElement = measurableElementRepository.findByStandardIdAndReference(request.getStandardId(), request.getReference());
        if (ReferencableEntity.isConflicting(existingMeasurableElement, measurableElement)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format("Measurable element with same reference code %s already exists in the standard.", request.getReference()));
        }

        measurableElement.setName(request.getName());
        measurableElement.setReference(request.getReference());
        measurableElement.setStandard(Repository.findByUuidOrId(request.getStandardUUID(), request.getStandardId(), standardRepository));
        measurableElement.setInactive(request.getInactive());
        return new ResponseEntity<>(measurableElementRepository.save(measurableElement), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/measurableElement/search/find", method = {RequestMethod.GET})
    public Page<MeasurableElement> find(@RequestParam(value = "standardId", required = false) Integer standardId,
                                        @RequestParam(value = "stateId", required = false) Integer stateId,
                                        @RequestParam(value = "areaOfConcernId", required = false) Integer areaOfConcernId,
                                        @RequestParam(value = "checklistId", required = false) Integer checklistId,
                                        @RequestParam(value = "assessmentToolId", required = false) Integer assessmentToolId,
                                        Pageable pageable) {
        if (standardId != null && checklistId == null)
            return measurableElementRepository.findDistinctByStandardId(standardId, pageable);
        if (standardId != null && checklistId != null)
            return measurableElementRepository.findDistinctByStandardIdAndCheckpointsChecklistId(standardId, checklistId, pageable);
        if (areaOfConcernId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernId(areaOfConcernId, pageable);
        if (checklistId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsId(checklistId, pageable);
        if (assessmentToolId != null && stateId == null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsAssessmentToolsId(assessmentToolId, pageable);
        if (assessmentToolId == null && stateId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsStateIdOrStandardAreaOfConcernChecklistsStateIsNull(stateId, pageable);
        if (assessmentToolId != null && stateId != null)
            return measurableElementRepository.findDistinctByStandardAreaOfConcernChecklistsStateIdOrStandardAreaOfConcernChecklistsStateIsNullAndStandardAreaOfConcernChecklistsAssessmentToolsId(stateId, assessmentToolId, pageable);
        return measurableElementRepository.findAll(pageable);
    }
}