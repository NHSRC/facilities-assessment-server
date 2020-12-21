package org.nhsrc.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "facility_type")
public class FacilityType extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @JsonIgnore
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

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
