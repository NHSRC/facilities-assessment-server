package org.nhsrc.web;

import org.nhsrc.domain.District;
import org.nhsrc.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/district")
@RestController
public class DistrictController {
    private final DistrictRepository districtRepository;

    @Autowired
    public DistrictController(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<District> create(@RequestBody District district) {
        return new ResponseEntity<>(districtRepository.save(district), HttpStatus.CREATED);
    }

}
