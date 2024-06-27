package org.lg.websquare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    public static String appendPercent(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        return "%" + input.trim().toLowerCase() + "%";
    }

    public  static String formatEmpty(String input) {
        if(input == null || input.isEmpty()) return null;
        return input;
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
}
