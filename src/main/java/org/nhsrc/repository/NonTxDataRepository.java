package org.nhsrc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@NoRepositoryBean
public interface NonTxDataRepository<T> extends PagingAndSortingRepository<T, Integer>, BaseRepository<T> {
    @Override
    @RestResource(exported = false)
    T findByUuid(UUID uuid);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Iterable<T> findAll(Sort sort);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Page<T> findAll(Pageable pageable);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    T findOne(Integer integer);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    boolean exists(Integer integer);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Iterable<T> findAll();

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    Iterable<T> findAll(Iterable<Integer> iterable);

    @Override
    @PreAuthorize("hasRole('Users_Read')")
    long count();

    @Override
    @RestResource(exported = false)
    <S extends T> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends T> Iterable<S> save(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void delete(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(T entity);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends T> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
