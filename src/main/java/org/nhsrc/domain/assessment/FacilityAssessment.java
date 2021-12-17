package org.nhsrc.domain.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDateTime;
import org.nhsrc.domain.*;
import org.nhsrc.domain.metadata.AssessmentMetaData;
import org.nhsrc.domain.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.nhsrc.utils.DateUtils.DATE_FORMAT_STRING;

@Entity
@Table(name = "facility_assessment")
public class FacilityAssessment extends AbstractScoreEntity {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy");
    private static final Logger logger = LoggerFactory.getLogger(FacilityAssessment.class);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_id")
    @NotNull
    @RestResource(exported = false)
    private AssessmentTool assessmentTool;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = DATE_FORMAT_STRING)
    private java.util.Date startDate;

    @Column(name = "end_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = DATE_FORMAT_STRING)
    private java.util.Date endDate;

    @Column(name = "series_name", nullable = true)
    private String seriesName;

    @Column(name = "assessment_code")
    private String assessmentCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_type_id")
    @NotNull
    @RestResource(exported = false)
    private AssessmentType assessmentType;

    @Column(name = "inactive")
    private boolean inactive;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "facilityAssessment")
    @RestResource(exported = false)
    private Set<FacilityAssessmentDevice> facilityAssessmentDevices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "facilityAssessment")
    @RestResource(exported = false)
    private Set<AssessmentCustomInfo> customInfos = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_number_assignment_id")
    @RestResource(exported = false)
    private AssessmentNumberAssignment assessmentNumberAssignment;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "facility_assessment_users", joinColumns = @JoinColumn(name = "facility_assessment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    @RestResource(exported = false)
    private Set<User> users = new HashSet<>();

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
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
    public Integer _getAssessmentToolId() {
        return this.assessmentTool.getId();
    }

    @JsonProperty("assessmentTypeId")
    public Integer _getAssessmentTypeId() {
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

    public void incorporateDevice(String deviceId) {
        if (deviceId != null && !deviceId.isEmpty() && facilityAssessmentDevices.stream().noneMatch(facilityAssessmentDevice -> facilityAssessmentDevice.getDeviceId().equals(deviceId))) {
            FacilityAssessmentDevice facilityAssessmentDevice = new FacilityAssessmentDevice();
            facilityAssessmentDevice.setDeviceId(deviceId);
            facilityAssessmentDevice.setFacilityAssessment(this);
            facilityAssessmentDevices.add(facilityAssessmentDevice);
        }
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_type_id")
    private FacilityType facilityType;

    @Column(name = "facility_name")
    private String facilityName;

    @JsonIgnore
    public Facility getFacility() {
        return facility;
    }

    @JsonProperty("facilityId")
    public Integer _getFacilityId() {
        return this.facility == null ? null : this.facility.getId();
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @JsonIgnore
    public State getState() {
        return this.state;
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return this.state == null ? null : this.state.getId();
    }

    public void setState(State state) {
        this.state = state;
    }

    @JsonIgnore
    public District getDistrict() {
        return this.district;
    }

    @JsonProperty("districtId")
    public Integer _getDistrictId() {
        return this.district == null ? null : this.district.getId();
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @JsonIgnore
    public FacilityType getFacilityType() {
        if (this.facilityType != null) return this.facilityType;
        if (this.facility != null) return this.facility.getFacilityType();
        return null;
    }

    @JsonProperty("facilityTypeId")
    public Integer _getFacilityTypeId() {
        FacilityType facilityType = this.getFacilityType();
        return facilityType == null ? null : facilityType.getId();
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
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
            String hmisCode = facility.getRegistryUniqueId();
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

    public boolean getInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getEffectiveFacilityName() {
        return facility == null ? this.getFacilityName() : this.getFacility().getName();
    }

    public void addCustomInfo(AssessmentMetaData assessmentMetaData, String value, String deviceId) {
        AssessmentCustomInfo savedCustomInfo = this.customInfos.stream().filter(e -> e.isSameKey(assessmentMetaData, deviceId)).findFirst().orElse(null);
        if (savedCustomInfo == null) {
            AssessmentCustomInfo customInfo = new AssessmentCustomInfo();
            customInfo.setAssessmentMetaData(assessmentMetaData);
            customInfo.setValueString(value);
            customInfo.setDeviceId(deviceId);
            customInfo.setFacilityAssessment(this);
            this.customInfos.add(customInfo);
        } else
            savedCustomInfo.setValueString(value);
    }

    public Set<AssessmentCustomInfo> getCustomInfos() {
        return customInfos;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    public String getAssessmentNumber() {
        return assessmentNumberAssignment == null ? null : assessmentNumberAssignment.getAssessmentNumber();
    }
}
