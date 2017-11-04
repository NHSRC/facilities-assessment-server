package org.nhsrc.web;

import org.nhsrc.domain.Facility;
import org.nhsrc.repository.DistrictRepository;
import org.nhsrc.repository.FacilityRepository;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.web.contract.FacilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/facilities", method = RequestMethod.POST)
    @Transactional
    void save(@RequestBody FacilityRequest facilityRequest) {
        Facility facility = facilityRepository.findByUuid(UUID.fromString(facilityRequest.getUuid()));
        if (facility == null) {
            facility = new Facility();
            facility.setUuid(UUID.fromString(facilityRequest.getUuid()));
        }
        facility.setName(facilityRequest.getName());
        facility.setDistrict(districtRepository.findByUuid(UUID.fromString(facilityRequest.getDistrictUUID())));
        facility.setFacilityType(facilityTypeRepository.findByUuid(UUID.fromString(facilityRequest.getFacilityTypeUUID())));
        facilityRepository.save(facility);
    }
}