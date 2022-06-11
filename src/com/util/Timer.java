package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Timer {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");

    private static volatile int fakeTimeInSecond = 0;
    private static final String FAKE_START_TIME = "2022.05.01"; // Set a start fake time

    private static int disasterTime = -100;
    private static boolean isEnd;

    public static String getCurrentTime() {
        return sdf2.format(System.currentTimeMillis());
    }

    // Get the fake time
    public static synchronized String getFakeTime(){
        try{
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(FAKE_START_TIME));
            cal.add(Calendar.DAY_OF_MONTH, fakeTimeInSecond); // 1 second in program equals 1 day in reality time
            return sdf.format(cal.getTime());
        } catch(ParseException e){
            return e.getMessage();
        }
    }

    // Timer Thread will call this method, every one second will increase the fakeTimeInSecond
    public static void updateTime(){
        try{
            Thread.sleep(1000);
            fakeTimeInSecond ++;
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    // Check whether the timer is end
    public static boolean isEnd() {
        return isEnd;
    }

    // End the timer
    public static void setEnd(boolean end) {
        isEnd = end;
    }

    // The disaster time will continue for few seconds
    public static boolean isDisasterTime(){
        return fakeTimeInSecond >= disasterTime && fakeTimeInSecond <= disasterTime + 2;
    }

    // Set the disaster time
    public static void setDisasterTime(int time) {
        disasterTime = time;
    }

    // Get the disaster time
    public static int getDisasterTime() {
        return disasterTime;
    }
}
