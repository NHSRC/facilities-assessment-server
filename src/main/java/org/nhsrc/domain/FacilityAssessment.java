package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "facility_assessment")
public class FacilityAssessment extends AbstractTransactionalEntity {

    @Column(name = "uuid", updatable = false, unique = true)
    @NotNull
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "facility_id")
    @NotNull
    private Facility facility;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private java.util.Date startDate;

    @Column(name = "end_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private java.util.Date endDate;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "facilityAssessment")
    private Set<ChecklistAssessment> checklistAssessments = new HashSet<>();


    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Set<ChecklistAssessment> getChecklistAssessments() {
        return checklistAssessments;
    }

    public void setChecklistAssessments(Set<ChecklistAssessment> checklistAssessments) {
        this.checklistAssessments = checklistAssessments;
    }
}
