package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentToolMode;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "assessmentToolMode", path = "assessmentToolMode")
public interface AssessmentToolModeRepository extends NonTxDataRepository<AssessmentToolMode> {
    AssessmentToolMode findByName(String name);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<AssessmentToolMode> findByIdIn(@Param("ids") Integer[] ids);
}