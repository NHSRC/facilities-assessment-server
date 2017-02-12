package org.nhsrc.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String DATE_TIME_FORMAT_STRING = "dd-MM-yyyy HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);

    public static final String MIN_DATE_STRING = LocalDateTime.MIN.format(DATE_TIME_FORMATTER);

}
