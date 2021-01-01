package org.nhsrc.repository;

import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.FacilityAssessment;
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
@RepositoryRestResource(collectionResourceRel = "indicator", path = "indicator")
@PreAuthorize("hasRole('Assessment_Read')")
public interface IndicatorRepository extends TxDataRepository<Indicator> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Indicator> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "lastModifiedByDeviceId", rel = "lastModifiedByDeviceId")
    @PreAuthorize("permitAll()")
    Page<Indicator> findByFacilityAssessmentFacilityAssessmentDevicesDeviceIdAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("deviceId") String deviceId, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @PreAuthorize("permitAll()")
    Indicator findByIndicatorDefinitionAndFacilityAssessment(IndicatorDefinition indicatorDefinition, FacilityAssessment facilityAssessment);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Indicator> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByFacilityAssessment", rel = "findByFacilityAssessment")
    Page<Indicator> findByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId, Pageable pageable);

    List<Indicator> findByFacilityAssessmentIdOrderByIndicatorDefinitionSortOrder(Integer facilityAssessmentId);

    @PreAuthorize("permitAll()")
    void deleteAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId);
}
