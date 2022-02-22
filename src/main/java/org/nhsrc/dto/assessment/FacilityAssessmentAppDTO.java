package org.nhsrc.dto.assessment;

import org.nhsrc.utils.DateUtils;

import java.util.Date;
import java.util.UUID;

public class FacilityAssessmentAppDTO extends BaseFacilityAssessmentDTO {
    private String startDate;
    private String endDate;

    private UUID assessmentTool;

    @Override
    public Date getStartDateAsDate() {
        return DateUtils.getUtilDateTime(startDate, new Date());
    }

    @Override
    public Date getEndDateAsDate() {
        return DateUtils.getUtilDateTime(endDate, new Date());
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
