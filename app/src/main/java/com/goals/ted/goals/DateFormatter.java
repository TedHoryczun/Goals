package com.goals.ted.goals;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ted on 9/20/16.
 */
public class DateFormatter {

    public static String convertDateToDateFormat(Calendar date){
        String formattedDate = null;
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, MMM dd yyyy");
        formattedDate = format1.format(date.getTime());
        return formattedDate;

    }
}
