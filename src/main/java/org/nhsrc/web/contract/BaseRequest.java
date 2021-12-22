package org.nhsrc.web.contract;

public class BaseRequest {
    private boolean inactive;
    private String uuid;

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean getInactive() {
        return inactive;
    }
}
