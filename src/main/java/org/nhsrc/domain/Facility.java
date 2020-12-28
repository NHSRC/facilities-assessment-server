package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "facility")
public class Facility extends AbstractEntity {
    private static String[] nameQualifiers = {"Sub-District Hospital", "DISTRICT HOSPITAL", "DMO", "MCH", "GOVT", "Community Health Centre", "District Women Hospital", "DWH", "DCH", "Area Hospital", "DH", "CHC", "BPHC", "(PHC)", "PHC", "UFWC", "COMMUNITY HEALTH CENTER", "SDCH", "(FRU)", "FRU", "RH", "SDH", "DMCH", "MCH", "GH"};
    private static String[] unnecessaryCharacters = {".", ","};

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = District.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    @NotNull
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_type_id", nullable = false)
    private FacilityType facilityType;

    @Column(name = "registry_unique_id")
    private String registryUniqueId;

    @JsonIgnore
    public String getRegistryUniqueId() {
        return registryUniqueId;
    }

    public void setRegistryUniqueId(String registryUniqueId) {
        this.registryUniqueId = registryUniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @JsonIgnore
    public FacilityType getFacilityType() {
        return facilityType;
    }

    @JsonProperty("facilityTypeId")
    public Integer _getFacilityTypeId() {
        return this.facilityType.getId();
    }

    @JsonProperty("districtId")
    public Integer _getDistrictId() {
        return this.district.getId();
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return this.district.getState().getId();
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public static String sanitiseName(String name) {
        for (String nameQualifier : nameQualifiers) {
            name = name.replace(nameQualifier, "");
        }
        for (String unnecessaryCharacter : unnecessaryCharacters) {
            name = name.replace(unnecessaryCharacter, "");
        }
        return name.trim();
    }
}
