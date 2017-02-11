package org.nhsrc.repository;

import org.nhsrc.domain.Facility;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FacilityRepository extends BaseRepository<Facility> {

}
