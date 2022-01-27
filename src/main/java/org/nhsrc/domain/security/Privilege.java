package org.nhsrc.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.nhsrc.domain.AssessmentToolMode;
import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.State;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity {
    public static final PrivilegeName USER = new PrivilegeName("User");
    public static final PrivilegeName ASSESSMENT_READ = new PrivilegeName("Assessment_Read");
    public static final PrivilegeName ASSESSMENT_WRITE = new PrivilegeName("Assessment_Write");
    public static final PrivilegeName USERS_WRITE = new PrivilegeName("Users_Write");
    public static final PrivilegeName FACILITY_WRITE = new PrivilegeName("Facility_Write");
    public static final PrivilegeName ALL_STATES_DASHBOARD = new PrivilegeName(State.ALL_STATES.getName());

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = State.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @RestResource(exported = false)
    private State state;

    @ManyToOne(targetEntity = AssessmentToolMode.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_mode_id")
    @RestResource(exported = false)
    private AssessmentToolMode assessmentToolMode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public State getState() {
        return state;
    }

    @JsonProperty("stateId")
    public Integer _getStateId() {
        return state == null ? null : state.getId();
    }

    @JsonProperty("assessmentToolModeId")
    public Integer _getAssessmentToolModeId() {
        return assessmentToolMode == null ? null : assessmentToolMode.getId();
    }

    public void setState(State state) {
        this.state = state;
    }

    @JsonIgnore
    public AssessmentToolMode getAssessmentToolMode() {
        return assessmentToolMode;
    }

    public void setAssessmentToolMode(AssessmentToolMode assessmentToolMode) {
        this.assessmentToolMode = assessmentToolMode;
    }

    public boolean satisfies(String privilegeName, String programName) {
        return this.getName().equals(privilegeName) && (this.getAssessmentToolMode() == null || this.getAssessmentToolMode().getName().equals(programName));
    }

    public static List<GrantedAuthority> createAuthorities(String ... privileges) {
        return Arrays.stream(privileges).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("{name='%s', state=%s, assessmentToolMode=%s}", name, state, assessmentToolMode);
    }
}
