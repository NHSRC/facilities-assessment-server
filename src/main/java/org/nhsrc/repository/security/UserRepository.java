package org.nhsrc.repository.security;

import org.nhsrc.domain.security.User;
import org.nhsrc.repository.NonTxDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends NonTxDataRepository<User> {
    @Override
    @RestResource(exported = false)
    User findByUuid(UUID uuid);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Iterable<User> findAll(Sort sort);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Page<User> findAll(Pageable pageable);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    User findOne(Integer integer);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    boolean exists(Integer integer);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Iterable<User> findAll();

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Iterable<User> findAll(Iterable<Integer> iterable);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    long count();

    @PreAuthorize("permitAll()")
    User findByEmail(String email);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<User> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByInactive", rel = "findByInactive")
    Page<User> findByInactive(@Param("inactive") Boolean inactive, Pageable pageable);
}
