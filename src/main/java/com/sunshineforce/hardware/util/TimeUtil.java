package com.sunshineforce.hardware.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String dataSecondString = "yyyy-MM-dd HH:mm:ss";
    public static String dataMonthStringNotSign = "yyyyMM";
    public static String dataSecondStringLine = "yyyy/MM/dd HH:mm:ss";

    public static String getTimeString(Date date, String dataFormatString){
        return new SimpleDateFormat(dataFormatString).format(date);
    }

    public static Date getTimeDate(String dateString, String dataFormatString) throws Exception{
        return new SimpleDateFormat(dataFormatString).parse(dateString);
    }

    public static Calendar getNextMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        return cal;
    }
}