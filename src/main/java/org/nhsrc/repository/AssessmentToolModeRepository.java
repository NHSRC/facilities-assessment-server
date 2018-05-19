package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentToolMode;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "assessmentToolMode", path = "assessmentToolMode")
public interface AssessmentToolModeRepository extends BaseRepository<AssessmentToolMode> {
    AssessmentToolMode findByName(String name);
}