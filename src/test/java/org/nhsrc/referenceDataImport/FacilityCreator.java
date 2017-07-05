package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;

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

    public static String toSQL(District district, Facility facility, State state) {
        FacilityType facilityType = facility.getFacilityType();
        String facilityTypeInsertSQL = String
                .format("INSERT INTO facility_type (name) values('%s') on conflict (name) DO NOTHING;\n", facilityType.getName());
        facility.setUuid(UUID.randomUUID());
        return facilityTypeInsertSQL.concat(String.format("INSERT INTO facility (name, uuid, district_id, facility_type_id) values ('%s', '%s'::UUID, (SELECT district.id FROM district, state WHERE district.name='%s' and state.name='%s' and district.state_id = state.id), (SELECT id from facility_type where name='%s')) on conflict (district_id, facility_type_id, name) do nothing;\n", facility.getName(), facility.getUuid().toString(), district.getName(), state.getName(), facilityType.getName()));
    }
}
