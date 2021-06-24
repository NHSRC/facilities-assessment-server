package org.nhsrc.web;

import org.nhsrc.domain.Facility;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.FacilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class FacilityController {
    private DistrictRepository districtRepository;
    private FacilityTypeRepository facilityTypeRepository;
    private FacilityRepository facilityRepository;

    @Autowired
    public FacilityController(DistrictRepository districtRepository, FacilityTypeRepository facilityTypeRepository, FacilityRepository facilityRepository, StateRepository stateRepository) {
        this.districtRepository = districtRepository;
        this.facilityTypeRepository = facilityTypeRepository;
        this.facilityRepository = facilityRepository;
    }

    @RequestMapping(value = "/facilitys", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Facility_Write')")
    public Facility save(@RequestBody FacilityRequest request) {
        Facility facility = Repository.findByUuidOrCreate(request.getUuid(), facilityRepository, new Facility());
        facility.setName(request.getName());
        facility.setDistrict(Repository.findByUuidOrId(request.getDistrictUUID(), request.getDistrictId(), districtRepository));
        facility.setFacilityType(Repository.findByUuidOrId(request.getFacilityTypeUUID(), request.getFacilityTypeId(), facilityTypeRepository));
        facility.setInactive(request.getInactive());
        return facilityRepository.save(facility);
    }

    @RequestMapping(value = "/facility/search/find", method = {RequestMethod.GET})
    public Page<Facility> find(@RequestParam(value = "stateId", required = false) Integer stateId,
                               @RequestParam(value = "districtId", required = false) Integer districtId,
                               @RequestParam(value = "facilityTypeId", required = false) Integer facilityTypeId,
                               @RequestParam(value = "inactive", required = false) Boolean inactive,
                               Pageable pageable) {
        Boolean inactiveParam = inactive != null && inactive;
        if (districtId != null)
            return facilityTypeId == null ? facilityRepository.findByDistrictIdAndInactive(districtId, inactiveParam, pageable) : facilityRepository.findByDistrictIdAndFacilityTypeIdAndInactive(districtId, facilityTypeId, inactiveParam, pageable);
        if (stateId != null)
            return facilityTypeId == null ? facilityRepository.findByDistrictStateIdAndInactive(stateId, inactiveParam, pageable) : facilityRepository.findByDistrictStateIdAndFacilityTypeIdAndInactive(stateId, facilityTypeId, inactiveParam, pageable);
        if (facilityTypeId != null)
            return facilityRepository.findByFacilityTypeIdAndInactive(facilityTypeId, inactiveParam, pageable);
        return facilityRepository.findAllByInactive(inactiveParam, pageable);
    }

    @RequestMapping(value = "/facilitys/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Facility_Metadata_Write')")
    public Facility delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, facilityRepository);
    }
}
