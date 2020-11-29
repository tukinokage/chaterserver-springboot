package com.example.chaterserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getDateTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = formatter.format(date);

        return time;
    }

    public static String getDateTimeNum(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyMMddHHmmss");

        String time = formatter.format(date);

        return time;
    }
}
