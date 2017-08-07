package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityAssessment;
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

    FacilityAssessment findByFacilityAndAssessmentToolAndStartDateBeforeAndStartDateAfter(Facility facility, AssessmentTool assessmentTool, Date startDateBefore, Date startDateAfter);

    List<FacilityAssessment> findByLastModifiedDateGreaterThan(Date lastModifiedDateTime);

    FacilityAssessment findByFacilityAndAssessmentToolAndSeriesName(Facility facility, AssessmentTool assessmentTool, String seriesName);
}
