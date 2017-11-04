package org.nhsrc.web.contract;

public class StandardRequest {
    private String uuid;
    private Boolean inactive;
    private String name;
    private String reference;
    private String areaOfConcernUUID;

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
}