package org.nhsrc.repository.security;

import org.nhsrc.domain.security.Role;
import org.nhsrc.repository.BaseRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "role", path = "role")
@PreAuthorize("hasRole('Users_Write')")
public interface RoleRepository extends BaseRepository<Role> {
    Role findByName(String name);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Role> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByUserId", rel = "findByUserId")
    List<Role> findByIdIn(@Param("userId") Integer userId);
}