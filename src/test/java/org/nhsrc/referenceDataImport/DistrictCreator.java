package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.State;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class DistrictCreator {
    public static District create(FacilityRow facilityRow) {
        District district = new District();
        district.setName(facilityRow.getDistrictName());
        Facility facility = FacilityCreator.create(facilityRow);
        district.addFacilities(Arrays.asList(facility));
        return district;
    }

    public static String toSQL(State state, District district) {
        district.setUuid(UUID.randomUUID());
        return String.format("INSERT INTO district (name, uuid, state_id) values ('%s', '%s'::UUID, (SELECT id FROM state WHERE name='%s')) ON CONFLICT (state_id, name) DO NOTHING;\n", district.getName(), district.getUuid().toString(), state.getName())
                .concat(district
                        .getFacilities()
                        .stream()
                        .map(facility -> FacilityCreator.toSQL(district, facility, state))
                        .collect(Collectors.joining("\n")));
    }
}
