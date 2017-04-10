package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@DynamicUpdate
@SelectBeforeUpdate
@Table(name = "facility_assessment")
public class FacilityAssessment extends AbstractScoreEntity {
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "facility_id")
    @NotNull
    private Facility facility;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "assessment_tool_id")
    @NotNull
    private AssessmentTool assessmentTool;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private java.util.Date startDate;

    @Column(name = "end_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private java.util.Date endDate;

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
}
