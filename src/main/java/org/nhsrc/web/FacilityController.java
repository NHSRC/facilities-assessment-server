package org.nhsrc.web;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.FacilityRequest;
import org.nhsrc.web.contract.adhoc.NonCodedFacilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class FacilityController {
    private DistrictRepository districtRepository;
    private FacilityTypeRepository facilityTypeRepository;
    private FacilityRepository facilityRepository;
    private StateRepository stateRepository;

    @Autowired
    public FacilityController(DistrictRepository districtRepository, FacilityTypeRepository facilityTypeRepository, FacilityRepository facilityRepository, StateRepository stateRepository) {
        this.districtRepository = districtRepository;
        this.facilityTypeRepository = facilityTypeRepository;
        this.facilityRepository = facilityRepository;
        this.stateRepository = stateRepository;
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
                               Pageable pageable) {
        if (districtId != null)
            return facilityTypeId == null ? facilityRepository.findByDistrictId(districtId, pageable) : facilityRepository.findByDistrictIdAndFacilityTypeId(districtId, facilityTypeId, pageable);
        if (stateId != null)
            return facilityTypeId == null ? facilityRepository.findByDistrictStateId(stateId, pageable) : facilityRepository.findByDistrictStateIdAndFacilityTypeId(stateId, facilityTypeId, pageable);
        if (facilityTypeId != null)
            return facilityRepository.findByFacilityTypeId(facilityTypeId, pageable);
        return facilityRepository.findAll(pageable);
    }

    @RequestMapping(value = "/facility/noncoded", method = {RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Facility_Write')")
    public String update(@RequestBody NonCodedFacilityRequest request) {
        return this.head(request);
    }

    @RequestMapping(value = "/facility/noncoded", method = {RequestMethod.GET})
    public String head(NonCodedFacilityRequest request) {
        String status = hasFacility(request.getName(), request.getState(), request.getDistrict(), request.getFacilityType());
        String sanitisedStatus;
        if (status.equals("NotFound")) {
            sanitisedStatus = hasFacility(Facility.sanitiseName(request.getName()), request.getState(), request.getDistrict(), request.getFacilityType());
            if (sanitisedStatus.equals("Found"))
                status = "SanFound";
        }
        return String.format("%s\n", status);
    }

    public String hasFacility(String name, String stateName, String district, String facilityTypeName) {
        State state = stateRepository.findByName(stateName);

        List<Facility> facilities = facilityRepository.findByNameContainingIgnoreCaseAndDistrictState(name, state);
        if (facilities.size() == 1) return "Found";
        if (facilities.size() == 0) return "NotFound";
        if (facilities.stream().filter(facility -> facility.getName().equalsIgnoreCase(name)).toArray().length == 1) return "Found";

        facilities = facilityRepository.findByNameAndDistrictState(name, state);
        if (facilities.size() == 1) return "Found";
        if (facilities.size() == 0) return "NotFound";

        List<District> districts = districtRepository.findByNameContainingIgnoreCaseAndState(district, state);
        if (districts.size() == 0) return "NoDistrict";
        if (districts.size() > 1) {
            districts = districtRepository.findByNameAndState(district, state);
            if (districts.size() != 1)
                return "MultipleDistricts";
        }

        facilities = facilityRepository.findByNameContainingIgnoreCaseAndDistrict(name, districts.get(0));
        if (facilities.size() == 1) return "Found";
        if (facilities.size() == 0) return "NotFound";

        facilities = facilityRepository.findByNameAndDistrict(name, districts.get(0));
        if (facilities.size() == 1) return "Found";
        if (facilities.size() == 0) return "NotFound";

        FacilityType facilityType = FacilityType.COMMON_FACILITY_TYPE_NAMES.get(facilityTypeName);
        if (facilityType != null) {
            facilities = facilityRepository.findByNameContainingIgnoreCaseAndDistrictAndFacilityTypeName(name, districts.get(0), facilityType.getName());
            if (facilities.size() == 1) return "Found";
        }

        return "MultipleInDistrict";
    }
}