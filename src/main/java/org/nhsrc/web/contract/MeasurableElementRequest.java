package org.nhsrc.web.contract;

public class MeasurableElementRequest extends BaseRequest {
    private String name;
    private String reference;
    private String standardUUID;
    private int standardId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStandardUUID() {
        return standardUUID;
    }

    public void setStandardUUID(String standardUUID) {
        this.standardUUID = standardUUID;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }
}
