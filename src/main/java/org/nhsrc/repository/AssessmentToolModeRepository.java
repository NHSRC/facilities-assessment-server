package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentToolMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AssessmentToolModeRepository extends BaseRepository<AssessmentToolMode> {
    AssessmentToolMode findByName(String name);
}