package org.nhsrc.domain.metadata;

import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "assessment_metadata")
public class AssessmentMetaData extends AbstractEntity {
    @Column(name = "data_type")
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    @Column(name = "name")
    private String name;

    @Column(name = "validation_regex")
    private String validationRegex;

    @Column(name = "mandatory")
    private boolean mandatory;

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidationRegex() {
        return validationRegex;
    }

    public void setValidationRegex(String validationRegex) {
        this.validationRegex = validationRegex;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}