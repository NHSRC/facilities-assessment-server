package org.nhsrc;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMATTER;

public class DateUtilTest {
    @Test
    public void name() throws Exception {
        System.out.println(LocalDateTime.MIN.format(DATE_TIME_FORMATTER));
    }
}
