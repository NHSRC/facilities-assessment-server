package org.nhsrc.web.contract;

import java.util.ArrayList;
import java.util.List;

public class AreaOfConcernRequest extends BaseRequest {
    private String name;
    private String reference;
    private List<Integer> checklistIds = new ArrayList<>();

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

    public List<Integer> getChecklistIds() {
        return checklistIds;
    }

    public void setChecklistId(List<Integer> checklistId) {
        this.checklistIds = checklistId;
    }
}
