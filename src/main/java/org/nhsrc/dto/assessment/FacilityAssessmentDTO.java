package org.nhsrc.dto.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.nhsrc.dto.assessment.BaseFacilityAssessmentDTO;

import java.util.Date;
import static org.nhsrc.utils.DateUtils.DATE_FORMAT_STRING;

public class FacilityAssessmentDTO extends BaseFacilityAssessmentDTO {
    @JsonFormat(pattern = DATE_FORMAT_STRING)
    private Date startDate;
    @JsonFormat(pattern = DATE_FORMAT_STRING)
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
