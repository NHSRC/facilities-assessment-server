package org.nhsrc.utils;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static final String DATE_TIME_FORMAT_STRING = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING);

    public static final ISO8601DateFormat ISO_8601_DATE_FORMAT = new ISO8601DateFormat();

    public static String nextDate(String string) {
        LocalDate localDate = getDate(string);
        return localDate.plus(1, ChronoUnit.DAYS).format(DATE_FORMATTER);
    }

    public static LocalDate getDate(String string) {
        return LocalDate.parse(string, DATE_FORMATTER);
    }
}
