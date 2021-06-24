package org.nhsrc.web;

import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.repository.FacilityRepository;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.AssessmentTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class FacilityTypeController {
    private final FacilityTypeRepository facilityTypeRepository;
    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityTypeController(FacilityTypeRepository facilityTypeRepository, FacilityRepository facilityRepository) {
        this.facilityTypeRepository = facilityTypeRepository;
        this.facilityRepository = facilityRepository;
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

    @RequestMapping(value = "/facilityType/search/findByDistrict", method = {RequestMethod.GET})
    public List<FacilityType> find(@RequestParam(value = "districtId") Integer districtId,
                                   @RequestParam(value = "inactive", required = false) Boolean inactive) {
        Boolean inactiveParam = inactive != null && inactive;
        List<Facility> facilities = facilityRepository.findAllByDistrictIdAndInactive(districtId, inactiveParam);
        return facilities.stream().map(Facility::getFacilityType).distinct().collect(Collectors.toList());
    }
}
