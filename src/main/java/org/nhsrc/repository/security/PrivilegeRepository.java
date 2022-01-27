package org.nhsrc.repository.security;

import org.nhsrc.domain.security.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "privilege", path = "privilege")
public interface PrivilegeRepository extends PagingAndSortingRepository<Privilege, Integer> {
    @RestResource(path = "findAllById", rel = "findAllById")
    List<Privilege> findByIdIn(@Param("ids") Integer[] ids);

    @RestResource(path = "findByRoleId", rel = "findByRoleId")
    List<Privilege> findByIdIn(@Param("userId") Integer userId);

    Privilege findByName(String name);

    @Override
    @PreAuthorize("hasRole('Privilege_Write')")
    <S extends Privilege> S save(S s);

    @Override
    @PreAuthorize("hasRole('Privilege_Write')")
    <S extends Privilege> Iterable<S> save(Iterable<S> iterable);

    @Override
    @PreAuthorize("hasRole('Privilege_Write')")
    void delete(Integer integer);

    @Override
    @PreAuthorize("hasRole('Privilege_Write')")
    void delete(Privilege privilege);

    @Override
    @PreAuthorize("hasRole('Privilege_Write')")
    void delete(Iterable<? extends Privilege> iterable);

    @Override
    @PreAuthorize("hasRole('Privilege_Write')")
    void deleteAll();
}
