package org.nhsrc.repository.assessment;

import jdk.Exported;
import org.joda.time.LocalDate;
import org.nhsrc.domain.assessment.AssessmentNumberAssignment;
import org.nhsrc.domain.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "assessmentNumberAssignment", path = "assessmentNumberAssignment")
public interface AssessmentNumberAssignmentRepository extends PagingAndSortingRepository<AssessmentNumberAssignment, Integer> {
    @RestResource(path = "findByState", rel = "findByState")
    Page<AssessmentNumberAssignment> findAllByFacilityDistrictStateId(@Param("stateId") int stateId, Pageable pageable);

    Page<AssessmentNumberAssignment> findAllByFacilityDistrictId(@Param("districtId") int districtId, Pageable pageable);

    List<AssessmentNumberAssignment> findAllByFacilityUuidAndAssessmentTypeUuidAndUsersInAndLastModifiedDateAfter(UUID facilityUuid, UUID assessmentTypeUuid, User[] users, Date date);

    default List<AssessmentNumberAssignment> getActiveAssessmentNumbers(String facilityUuid, String assessmentTypeUuid, User user) {
        LocalDate localDate = getDate();
        return findAllByFacilityUuidAndAssessmentTypeUuidAndUsersInAndLastModifiedDateAfter(UUID.fromString(facilityUuid), UUID.fromString(assessmentTypeUuid), new User[]{user}, localDate.toDate());
    }

    default LocalDate getDate() {
        return LocalDate.now().minusYears(1);
    }

    List<AssessmentNumberAssignment> findAllByFacilityUuidAndAssessmentTypeUuidAndLastModifiedDateAfterAndInactiveFalse(UUID facilityUuid, UUID assessmentTypeUuid, Date date);

    default boolean hasAnyActionAssessmentNumbers(String facilityUuid, String assessmentTypeUuid) {
        LocalDate localDate = getDate();
        List<AssessmentNumberAssignment> anas = findAllByFacilityUuidAndAssessmentTypeUuidAndLastModifiedDateAfterAndInactiveFalse(UUID.fromString(facilityUuid), UUID.fromString(assessmentTypeUuid), localDate.toDate());
        return anas.size() > 0;
    }

    @Override
    @RestResource(exported = false)
    <S extends AssessmentNumberAssignment> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends AssessmentNumberAssignment> Iterable<S> save(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void delete(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(AssessmentNumberAssignment assessmentNumberAssignment);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends AssessmentNumberAssignment> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
