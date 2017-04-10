package org.nhsrc.repository;

import org.nhsrc.domain.AssessmentTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

public interface AssessmentToolRepository extends BaseRepository<AssessmentTool> {
    Page<AssessmentTool> findByLastModifiedDateGreaterThanOrderById(@DateTimeFormat(pattern = DATE_TIME_FORMAT_STRING) Date lastModifiedDateTime, Pageable pageable);
}
