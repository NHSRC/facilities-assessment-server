package org.nhsrc.repository;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "facility", path = "facility")
public interface FacilityRepository extends NonTxDataRepository<Facility> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Facility> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("effectiveLastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date effectiveLastModifiedDateTime, Pageable pageable);

    @RestResource(path = "lastModifiedByState", rel = "lastModifiedByState")
    Page<Facility> findByDistrictStateNameAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("name") String name, @Param("effectiveLastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date effectiveLastModifiedDateTime, Pageable pageable);

    @RestResource(path = "byDistrictAndFacilityType", rel = "byDistrictAndFacilityType")
    Page<Facility> findByDistrictNameAndFacilityTypeNameOrderByName(@Param("districtName") String districtName, @Param("facilityTypeName") String facilityTypeName, Pageable pageable);

    @RestResource(path = "byDistrict", rel = "byDistrict")
    Page<Facility> findByDistrictNameOrderByName(@Param("districtName") String districtName, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Facility> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByFacilityType", rel = "findByFacilityType")
    Page<Facility> findByFacilityTypeId(@Param("facilityTypeId") Integer facilityTypeId, Pageable pageable);

    @RestResource(path = "findByDistrict", rel = "findByDistrict")
    Page<Facility> findByDistrictId(@Param("districtId") Integer districtId, Pageable pageable);

    Page<Facility> findByDistrictIdAndFacilityTypeId(@Param("districtId") Integer districtId, @Param("facilityTypeId") Integer facilityTypeId, Pageable pageable);

    @RestResource(path = "findByState", rel = "findByState")
    Page<Facility> findByDistrictStateId(@Param("stateId") Integer stateId, Pageable pageable);

    Page<Facility> findByDistrictStateIdAndFacilityTypeId(@Param("stateId") Integer stateId, @Param("facilityTypeId") Integer facilityTypeId, Pageable pageable);

    List<Facility> findByNameContainingIgnoreCaseAndDistrictState(@Param("name") String name, @Param("state") State state);
    List<Facility> findByNameAndDistrictState(@Param("name") String name, @Param("state") State state);
    List<Facility> findByNameContainingIgnoreCaseAndDistrict(@Param("name") String name, @Param("district") District district);
    List<Facility> findByNameAndDistrict(@Param("name") String name, @Param("district") District district);
    Facility findByNameAndDistrictAndFacilityType(String name, District district, FacilityType facilityType);
    List<Facility> findByNameContainingIgnoreCaseAndDistrictAndFacilityTypeName(@Param("name") String name, @Param("district") District district, @Param("facilityTypeName") String facilityTypeName);
}
