package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.ExcludedAssessmentToolState;
import org.nhsrc.domain.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "excludedAssessmentToolState", path = "excludedAssessmentToolState")
public interface ExcludedAssessmentToolStateRepository extends PagingAndSortingRepository<ExcludedAssessmentToolState, Integer>, BaseRepository<ExcludedAssessmentToolState> {
    ExcludedAssessmentToolState findFirstByAssessmentToolAndState(AssessmentTool assessmentTool, State state);

    @RestResource(path = "lastModifiedByState", rel = "lastModifiedByState")
    Page<ExcludedAssessmentToolState> findByStateNameAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("name") String name, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    List<ExcludedAssessmentToolState> findByStateId(int stateId);
}