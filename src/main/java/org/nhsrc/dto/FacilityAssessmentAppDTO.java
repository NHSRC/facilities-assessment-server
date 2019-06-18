package org.nhsrc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

public class FacilityAssessmentAppDTO extends BaseFacilityAssessmentDTO {
    @JsonFormat(pattern = DATE_TIME_FORMAT_STRING)
    private Date startDate;
    @JsonFormat(pattern = DATE_TIME_FORMAT_STRING)
    private Date endDate;

    private UUID assessmentTool;

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

    public UUID getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(UUID assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    @Override
    public String toString() {
        return String.format("%s, {startDate=%s, endDate=%s, assessmentTool=%s}", super.toString(), startDate, endDate, assessmentTool);
    }
}
