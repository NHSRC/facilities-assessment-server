package org.nhsrc.web;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.MeasurableElement;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.ChecklistRequest;
import org.nhsrc.web.contract.MeasurableElementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/measurableElements", method = RequestMethod.POST)
    @Transactional
    void save(@RequestBody MeasurableElementRequest measurableElementRequest) {
        MeasurableElement measurableElement = measurableElementRepository.findByUuid(UUID.fromString(measurableElementRequest.getUuid()));
        if (measurableElement == null) {
            measurableElement = new MeasurableElement();
            measurableElement.setUuid(UUID.fromString(measurableElementRequest.getUuid()));
        }
        measurableElement.setName(measurableElementRequest.getName());
        measurableElement.setReference(measurableElementRequest.getReference());
        measurableElement.setStandard(standardRepository.findByUuid(UUID.fromString(measurableElementRequest.getStandardUUID())));
        measurableElementRepository.save(measurableElement);
    }
}