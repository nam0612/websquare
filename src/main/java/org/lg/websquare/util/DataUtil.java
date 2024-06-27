package org.lg.websquare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DataUtil {

    public static String appendPercent(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        return "%" + input.trim().toLowerCase() + "%";
    }

    public static Date stringToDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        String dateFormat = "yyyyMMdd";

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("Failed to convert the date.");
            return null;
        }
    }

    public static Date stringToDate2(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        String dateFormat = "yyyy-MM-dd";

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("Failed to convert the date.");
            return null;
        }
    }

    public static String safeToString(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        return string;
    }
}
