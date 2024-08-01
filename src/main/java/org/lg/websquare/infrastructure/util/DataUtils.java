package org.lg.websquare.infrastructure.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataUtils {

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
        String dateFormat = "yyyy-MM-dd";

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("Failed to convert the date.");
            return null;
        }
    }

    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            inputFormatter.parse(dateString);
            return inputFormatter.parse(dateString);
        } catch (Exception e) {
           e.getStackTrace();
        }

        DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(dateString, inputFormatter2);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");

        return outputFormatter.parse(outputFormatter.format(date));
    }
}