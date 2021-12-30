package org.nhsrc.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.AssessmentNumberAssignment;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.springframework.data.annotation.Transient;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    public static final String ANONYMOUS_USERS_EMAIL = "anonymous@example.com";
    public static final String BACKGROUND_SERVICE_USER_EMAIL = "backgroundservice@example.com";
    public static final String INTEGRATION_TEST_USER_EMAIL = "intergrationtest@example.com";

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Transient
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @RestResource(exported = false)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "password_changed")
    private boolean passwordChanged;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "users")
    @NotNull
    @JsonIgnore
    private Set<AssessmentNumberAssignment> assessmentNumberAssignments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "users")
    @NotNull
    @JsonIgnore
    private Set<FacilityAssessment> facilityAssessments = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<FacilityLevelAccess> facilityLevelAccessList = new HashSet<>();

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public Set<Role> getRoles() {
        return this.roles == null ? new HashSet<>() : this.roles;
    }

    public List<Integer> getRoleIds() {
        Set<Role> roles = this.getRoles() == null ? new HashSet<>() : this.getRoles();
        return roles.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    // Used by web app to get the privileges for the user
    public Set<Privilege> getPrivileges() {
        Set<Privilege> privileges = new HashSet<>();
        roles.forEach(role -> {
            privileges.addAll(role.getPrivileges());
        });
        return privileges;
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean hasPrivilege(PrivilegeName privilegeName, String programName) {
        Role role = this.getRoles().stream().filter(x -> x.hasPrivilege(privilegeName.getName(), programName)).findFirst().orElse(null);
        return role != null;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public List<Integer> getAccessibleFacilityIds() {
        return facilityLevelAccessList.stream().map(x -> x.getFacility().getId()).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<AssessmentToolMode> getPrivilegedAssessmentToolModes() {
        return getPrivileges().stream().map(Privilege::getAssessmentToolMode).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<State> getPrivilegedStates() {
        return getPrivileges().stream().map(Privilege::getState).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
