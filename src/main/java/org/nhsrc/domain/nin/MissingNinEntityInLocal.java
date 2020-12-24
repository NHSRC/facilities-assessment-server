package org.nhsrc.domain.nin;

import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.metadata.EntityType;

import javax.persistence.*;

@Entity
@Table(name = "missing_nin_entity_in_local")
public class MissingNinEntityInLocal extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EntityType type;

    public MissingNinEntityInLocal() {
    }

    public MissingNinEntityInLocal(String name, EntityType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }
}