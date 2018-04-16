package org.nhsrc.utils;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String DATE_TIME_FORMAT_STRING = "dd-MM-yyyy HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);

    public static final ISO8601DateFormat ISO_8601_DATE_FORMAT = new ISO8601DateFormat();
}
