package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "assessment_tool_mode")
public class AssessmentToolMode extends AbstractEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @JsonIgnore
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssessmentToolMode(String name) {
        this.name = name;
    }

    public AssessmentToolMode() {
    }
}