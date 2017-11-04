package org.nhsrc.web.contract;

public class DistrictRequest {
    private String uuid;
    private Boolean inactive;
    private String name;
    private String stateUUID;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateUUID() {
        return stateUUID;
    }

    public void setStateUUID(String stateUUID) {
        this.stateUUID = stateUUID;
    }
}