package org.nhsrc.repository;

import org.nhsrc.domain.Checklist;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChecklistRepository extends BaseRepository<Checklist> {
}
