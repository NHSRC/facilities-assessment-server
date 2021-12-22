package org.nhsrc.web.contract;

public class StandardRequest extends BaseRequest {
    private String name;
    private String reference;
    private String areaOfConcernUUID;
    private int areaOfConcernId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaOfConcernUUID() {
        return areaOfConcernUUID;
    }

    public void setAreaOfConcernUUID(String areaOfConcernUUID) {
        this.areaOfConcernUUID = areaOfConcernUUID;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getAreaOfConcernId() {
        return areaOfConcernId;
    }

    public void setAreaOfConcernId(int areaOfConcernId) {
        this.areaOfConcernId = areaOfConcernId;
    }
}
