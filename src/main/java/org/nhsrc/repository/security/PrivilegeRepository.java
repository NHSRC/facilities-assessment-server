package org.nhsrc.repository.security;

import org.nhsrc.domain.security.Privilege;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "privilege", path = "privilege")
@PreAuthorize(value = "hasAnyAuthority('Privilege_Write')")
public interface PrivilegeRepository extends PagingAndSortingRepository<Privilege, Integer> {
    @RestResource(path = "findAllById", rel = "findAllById")
    List<Privilege> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByRoleId", rel = "findByRoleId")
    List<Privilege> findByIdIn(@Param("userId") Integer userId);
}