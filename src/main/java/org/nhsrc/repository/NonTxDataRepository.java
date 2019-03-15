package org.nhsrc.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@NoRepositoryBean
public interface NonTxDataRepository<T> extends PagingAndSortingRepository<T, Integer>, BaseRepository<T> {
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
