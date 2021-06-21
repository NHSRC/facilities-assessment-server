package org.nhsrc.repository.assessment;

import org.nhsrc.domain.assessment.AssessmentNumberAssignment;
import org.nhsrc.domain.security.Privilege;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "assessmentNumberAssignment", path = "assessmentNumberAssignment")
@PreAuthorize("hasRole('Users_Write')")
public interface AssessmentNumberAssignmentRepository extends PagingAndSortingRepository<AssessmentNumberAssignment, Integer> {
}
