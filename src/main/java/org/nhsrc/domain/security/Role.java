package org.nhsrc.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.BaseEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "role_privilege", inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @RestResource(exported = false)
    private Set<Privilege> privileges = new HashSet<>();

    @JsonIgnore
    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Used by the web app
    public List<Integer> getPrivilegeIds() {
        return this.getPrivileges().stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public void removePrivilege(Privilege privilege) {
        this.privileges.remove(privilege);
    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
    }

    public boolean hasPrivilege(String privilegeName, String programName) {
        Privilege matchingPrivilege = this.getPrivileges().stream().filter(privilege -> privilege.satisfies(privilegeName, programName)).findFirst().orElse(null);
        return matchingPrivilege != null;
    }

    @Override
    public String toString() {
        return String.format("name='%s'", name);
    }
}
