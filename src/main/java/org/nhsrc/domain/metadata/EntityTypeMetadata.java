package org.nhsrc.domain.metadata;

import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.nin.NinSyncType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entity_type_metadata")
public class EntityTypeMetadata extends BaseEntity {
    public static final String BULK_MODIFICATION_DATE_NAME = "BulkModificationDate";

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EntityType type;

    @Column(name = "name")
    private String name;

    @Column(name = "value_date")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateValue;

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }
}