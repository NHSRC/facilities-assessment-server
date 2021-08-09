package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.nhsrc.domain.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "facility")
public class Facility extends AbstractEntity {
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facility")
    private Set<FacilityLevelAccess> facilityLevelAccessList = new HashSet<>();

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

    public List<Integer> getUserIdsWithAccess() {
        return facilityLevelAccessList.stream().map(x -> x.getUser().getId()).collect(Collectors.toList());
    }

    public void removeUserAccess(User user) {
        this.facilityLevelAccessList.stream().filter(x -> x.getUser().equals(user)).findAny().ifPresent(facilityLevelAccess -> this.facilityLevelAccessList.remove(facilityLevelAccess));
    }

    public void addUserAccess(User user) {
        FacilityLevelAccess facilityLevelAccess = new FacilityLevelAccess();
        facilityLevelAccess.setUser(user);
        facilityLevelAccess.setFacility(this);
        this.facilityLevelAccessList.add(facilityLevelAccess);
    }
}
