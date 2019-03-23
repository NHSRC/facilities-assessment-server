package org.nhsrc.domain.security;

import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.State;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity {
    public static final String USER_WITHOUT_PREFIX = "User";
    public static final String USER = "ROLE_User";
    public static final String ASSESSMENT_READ = "ROLE_Assessment_Read";
    public static final String ASSESSMENT_WRITE = "ROLE_Assessment_Write";

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = State.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @NotNull
    private State state;

    @ManyToOne(targetEntity = AssessmentToolMode.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_mode_id")
    @NotNull
    private AssessmentToolMode assessmentToolMode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public AssessmentToolMode getAssessmentToolMode() {
        return assessmentToolMode;
    }

    public void setAssessmentToolMode(AssessmentToolMode assessmentToolMode) {
        this.assessmentToolMode = assessmentToolMode;
    }

    public boolean satisfies(String privilegeName, String programName) {
        return this.getName().equals(privilegeName) && (this.getAssessmentToolMode() == null || this.getAssessmentToolMode().getName().equals(programName));
    }
}