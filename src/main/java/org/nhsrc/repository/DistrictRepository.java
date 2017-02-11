package org.nhsrc.repository;

import org.nhsrc.domain.District;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DistrictRepository extends BaseRepository<District> {

}
