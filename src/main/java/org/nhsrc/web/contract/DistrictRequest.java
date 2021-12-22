package org.nhsrc.web.contract;

public class DistrictRequest extends BaseRequest {
    private String stateUUID;
    private int stateId;
    private String name;

    public String getStateUUID() {
        return stateUUID;
    }

    public void setStateUUID(String stateUUID) {
        this.stateUUID = stateUUID;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
