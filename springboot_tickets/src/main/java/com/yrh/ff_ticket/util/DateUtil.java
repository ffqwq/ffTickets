package com.yrh.ff_ticket.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date A_YMD_B_HMS(Date A,Date B) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String YMD = sdf1.format(A);

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String HMS = sdf2.format(B);

        SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        return sdf.parse(YMD+" "+HMS);
    }
    public static String getCurrentDateTimeString(String format){
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String DateToString(Date date,String format){
        return new SimpleDateFormat(format).format(date);
    }
}
