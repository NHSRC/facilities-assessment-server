package org.nhsrc.repository;

import org.nhsrc.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "facilityAssessment", path = "facilityAssessment")
@PreAuthorize("hasRole('Assessment_Read')")
public interface FacilityAssessmentRepository extends TxDataRepository<FacilityAssessment> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<FacilityAssessment> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "lastModifiedByDeviceId", rel = "lastModifiedByDeviceId")
    @PreAuthorize("permitAll()")
    Page<FacilityAssessment> findByFacilityAssessmentDevicesDeviceIdAndLastModifiedDateGreaterThanEqualOrderByLastModifiedDateAscIdAsc(@Param("deviceId") String deviceId, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    List<FacilityAssessment> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAsc(Date lastModifiedDateTime);

    @PreAuthorize("permitAll()")
    Page<FacilityAssessment> findByFacilityAssessmentDevicesDeviceIdAndLastModifiedDateGreaterThan(String deviceId, Date lastModifiedDateTime, Pageable pageable);

    @PreAuthorize("permitAll()")
    FacilityAssessment findByFacilityAndAssessmentToolAndSeriesName(Facility facility, AssessmentTool assessmentTool, String seriesName);

    @PreAuthorize("permitAll()")
    FacilityAssessment findByFacilityNameAndAssessmentToolAndSeriesName(String facilityName, AssessmentTool assessmentTool, String seriesName);

    @RestResource(path = "byAssessmentId", rel = "byAssessmentId")
    Page<FacilityAssessment> findById(@Param("assessmentId") int assessmentId, Pageable pageable);

    @PreAuthorize("permitAll()")
    FacilityAssessment findById(int assessmentId);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<FacilityAssessment> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<FacilityAssessment> findByAssessmentToolId(@Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    Page<FacilityAssessment> findByAssessmentToolIdAndEndDateGreaterThanEqualOrderByEndDateAscIdAsc(Integer assessmentToolId, Date endDateTime, Pageable pageable);

    @RestResource(path = "findByFacility", rel = "findByFacility")
    Page<FacilityAssessment> findByFacilityId(@Param("facilityId") Integer facilityId, Pageable pageable);

    @RestResource(path = "findByDistrict", rel = "findByDistrict")
    Page<FacilityAssessment> findByFacilityDistrictId(@Param("districtId") Integer districtId, Pageable pageable);

    @RestResource(path = "findByAssessmentType", rel = "findByAssessmentType")
    Page<FacilityAssessment> findByAssessmentTypeId(@Param("assessmentTypeId") Integer assessmentTypeId, Pageable pageable);

    @RestResource(path = "find", rel = "find")
    Page<FacilityAssessment> findByFacilityDistrictIdAndAssessmentToolId(@Param("districtId") Integer districtId, @Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);
}