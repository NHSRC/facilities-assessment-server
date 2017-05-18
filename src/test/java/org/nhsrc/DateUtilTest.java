package org.nhsrc;

import org.junit.Test;
import org.nhsrc.domain.Standard;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.nhsrc.utils.DateUtils.DATE_TIME_FORMATTER;

public class DateUtilTest {
    @Test
    public void name() throws Exception {
        Standard standard = new Standard();
        standard.setReference("a");

        ArrayList<Standard> standards = new ArrayList<>();
        standards.add(standard);

        Standard foo = standards.stream().filter(std -> std.getReference().equals("a")).findAny().orElse(null);
        Standard bar = standards.stream().filter(std -> std.getReference().equals("b")).findAny().orElse(null);

        System.out.println(LocalDateTime.MIN.format(DATE_TIME_FORMATTER));
    }
}
