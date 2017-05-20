package org.nhsrc.repository.tag;

import org.nhsrc.domain.Tag;
import org.nhsrc.domain.tag.StandardTag;
import org.nhsrc.repository.BaseRepository;
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
@RepositoryRestResource(collectionResourceRel = "tag", path = "tag")
public interface TagRepository extends BaseRepository<Tag> {
    @RestResource(path = "lastModified", rel = "lastModified")
    Page<Tag> findByLastModifiedDateGreaterThanOrderById(@Param("lastModifiedDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable);
}