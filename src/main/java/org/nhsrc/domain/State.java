package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "state")
public class State extends AbstractEntity {
    public State(String name) {
        this.name = name;
    }

    public State() {
    }

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "state")
    @RestResource(exported = false)
    private Set<District> districts = new HashSet<>();

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

    @JsonIgnore
    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }

    public void addDistricts(Collection<District> districts) {
        districts.forEach(districtToAdd -> {
            District existingDistrict = this.districts.stream().filter(district -> district.getName().equalsIgnoreCase(districtToAdd.getName())).findFirst().orElse(districtToAdd);
            existingDistrict.addFacilities(districtToAdd.getFacilities());
            this.districts.removeIf(district -> district.getName().equalsIgnoreCase(existingDistrict.getName()));
            this.districts.add(existingDistrict);

        });
    }
}
