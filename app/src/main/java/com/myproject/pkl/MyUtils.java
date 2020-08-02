package com.myproject.pkl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {
    public static String getCurrentClock(){
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
    public static String getCurrentDate(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
    public static String getCurrentDay(){
        return new SimpleDateFormat("dd").format(new Date());
    }
    public static String getCurrentMonth(){
        return new SimpleDateFormat("MM").format(new Date());
    }
    public static String getCurrentYear(){
        return new SimpleDateFormat("yyyy").format(new Date());
    }
    public static String getTanggalFormat(String storedDate) {
        String date = storedDate;
        String[] splitDay = storedDate.split("/");
        int day = Integer.parseInt(splitDay[0]);
        int month = Integer.parseInt(splitDay[1]);
        int year = Integer.parseInt(splitDay[2]);
        int today = Integer.parseInt(getCurrentDay());
        int thisMonth = Integer.parseInt(getCurrentMonth());
        int thisYear = Integer.parseInt(getCurrentYear());
        if (year == thisYear){
            if (month == thisMonth){
                if (day == today){
                    date = "Today";
                } else if (today - day == 1){
                    date = "Yesterday";
                }
            }
        }
        return date;
    }
}
