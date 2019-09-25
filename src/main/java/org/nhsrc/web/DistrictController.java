package org.nhsrc.web;

import org.nhsrc.domain.District;
import org.nhsrc.repository.DistrictRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.web.contract.DistrictRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class DistrictController {
    private DistrictRepository districtRepository;
    private StateRepository stateRepository;

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
}