package org.nhsrc.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T> extends PagingAndSortingRepository<T, Long> {
    T find(UUID uuid);
}
