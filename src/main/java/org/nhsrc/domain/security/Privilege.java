package org.nhsrc.domain.security;

import org.nhsrc.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity {
    public static final String USER_WITHOUT_PREFIX = "User";
    public static final String USER = "ROLE_User";
    public static final String ASSESSMENT_READ = "ROLE_Assessment_Read";
    public static final String ASSESSMENT_WRITE = "ROLE_Assessment_Write";

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}