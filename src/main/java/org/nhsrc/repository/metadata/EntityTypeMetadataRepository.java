package org.nhsrc.repository.metadata;


import org.nhsrc.domain.metadata.EntityTypeMetadata;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EntityTypeMetadataRepository extends PagingAndSortingRepository<EntityTypeMetadata, Integer> {
}