package org.nhsrc.web;

import org.junit.Test;
import org.nhsrc.common.AbstractWebIntegrationTest;
import org.nhsrc.domain.State;

import java.util.Set;

public class RegionDataControllerTest extends AbstractWebIntegrationTest {
    @Test
    public void testStuff() throws Exception {
        Set<State> states = (Set<State>) getResponse("states", Set.class);
        for (State state : states) {
            System.out.println(state.getName());
        }
    }
}
