package org.nhsrc.domain;

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
    private AssessmentTool assessmentTool;

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

    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }
}