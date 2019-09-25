package org.nhsrc.web;

import org.nhsrc.domain.FacilityType;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AssessmentTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('Facility_Metadata_Write')")
    public FacilityType save(@RequestBody AssessmentTypeRequest request) {
        FacilityType facilityType = Repository.findByUuidOrCreate(request.getUuid(), facilityTypeRepository, new FacilityType());
        facilityType.setName(request.getName());
        facilityType.setInactive(request.getInactive());
        return facilityTypeRepository.save(facilityType);
    }

    @RequestMapping(value = "/facilityTypes/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Facility_Metadata_Write')")
    public FacilityType delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, facilityTypeRepository);
    }
}