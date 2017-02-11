package org.nhsrc.web;

import org.junit.Test;
import org.nhsrc.common.AbstractWebIntegrationTest;

import java.util.LinkedHashMap;
import java.util.Set;

public class RegionDataControllerTest extends AbstractWebIntegrationTest {
    @Test
    public void testStuff() throws Exception {
        Set states = (Set) this.getResponse("states?lastSyncedDate=12-02-1990 01:01:47", Set.class);
        for (Object state : states) {
            LinkedHashMap state1 = (LinkedHashMap) state;
            System.out.println(state1.get("name"));
        }
    }
}
