package org.nhsrc.repository.security;

import org.nhsrc.domain.security.Role;
import org.nhsrc.repository.BaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "role", path = "role")
public interface RoleRepository extends BaseRepository<Role> {
    Role findByRole(String name);
}