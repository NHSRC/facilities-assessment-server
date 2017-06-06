package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;

public class FacilityCreator {
    public static Facility create(FacilityRow facilityRow) {
        Facility facility = new Facility();
        FacilityType facilityType = new FacilityType();
        facilityType.setName(facilityRow.getFacilityType());
        facility.setName(facilityRow.getFacilityName());
        facility.setFacilityType(facilityType);
        return facility;
    }
}
