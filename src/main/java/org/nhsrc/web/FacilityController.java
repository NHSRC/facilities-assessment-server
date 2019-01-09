package org.nhsrc.web;

import org.nhsrc.domain.Facility;
import org.nhsrc.domain.Standard;
import org.nhsrc.repository.DistrictRepository;
import org.nhsrc.repository.FacilityRepository;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.FacilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class FacilityController {
    private DistrictRepository districtRepository;
    private FacilityTypeRepository facilityTypeRepository;
    private FacilityRepository facilityRepository;

    @Autowired
    public FacilityController(DistrictRepository districtRepository, FacilityTypeRepository facilityTypeRepository, FacilityRepository facilityRepository) {
        this.districtRepository = districtRepository;
        this.facilityTypeRepository = facilityTypeRepository;
        this.facilityRepository = facilityRepository;
    }

    @RequestMapping(value = "/facilitys", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public Facility save(@RequestBody FacilityRequest request) {
        Facility facility = Repository.findByUuidOrCreate(request.getUuid(), facilityRepository, new Facility());
        facility.setName(request.getName());
        facility.setDistrict(Repository.findByUuidOrId(request.getDistrictUUID(), request.getDistrictId(), districtRepository));
        facility.setFacilityType(Repository.findByUuidOrId(request.getFacilityTypeUUID(), request.getFacilityTypeId(), facilityTypeRepository));
        return facilityRepository.save(facility);
    }

    @RequestMapping(value = "/facility/search/find", method = {RequestMethod.GET})
    public Page<Facility> find(@RequestParam(value = "stateId", required = false) Integer stateId,
                               @RequestParam(value = "districtId", required = false) Integer districtId,
                               @RequestParam(value = "facilityTypeId", required = false) Integer facilityTypeId,
                               Pageable pageable) {
        if (districtId != null)
            return facilityTypeId == null ? facilityRepository.findByDistrictId(districtId, pageable) : facilityRepository.findByDistrictIdAndFacilityTypeId(districtId, facilityTypeId, pageable);
        if (stateId != null)
            return facilityTypeId == null ? facilityRepository.findByDistrictStateId(stateId, pageable) : facilityRepository.findByDistrictStateIdAndFacilityTypeId(stateId, facilityTypeId, pageable);
        if (facilityTypeId != null)
            return facilityRepository.findByFacilityTypeId(facilityTypeId, pageable);
        return facilityRepository.findAll(pageable);
    }

}