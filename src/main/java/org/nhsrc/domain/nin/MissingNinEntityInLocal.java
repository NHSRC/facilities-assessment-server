package org.nhsrc.domain.nin;

import org.nhsrc.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "missing_nin_entity_in_local")
public class MissingNinEntityInLocal extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private FacilityEntityType type;

    public MissingNinEntityInLocal() {
    }

    public MissingNinEntityInLocal(String name, FacilityEntityType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityEntityType getType() {
        return type;
    }

    public void setType(FacilityEntityType type) {
        this.type = type;
    }
}