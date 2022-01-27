package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "district")
public class District extends AbstractEntity {
    public static final District ALL_DISTRICTS = new District(-1, "All Districts");

    public District() {
    }

    public District(int id, String name) {
        this.name = name;
        this.setId(id);
    }

    public District(String name, State state) {
        this.name = name;
        this.state = state;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "district")
    @RestResource(exported = false)
    private Set<Facility> facilities = new HashSet<>();

    @ManyToOne(targetEntity = State.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @NotNull
    @RestResource(exported = false)
    private State state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return this.state.getId();
    }

    public void setState(State state) {
        this.state = state;
    }

    @JsonIgnore
    public Set<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

    public void addFacilities(Collection<Facility> facilities){
        this.facilities.addAll(facilities);
    }

}
