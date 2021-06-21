package org.nhsrc.repository.security;

import org.nhsrc.domain.security.User;
import org.nhsrc.repository.NonTxDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
@PreAuthorize("hasRole('Users_Write')")
public interface UserRepository extends NonTxDataRepository<User> {
    @PreAuthorize("permitAll()")
    User findByEmail(String email);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<User> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByInactive", rel = "findByInactive")
    Page<User> findByInactive(@Param("inactive") Boolean inactive, Pageable pageable);
}
