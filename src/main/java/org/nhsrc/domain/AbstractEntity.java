package org.nhsrc.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
abstract class AbstractEntity extends AbstractTransactionalEntity {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private UUID uuid;


    @Column(name = "inactive", nullable = false)
    private Boolean inactive = false;

    AbstractEntity() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

}

