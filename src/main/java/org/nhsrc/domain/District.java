package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "district")
public class District extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = State.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
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

    public void setState(State state) {
        this.state = state;
    }
}