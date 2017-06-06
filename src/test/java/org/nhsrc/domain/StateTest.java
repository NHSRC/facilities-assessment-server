package org.nhsrc.domain;

import org.junit.Test;
import org.nhsrc.builder.DistrictBuilder;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StateTest {
    @Test
    public void addDistrictsTest() throws Exception {
        District district1 = new DistrictBuilder().withName("1").build();
        District district2 = new DistrictBuilder().withName("2").build();
        District district3 = new DistrictBuilder().withName("3").build();
        District district4 = new DistrictBuilder().withName("4").build();
        District district5 = new DistrictBuilder().withName("5").build();
        District district6 = new DistrictBuilder().withName("6").build();
        District district7 = new DistrictBuilder().withName("6").build();
        State existingState = new State();
        existingState.setDistricts(new HashSet<>(Arrays.asList(district1, district2, district3)));
        existingState.addDistricts(
                Arrays.asList(district1, district2, district3, district4, district5, district6, district7));
        assertEquals(6, existingState.getDistricts().size());
        assertTrue(existingState.getDistricts().stream().anyMatch(district -> Arrays.asList(district1, district2, district3, district4, district5, district6).contains(district)));
    }
}