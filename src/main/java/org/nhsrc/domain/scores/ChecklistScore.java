package org.nhsrc.domain.scores;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.FacilityAssessment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checklist_score")
public class ChecklistScore extends BaseEntity {
    @ManyToOne(targetEntity = AreaOfConcern.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "area_of_concern_id")
    @NotNull
    private AreaOfConcern areaOfConcern;

    @ManyToOne(targetEntity = Standard.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
    @NotNull
    private Standard standard;

    @ManyToOne(targetEntity = Checklist.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

    @ManyToOne(targetEntity = FacilityAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_assessment_id")
    @NotNull
    private FacilityAssessment facilityAssessment;

    @Column(name = "numerator")
    private int numerator;

    @Column(name = "denominator")
    private int denominator;

    @JsonIgnore
    public AreaOfConcern getAreaOfConcern() {
        return areaOfConcern;
    }

    public void setAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areaOfConcern = areaOfConcern;
    }

    @JsonIgnore
    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    @JsonIgnore
    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    @JsonIgnore
    public FacilityAssessment getFacilityAssessment() {
        return facilityAssessment;
    }

    public void setFacilityAssessment(FacilityAssessment facilityAssessment) {
        this.facilityAssessment = facilityAssessment;
    }
}
