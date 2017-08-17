package org.nhsrc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "assessment_tool_mode")
public class AssessmentToolMode extends AbstractTransactionalEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

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