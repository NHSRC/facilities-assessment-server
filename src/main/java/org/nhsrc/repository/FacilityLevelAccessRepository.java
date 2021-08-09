package org.nhsrc.repository;

import org.nhsrc.domain.FacilityLevelAccess;
import org.nhsrc.domain.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface FacilityLevelAccessRepository extends CrudRepository<FacilityLevelAccess, Integer> {
    List<FacilityLevelAccess> findByUserId(int id);
}
