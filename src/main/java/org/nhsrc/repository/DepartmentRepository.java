package org.nhsrc.repository;

import org.nhsrc.domain.Checkpoint;
import org.nhsrc.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "department", path = "department")
public interface DepartmentRepository extends BaseRepository<Department> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Department> findByLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(@Param("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
    Department findByName(String name);

    @RestResource(path = "findAllById", rel = "findAllById")
    List<Department> findByIdIn(@Param("ids") Integer[] ids);
}
