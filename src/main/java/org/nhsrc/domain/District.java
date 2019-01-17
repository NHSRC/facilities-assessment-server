package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "district")
public class District extends AbstractEntity {
    public District() {
    }

    public District(String name, State state) {
        this.name = name;
        this.state = state;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "district")
    private Set<Facility> facilities = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = State.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @NotNull
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
    public long _getStateId() {
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