package org.nhsrc.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "assessment_tool")
public class AssessmentTool extends AbstractEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "assessmentTool")
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
