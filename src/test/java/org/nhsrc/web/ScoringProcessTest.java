package org.nhsrc.web;

import org.junit.Test;
import org.nhsrc.common.AbstractWebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertEquals;

@Sql({"/setup.sql"})
@Sql({"/data_only.sql"})
public class ScoringProcessTest extends AbstractWebIntegrationTest {
    @Test
    public void score() throws Exception {
//        assertEquals("pong", getResponse("ping", String.class));
    }
}