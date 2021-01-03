package org.nhsrc.dto.assessment;

public class AssessmentCustomInfoDTO {
    private String uuid;
    //for scenario where user has not downloaded AMD yet
    private String name;
    private String valueString;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}