package org.nhsrc.repository;

import org.nhsrc.config.DeploymentConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "deploymentAppConfiguration", path = "deploymentAppConfiguration")
public interface DeploymentConfigurationRepository extends BaseRepository<DeploymentConfiguration> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<DeploymentConfiguration> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
}