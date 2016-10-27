package org.nhsrc.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checklist")
public class Checklist extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToOne(targetEntity = FacilityType.class, fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "facility_type_id")
    @NotNull
    private FacilityType facilityType;

    @OneToOne
    @JoinColumn(name = "department_id")
    @NotNull
    private Department department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
