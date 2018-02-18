package org.nhsrc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "indicator_definition")
public class IndicatorDefinition extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "data_type")
    private IndicatorDataType dataType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IndicatorDataType getDataType() {
        return dataType;
    }

    public void setDataType(IndicatorDataType dataType) {
        this.dataType = dataType;
    }
}