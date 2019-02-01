package org.nhsrc.web;

import org.nhsrc.domain.FacilityType;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AssessmentTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class FacilityTypeController {
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    public FacilityTypeController(FacilityTypeRepository facilityTypeRepository) {
        this.facilityTypeRepository = facilityTypeRepository;
    }

    @RequestMapping(value = "/facilityTypes", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public FacilityType save(@RequestBody AssessmentTypeRequest request) {
        FacilityType facilityType = Repository.findByUuidOrCreate(request.getUuid(), facilityTypeRepository, new FacilityType());
        facilityType.setName(request.getName());
        facilityType.setInactive(request.getInactive());
        return facilityTypeRepository.save(facilityType);
    }
}