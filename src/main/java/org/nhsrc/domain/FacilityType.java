package org.nhsrc.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "facility_type")
public class FacilityType extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityType() {
    }

    public FacilityType(String name) {
        this.name = name;
    }
}
