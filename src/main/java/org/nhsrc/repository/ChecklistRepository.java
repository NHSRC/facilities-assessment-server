package org.nhsrc.repository;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "checklist", path = "checklist")
public interface ChecklistRepository extends NonTxDataRepository<Checklist> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Checklist> findByInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "forAssessmentTool", rel = "forAssessmentTool")
    Page<Checklist> findByAssessmentToolsUuidAndInactiveFalseAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("assessmentToolUuid") UUID assessmentToolUuid, @Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);

    @RestResource(path = "byAssessmentTool", rel = "byAssessmentTool")
    Page<Checklist> findByAssessmentToolsNameAndInactiveFalseOrderByName(@Param("assessmentToolName") String assessmentToolName, Pageable pageable);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Checklist> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByAssessmentTool", rel = "findByAssessmentTool")
    Page<Checklist> findByAssessmentToolsId(@Param("assessmentToolId") int assessmentToolId, Pageable pageable);

    List<Checklist> findAllByAssessmentToolsIdAndName(@Param("assessmentToolId") int assessmentToolId, @Param("name") String name);

    @RestResource(path = "findByState", rel = "findByState")
    Page<Checklist> findByStateId(@Param("stateId") int stateId, Pageable pageable);

    @RestResource(path = "findByAreaOfConcern", rel = "findByAreaOfConcern")
    Page<Checklist> findByAreasOfConcernId(@Param("areaOfConcernId") Integer areaOfConcernId, Pageable pageable);
    List<Checklist> findByAreasOfConcernId(@Param("areaOfConcernId") Integer areaOfConcernId);

    @RestResource(path = "find", rel = "find")
    Page<Checklist> findByStateIdAndAssessmentToolsId(@Param("stateId") Integer stateId, @Param("assessmentToolId") Integer assessmentToolId, Pageable pageable);

    @Query("SELECT c FROM Checklist c inner join c.assessmentTools as at WHERE (c.state.id = :stateId or c.state is null) and at.id = :assessmentToolId order by at.name, c.name")
    Page<Checklist> findByAssessmentToolsIdAndStateIdOrStateIsNull(@Param("assessmentToolId") Integer assessmentToolId, @Param("stateId") Integer stateId, Pageable pageable);

    Checklist findByNameAndAssessmentTools(@Param("String") String name, @Param("assessmentTool") AssessmentTool assessmentTool);

    @Query("SELECT cl FROM FacilityAssessmentMissingCheckpoint famc inner join famc.missingCheckpoint mc inner join mc.checklist cl WHERE famc.facilityAssessment.id = :facilityAssessmentId")
    List<Checklist> findUniqueChecklistsMissingInCheckpointsForFacilityAssessment(@Param("facilityAssessmentId") Integer facilityAssessmentId);

    List<Checklist> findAllBy(Pageable pageable);

    @Override
    default Page<Checklist> findAll(Pageable pageable) {
        List<Checklist> all = findAllBy(pageable);
        List<Checklist> list = all.stream().distinct().collect(Collectors.toList());
        return new PageImpl<>(list, pageable, list.size());
    }
}
