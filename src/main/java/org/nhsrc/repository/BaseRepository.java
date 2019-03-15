package org.nhsrc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, Integer> {
    T findByUuid(UUID uuid);
}