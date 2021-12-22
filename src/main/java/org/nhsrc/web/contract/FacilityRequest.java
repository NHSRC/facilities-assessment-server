package org.nhsrc.web.contract;

import java.util.ArrayList;
import java.util.List;

public class FacilityRequest extends BaseRequest {
    private String name;
    private String facilityTypeUUID;
    private int facilityTypeId;
    private String districtUUID;
    private int districtId;
    private List<Integer> userIdsWithAccess = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacilityTypeUUID() {
        return facilityTypeUUID;
    }

    public void setFacilityTypeUUID(String facilityTypeUUID) {
        this.facilityTypeUUID = facilityTypeUUID;
    }

    public String getDistrictUUID() {
        return districtUUID;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public void setDistrictUUID(String districtUUID) {
        this.districtUUID = districtUUID;
    }

    public int getFacilityTypeId() {
        return facilityTypeId;
    }

    public void setFacilityTypeId(int facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }

    public List<Integer> getUserIdsWithAccess() {
        return userIdsWithAccess;
    }

    public void setUserIdsWithAccess(List<Integer> userIdsWithAccess) {
        this.userIdsWithAccess = userIdsWithAccess;
    }
}
