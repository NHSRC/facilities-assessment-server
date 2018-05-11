package org.nhsrc.repository;

import org.nhsrc.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "facilityAssessment", path = "facilityAssessment")
public interface FacilityAssessmentRepository extends BaseRepository<FacilityAssessment> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<FacilityAssessment> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "lastModifiedByDeviceId", rel = "lastModifiedByDeviceId")
    Page<FacilityAssessment> findByFacilityAssessmentDevicesDeviceIdAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("deviceId") String deviceId, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    FacilityAssessment findByFacilityAndAssessmentToolAndStartDateBeforeAndStartDateAfter(Facility facility, AssessmentTool assessmentTool, Date startDateBefore, Date startDateAfter);

    List<FacilityAssessment> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAsc(Date lastModifiedDateTime);

    List<FacilityAssessment> findByFacilityAssessmentDevicesDeviceIdAndLastModifiedDateGreaterThan(String deviceId, Date lastModifiedDateTime);

    FacilityAssessment findByFacilityAndAssessmentToolAndSeriesName(Facility facility, AssessmentTool assessmentTool, String seriesName);

    FacilityAssessment findByFacilityNameAndAssessmentToolAndSeriesName(String facilityName, AssessmentTool assessmentTool, String seriesName);

    Page<FacilityAssessment> findByFacilityDistrictStateAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(State state, Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "byAssessmentId", rel = "byAssessmentId")
    Page<FacilityAssessment> findById(@Param("assessmentId") int assessmentId, Pageable pageable);

    FacilityAssessment findById(int assessmentId);
}