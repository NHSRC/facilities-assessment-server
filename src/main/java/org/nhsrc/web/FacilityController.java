package org.nhsrc.web;

import org.nhsrc.domain.Facility;
import org.nhsrc.domain.assessment.AssessmentCustomInfo;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.repository.*;
import org.nhsrc.web.contract.FacilityRequest;
import org.nhsrc.web.contract.assessment.AssessmentCustomInfoResponse;
import org.nhsrc.web.contract.assessment.FacilityAssessmentResponse;
import org.nhsrc.web.contract.assessment.FacilityWithAssessmentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class FacilityController {
    private final DistrictRepository districtRepository;
    private final FacilityTypeRepository facilityTypeRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityAssessmentRepository facilityAssessmentRepository;

    @Autowired
    public FacilityController(DistrictRepository districtRepository, FacilityTypeRepository facilityTypeRepository, FacilityRepository facilityRepository, FacilityAssessmentRepository facilityAssessmentRepository) {
        this.districtRepository = districtRepository;
        this.facilityTypeRepository = facilityTypeRepository;
        this.facilityRepository = facilityRepository;
        this.facilityAssessmentRepository = facilityAssessmentRepository;
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

    @RequestMapping(value = "/facility/assessments", method = {RequestMethod.GET})
    public FacilityWithAssessmentsResponse get(@RequestParam(value = "registryUniqueId") String registryUniqueId) {
        Facility facility = facilityRepository.findByRegistryUniqueId(registryUniqueId);
        FacilityWithAssessmentsResponse response = new FacilityWithAssessmentsResponse();
        response.setFacilityType(facility.getFacilityType().getName());
        response.setFacilityName(facility.getName());
        response.setDistrictName(facility.getDistrict().getName());
        response.setRegistryUniqueId(facility.getRegistryUniqueId());
        response.setStateName(facility.getDistrict().getState().getName());

        List<FacilityAssessment> facilityAssessments = facilityAssessmentRepository.findAllByFacility(facility);
        List<FacilityAssessmentResponse> facilityAssessmentResponses = facilityAssessments.stream().map(facilityAssessment -> {
            FacilityAssessmentResponse facilityAssessmentResponse = new FacilityAssessmentResponse();
            facilityAssessmentResponse.setAssessmentNumber(facilityAssessment.getSeriesName());
            facilityAssessmentResponse.setAssessmentEndDate(facilityAssessment.getEndDate());
            facilityAssessmentResponse.setAssessmentStartDate(facilityAssessment.getStartDate());
            facilityAssessmentResponse.setUuid(facilityAssessment.getUuidString());
            List<AssessmentCustomInfoResponse> collect = facilityAssessment.getCustomInfos().stream().map(assessmentCustomInfo -> {
                AssessmentCustomInfoResponse assessmentCustomInfoResponse = new AssessmentCustomInfoResponse();
                assessmentCustomInfoResponse.setAssessmentMetaDataName(assessmentCustomInfo.getAssessmentMetaData().getName());
                assessmentCustomInfoResponse.setValueString(assessmentCustomInfo.getValueString());
                return assessmentCustomInfoResponse;
            }).collect(Collectors.toList());
            facilityAssessmentResponse.setCustomInfos(collect);
            return facilityAssessmentResponse;
        }).collect(Collectors.toList());
        response.setAssessments(facilityAssessmentResponses);
        return response;
    }
}
