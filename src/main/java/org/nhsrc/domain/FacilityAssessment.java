package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@DynamicUpdate
@SelectBeforeUpdate
@Table(name = "facility_assessment")
public class FacilityAssessment extends AbstractScoreEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_id")
    @NotNull
    private Facility facility;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_id")
    @NotNull
    private AssessmentTool assessmentTool;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date startDate;

    @Column(name = "end_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date endDate;

    @Column(name = "series_name", nullable = true)
    private String seriesName;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "facilityAssessment")
    private Set<FacilityAssessmentDevice> facilityAssessmentDevices = new HashSet<>();

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

    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Set<FacilityAssessmentDevice> getFacilityAssessmentDevices() {
        return facilityAssessmentDevices;
    }

    public void setFacilityAssessmentDevices(Set<FacilityAssessmentDevice> facilityAssessmentDevices) {
        this.facilityAssessmentDevices = facilityAssessmentDevices;
    }

    public void incorporateDevice(String deviceId) {
        if (deviceId != null && !deviceId.isEmpty() && facilityAssessmentDevices.stream().noneMatch(facilityAssessmentDevice -> facilityAssessmentDevice.getDeviceId().equals(deviceId))) {
            FacilityAssessmentDevice facilityAssessmentDevice = new FacilityAssessmentDevice();
            facilityAssessmentDevice.setUuid(UUID.randomUUID());
            facilityAssessmentDevice.setDeviceId(deviceId);
            facilityAssessmentDevice.setFacilityAssessment(this);
            facilityAssessmentDevices.add(facilityAssessmentDevice);
        }
    }
}
