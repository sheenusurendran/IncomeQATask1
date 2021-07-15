package com.income.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormat {

    public static String basicFormat(String date, String dateFormat) {
        SimpleDateFormat fromFormat = new SimpleDateFormat(dateFormat);
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return toFormat.format(fromFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}