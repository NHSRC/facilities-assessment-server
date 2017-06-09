package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;

import java.util.UUID;

public class FacilityCreator {
    public static Facility create(FacilityRow facilityRow) {
        Facility facility = new Facility();
        FacilityType facilityType = new FacilityType();
        facilityType.setName(facilityRow.getFacilityType());
        facility.setName(facilityRow.getFacilityName());
        facility.setFacilityType(facilityType);
        return facility;
    }

    public static String toSQL(District district, Facility facility) {
        FacilityType facilityType = facility.getFacilityType();
        String facilityTypeInsertSQL = String
                .format("INSERT INTO facility_type (name) values('%s') on conflict (name) DO NOTHING;\n", facilityType.getName());
        facility.setUuid(UUID.randomUUID());
        return facilityTypeInsertSQL.concat(String.format("INSERT INTO facility (name, uuid, district_id, facility_type_id) values ('%s', '%s'::UUID, (SELECT id FROM district WHERE name='%s'), (SELECT id from facility_type where name='%s')) on conflict (name, facility_type_id) do nothing;\n", facility.getName(), facility.getUuid().toString(), district.getName(), facilityType.getName()));
    }
}
