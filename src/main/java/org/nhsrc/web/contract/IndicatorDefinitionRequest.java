package org.nhsrc.web.contract;

import java.util.List;

public class IndicatorDefinitionRequest extends BaseRequest {
    private int id;
    private String name;
    private String dataType;
    private int assessmentToolId;
    private List<String> codedValuesJson;
    private String description;
    private String formula;
    private boolean output;
    private int sortOrder;
    private String symbol;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getAssessmentToolId() {
        return assessmentToolId;
    }

    public void setAssessmentToolId(int assessmentToolId) {
        this.assessmentToolId = assessmentToolId;
    }

    public List<String> getCodedValuesJson() {
        return codedValuesJson;
    }

    public void setCodedValuesJson(List<String> codedValuesJson) {
        this.codedValuesJson = codedValuesJson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }
}
