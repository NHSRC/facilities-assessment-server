package org.nhsrc.repository.missing;

import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.domain.missing.MissingChecklist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "missingChecklist", path = "missingChecklist")
@PreAuthorize("hasRole('Assessment_Read')")
public interface MissingChecklistRepository extends PagingAndSortingRepository<MissingChecklist, Integer> {
    List<MissingChecklist> findAllByFacilityAssessment(@Param("facilityAssessment") FacilityAssessment facilityAssessment);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<MissingChecklist> findByIdIn(@Param("ids") Integer[] ids);
}