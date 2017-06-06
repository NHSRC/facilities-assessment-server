package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Row;
import org.nhsrc.domain.District;
import org.nhsrc.domain.State;

import java.util.Arrays;
import java.util.HashSet;

public class StateCreator {
    public static State create(Row row) {
        FacilityRow facilityRow = new FacilityRow(row);
        State state = new State();
        District district = DistrictCreator.create(facilityRow);
        state.setName(facilityRow.getStateName());
        state.setDistricts(new HashSet<>(Arrays.asList(district)));
        return state;
    }

    public static boolean facilityTypeFilter(Row row) {
        try {
            FacilityRow facilityRow = new FacilityRow(row);
            return !facilityRow.getFacilityType().toLowerCase().equalsIgnoreCase("Sub Centres") && facilityRow.getFacilityId() != null;
        } catch (NullPointerException ne) {
            return false;
        }
    }
}
