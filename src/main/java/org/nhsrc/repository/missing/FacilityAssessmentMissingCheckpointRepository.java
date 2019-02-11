package org.nhsrc.repository.missing;

import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.FacilityAssessment;
import org.nhsrc.domain.missing.FacilityAssessmentMissingCheckpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "facilityAssessmentMissingCheckpoint", path = "facilityAssessmentMissingCheckpoint")
public interface FacilityAssessmentMissingCheckpointRepository extends PagingAndSortingRepository<FacilityAssessmentMissingCheckpoint, Integer> {
    List<FacilityAssessmentMissingCheckpoint> findAllByFacilityAssessmentAndMissingCheckpointChecklist(@Param("facilityAssessment") FacilityAssessment facilityAssessment, @Param("checklist") Checklist checklist);

    @RestResource(path = "findByFacilityAssessmentId", rel = "findByFacilityAssessmentId")
    Page<FacilityAssessmentMissingCheckpoint> findAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId, Pageable pageable);
}