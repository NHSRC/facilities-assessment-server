package org.nhsrc.dto.assessment;

import org.nhsrc.utils.DateUtils;

import java.util.Date;

public class FacilityAssessmentDTO extends BaseFacilityAssessmentDTO {
    private String startDate;
    private String endDate;

    @Override
    public Date getEndDateAsDate() {
        return DateUtils.getUtilDate(endDate, new Date());
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public Date getStartDateAsDate() {
        return DateUtils.getUtilDate(startDate, new Date());
    }
}
