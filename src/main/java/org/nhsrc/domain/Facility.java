package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "facility")
public class Facility extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = District.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    @NotNull
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_type_id", nullable = false)
    private FacilityType facilityType;

    @Column(name = "hmis_code")
    private String hmisCode;

    @JsonIgnore
    public String getHmisCode() {
        return hmisCode;
    }

    public void setHmisCode(String hmisCode) {
        this.hmisCode = hmisCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }
}
