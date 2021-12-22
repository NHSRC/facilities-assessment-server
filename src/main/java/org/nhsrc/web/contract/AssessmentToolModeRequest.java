package org.nhsrc.web.contract;

public class AssessmentToolModeRequest extends BaseRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
