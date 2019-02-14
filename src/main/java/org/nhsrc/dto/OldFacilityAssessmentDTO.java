package org.nhsrc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMAT_STRING;

public class OldFacilityAssessmentDTO extends BaseFacilityAssessmentDTO {
    @JsonFormat(pattern = DATE_TIME_FORMAT_STRING)
    private Date startDate;
    @JsonFormat(pattern = DATE_TIME_FORMAT_STRING)
    private Date endDate;

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
}
