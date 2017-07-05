package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.UUID;


@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractScoreEntity implements Persistable<UUID> {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, insertable = false, nullable = false, columnDefinition = "serial")
    private Integer id;

    @Id
    @Column(name = "uuid", updatable = false, unique = true)
    @NotNull
    private UUID uuid;

    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private java.util.Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private java.util.Date lastModifiedDate;

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @JsonIgnore
    public Integer getDBId() {
        return id;
    }

    public java.util.Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.util.Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(java.util.Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.uuid == null;
    }
}

