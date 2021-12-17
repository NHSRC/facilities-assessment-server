package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "indicator_definition")
public class IndicatorDefinition extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "data_type")
    @Enumerated(EnumType.STRING)
    private IndicatorDataType dataType;

    @NotNull
    @ManyToOne(targetEntity = AssessmentTool.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_id")
    @RestResource(exported = false)
    private AssessmentTool assessmentTool;

    @Column(name = "formula")
    private String formula;

    @Column(name = "output")
    private boolean output;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "sort_order")
    private int sortOrder;

    @Column(name = "coded_values")
    private String codedValues;

    @Column(name = "description")
    private String description;

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

    @JsonIgnore
    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    @JsonProperty("assessmentToolId")
    public Integer _getAssessmentToolId() {
        return this.assessmentTool.getId();
    }

    public void setAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isOutput() {
        return output;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCodedValues() {
        return codedValues;
    }

    public void setCodedValues(String codedValues) {
        this.codedValues = codedValues;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
