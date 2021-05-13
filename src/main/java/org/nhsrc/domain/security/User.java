package org.nhsrc.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.BaseEntity;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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
    private Set<Role> roles = new HashSet<>();

    @Column(name = "password_changed")
    private boolean passwordChanged;

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
}
