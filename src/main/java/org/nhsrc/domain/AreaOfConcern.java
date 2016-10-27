package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "area_of_concern")
public class AreaOfConcern extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "checklist_area_of_concern", joinColumns = @JoinColumn(name = "area_of_concern_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "id"))
    @NotNull
    private Set<Checklist> checklists = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(Set<Checklist> checklists) {
        this.checklists = checklists;
    }
}
