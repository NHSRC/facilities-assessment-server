package org.nhsrc.repository.metadata;

import org.nhsrc.domain.metadata.AssessmentMetaData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssessmentMetaDataRepository extends PagingAndSortingRepository<AssessmentMetaData, Integer> {
    AssessmentMetaData findByUuid(UUID uuid);
    AssessmentMetaData findByName(String name);
}