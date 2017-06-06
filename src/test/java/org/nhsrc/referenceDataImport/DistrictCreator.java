package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;

import java.util.Arrays;

public class DistrictCreator {
    public static District create(FacilityRow facilityRow) {
        District district = new District();
        district.setName(facilityRow.getDistrictName());
        Facility facility = FacilityCreator.create(facilityRow);
        district.addFacilities(Arrays.asList(facility));
        return district;
    }
}
