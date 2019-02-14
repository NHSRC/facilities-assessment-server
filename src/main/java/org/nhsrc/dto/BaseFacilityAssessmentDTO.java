package org.nhsrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class BaseFacilityAssessmentDTO implements Serializable {
    private UUID uuid;
    private UUID facility;
    private int facilityId;
    private String facilityName;
    private UUID assessmentTool;
    private int assessmentToolId;
    private String seriesName;
    private String deviceId;
    private UUID assessmentTypeUUID;
    private int assessmentTypeId;
    private UUID stateUUID;
    private int stateId;
    private UUID districtUUID;
    private int districtId;
    private UUID facilityTypeUUID;
    private int facilityTypeId;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getFacility() {
        return facility;
    }

    public void setFacility(UUID facility) {
        this.facility = facility;
    }

    public UUID getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(UUID assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public UUID getAssessmentTypeUUID() {
        return assessmentTypeUUID;
    }

    public void setAssessmentTypeUUID(UUID assessmentTypeUUID) {
        this.assessmentTypeUUID = assessmentTypeUUID;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public UUID getStateUUID() {
        return stateUUID;
    }

    public void setStateUUID(UUID stateUUID) {
        this.stateUUID = stateUUID;
    }

    public UUID getDistrictUUID() {
        return districtUUID;
    }

    public void setDistrictUUID(UUID districtUUID) {
        this.districtUUID = districtUUID;
    }

    public UUID getFacilityTypeUUID() {
        return facilityTypeUUID;
    }

    public void setFacilityTypeUUID(UUID facilityTypeUUID) {
        this.facilityTypeUUID = facilityTypeUUID;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getAssessmentToolId() {
        return assessmentToolId;
    }

    public void setAssessmentToolId(int assessmentToolId) {
        this.assessmentToolId = assessmentToolId;
    }

    public int getAssessmentTypeId() {
        return assessmentTypeId;
    }

    public void setAssessmentTypeId(int assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getFacilityTypeId() {
        return facilityTypeId;
    }

    public void setFacilityTypeId(int facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }

    @Override
    public String toString() {
        return String.format("{uuid=%s, facility=%s, facilityName=%s, assessmentTool=%s, seriesName='%s', deviceId='%s', assessmentTypeUUID=%s}", uuid, facility, facilityName, assessmentTool, seriesName, deviceId, assessmentTypeUUID);
    }

    public abstract Date getStartDate();

    public abstract void setStartDate(Date startDate);

    public abstract Date getEndDate();

    public abstract void setEndDate(Date endDate);
}