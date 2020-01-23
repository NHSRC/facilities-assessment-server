package org.nhsrc.domain;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "excluded_assessment_tool_state")
@BatchSize(size = 25)
public class ExcludedAssessmentToolState extends AbstractEntity {
    @ManyToOne(targetEntity = AssessmentTool.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_tool_id")
    @NotNull
    private AssessmentTool assessmentTool;

    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    public ExcludedAssessmentToolState() {
    }

    public ExcludedAssessmentToolState(AssessmentTool assessmentTool, State state) {
        this.assessmentTool = assessmentTool;
        this.state = state;
        this.setInactive(false);
    }

    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "{" +
                "assessmentTool=" + assessmentTool.getName() +
                ", state=" + state.getName() +
                '}';
    }
}