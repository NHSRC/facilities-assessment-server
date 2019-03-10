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
@RepositoryRestResource(collectionResourceRel = "checkpointScore", path = "checkpointScore")
@PreAuthorize("hasRole('Assessment_Read')")
public interface CheckpointScoreRepository extends BaseRepository<CheckpointScore> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<CheckpointScore> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "lastModifiedByDeviceId", rel = "lastModifiedByDeviceId")
    @PreAuthorize("permitAll()")
    Page<CheckpointScore> findByFacilityAssessmentFacilityAssessmentDevicesDeviceIdAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("deviceId") String deviceId, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    CheckpointScore findByCheckpointAndFacilityAssessmentAndChecklist(Checkpoint checkpoint, FacilityAssessment facilityAssessment, Checklist checklist);

    Page<CheckpointScore> findByFacilityAssessmentFacilityDistrictStateAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(State state, Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "byAssessmentId", rel = "byAssessmentId")
    Page<CheckpointScore> findByFacilityAssessmentId(@Param("assessmentId") int assessmentId, Pageable pageable);

    List<CheckpointScore> findByFacilityAssessmentIdAndChecklistName(int assessmentId, String checklistName);
    CheckpointScore findFirstByFacilityAssessmentId(int assessmentId);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<CheckpointScore> findByIdIn(@Param("ids") Integer[] ids);

    void deleteAllByFacilityAssessmentId(@Param("facilityAssessmentId") Integer facilityAssessmentId);
}
