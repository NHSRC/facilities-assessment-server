package org.nhsrc.web;

import org.nhsrc.domain.District;
import org.nhsrc.domain.State;
import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.DistrictRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.web.contract.DistrictRequest;
import org.nhsrc.web.contract.DashboardFilterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class DistrictController {
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;

    @Autowired
    public DistrictController(DistrictRepository districtRepository, StateRepository stateRepository) {
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
    }

    @RequestMapping(value = "/districts", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Facility_Metadata_Write')")
    public District save(@RequestBody DistrictRequest districtRequest) {
        District district = Repository.findByUuidOrCreate(districtRequest.getUuid(), districtRepository, new District());
        district.setName(districtRequest.getName());
        district.setState(Repository.findByUuidOrId(districtRequest.getStateUUID(), districtRequest.getStateId(), stateRepository));
        district.setInactive(districtRequest.getInactive());
        return districtRepository.save(district);
    }

    @RequestMapping(value = "/districts/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Facility_Metadata_Write')")
    public District delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, districtRepository);
    }

    @RequestMapping(value = "/district/search/find", method = {RequestMethod.GET})
    public Page<District> find(@RequestParam(value = "stateId") Integer stateId,
                               @RequestParam(value = "inactive") Boolean inactive,
                               Pageable pageable) {
        return districtRepository.findAllByStateIdAndInactive(stateId, inactive, pageable);
    }

    @RequestMapping(value = "/dashboardDistrict/search/find", method = {RequestMethod.GET})
    public List<DashboardFilterResponse> dashboardDistrict(@RequestParam(value = "state") int stateId) {
        DashboardFilterResponse allDistricts = new DashboardFilterResponse(District.ALL_DISTRICTS.getId(), District.ALL_DISTRICTS.getName());
        List<DashboardFilterResponse> filters;
        if (stateId == District.ALL_DISTRICTS.getId()) {
            filters = new ArrayList<>();
            filters.add(allDistricts);
        } else {
            List<District> districts = districtRepository.findByStateIdAndInactiveFalse(stateId);
            filters = districts.stream().map(district -> new DashboardFilterResponse(district.getId(), district.getName())).sorted(Comparator.comparing(DashboardFilterResponse::getName)).collect(Collectors.toList());
            filters.add(0, allDistricts);
        }

        return filters;
    }
}
