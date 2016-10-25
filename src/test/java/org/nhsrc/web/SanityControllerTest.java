package org.nhsrc.web;


import org.junit.Test;
import org.nhsrc.common.AbstractWebIntegrationTest;

import static org.junit.Assert.assertEquals;

public class SanityControllerTest extends AbstractWebIntegrationTest {

    @Test
    public void pingTest() throws Exception {
        assertEquals("pong", getResponse("ping", String.class));
    }
}