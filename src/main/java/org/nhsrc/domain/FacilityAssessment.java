package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.nhsrc.config.SecurityConfiguration;
import org.nhsrc.domain.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@DynamicUpdate
@SelectBeforeUpdate
@Table(name = "facility_assessment")
public class FacilityAssessment extends AbstractScoreEntity {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy");
    private static Logger logger = LoggerFactory.getLogger(FacilityAssessment.class);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Column(name = "facility_name")
    private String facilityName;

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

    @Column(name = "assessment_code")
    private String assessmentCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_type_id")
    @NotNull
    private AssessmentType assessmentType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "facilityAssessment")
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

    @JsonProperty("assessmentToolId")
    public long _getAssessmentToolId() {
        return this.assessmentTool.getId();
    }

    @JsonProperty("FacilityId")
    public long _getFacilityId() {
        return this.facility.getId();
    }

    @JsonProperty("assessmentTypeId")
    public long _getAssessmentTypeId() {
        return this.assessmentType.getId();
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

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
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

    public void setupCode() {
        //N-Ex-KL-DH-412636-01012018
        try {
            Facility facility = this.getFacility();
            if (facility == null) return;

            AssessmentToolMode assessmentToolMode = this.assessmentTool.getAssessmentToolMode();
            AssessmentType assessmentType = this.getAssessmentType();
            District district = facility.getDistrict();
            State state = district.getState();
            String assessmentToolModeShortName = assessmentToolMode.getShortName();
            String stateShortName = state.getShortName();
            String hmisCode = facility.getHmisCode();
            String assessmentTypeShortName = assessmentType.getShortName();
            this.assessmentCode = String.format("%s-%s-%s-%s-%s", assessmentToolModeShortName, assessmentTypeShortName, stateShortName, hmisCode, dateFormatter.format(new Date()));
        } catch (NullPointerException ignored) {
            logger.error("Error happened in setting up code", ignored);
        }
    }

    @JsonIgnore
    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void updateEndDate(Date endDate) {
        if (new LocalDateTime(this.endDate).isBefore(new LocalDateTime(endDate))) {
            this.endDate = endDate;
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
