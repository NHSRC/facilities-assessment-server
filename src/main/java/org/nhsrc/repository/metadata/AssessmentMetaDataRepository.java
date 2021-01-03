package org.nhsrc.repository.metadata;

import org.nhsrc.domain.metadata.AssessmentMetaData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "assessmentMetaData", path = "assessmentMetaData")
public interface AssessmentMetaDataRepository extends PagingAndSortingRepository<AssessmentMetaData, Integer> {
    AssessmentMetaData findByUuid(UUID uuid);
    AssessmentMetaData findByName(String name);
}