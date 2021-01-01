package org.nhsrc.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "assessmentCustomInfo", path = "assessmentCustomInfo")
public interface AssessmentCustomInfoRepository {

}