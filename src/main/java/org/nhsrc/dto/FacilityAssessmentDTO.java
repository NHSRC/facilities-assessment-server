package org.nhsrc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

public class FacilityAssessmentDTO implements Serializable {
    private UUID uuid;

    private UUID facility;

    private UUID assessmentTool;

    @JsonFormat(pattern = DATE_TIME_FORMAT_STRING)
    private Date startDate;

    @JsonFormat(pattern = DATE_TIME_FORMAT_STRING)
    private Date endDate;

    private String seriesName;

    private String deviceId;

    private UUID assessmentTypeUUID;

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
}
