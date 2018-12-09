package org.nhsrc.web;

import org.nhsrc.domain.District;
import org.nhsrc.repository.DistrictRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.web.contract.DistrictRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public District save(@RequestBody DistrictRequest districtRequest) {
        District district = districtRepository.findByUuid(UUID.fromString(districtRequest.getUuid()));
        if (district == null) {
            district = new District();
            district.setUuid(UUID.fromString(districtRequest.getUuid()));
        }
        district.setName(districtRequest.getName());
        district.setState(stateRepository.findByUuid(UUID.fromString(districtRequest.getStateUUID())));
        return districtRepository.save(district);
    }
}