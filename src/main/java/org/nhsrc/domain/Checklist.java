package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "checklist")
public class Checklist extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(targetEntity = AssessmentType.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "assessment_type_id")
    @NotNull
    private AssessmentType assessmentType;

    @OneToOne
    @JoinColumn(name = "department_id")
    @NotNull
    private Department department;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "checklist_area_of_concern", inverseJoinColumns = @JoinColumn(name = "area_of_concern_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "id"))
    private Set<AreaOfConcern> areasOfConcern = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<AreaOfConcern> getAreasOfConcern() {
        return areasOfConcern;
    }

    public void setAreasOfConcern(Set<AreaOfConcern> areasOfConcern) {
        this.areasOfConcern = areasOfConcern;
    }
}
