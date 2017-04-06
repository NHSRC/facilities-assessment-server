package org.nhsrc.repository;

import org.nhsrc.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

@Repository
@Transactional
public interface DepartmentRepository extends BaseRepository<Department> {
    Page<Department> findByLastModifiedDateGreaterThanOrderById(@DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastModifiedDateTime, Pageable pageable);

}
