package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timer {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private static volatile int fakeTimeInSecond = 0;
    private static final String FAKE_START_TIME = "2022.05.01.00.00.00"; // Set a start fake time

    private static int disasterTime; //Random generated in second, using setter method to set
    private static boolean isEnd;

    public static String getCurrentTime() {
        return sdf.format(new Date());
    }

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

    public static void updateTime(){
        try{
            Thread.sleep(1000);
            fakeTimeInSecond ++;
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public static boolean isEnd() {
        return isEnd;
    }

    public static void setEnd(boolean end) {
        isEnd = end;
    }

    /*If return true, the thread will throw exception and die
    * In main class, there will be a loop always check whether all threads die already or not
    * If all threads die already, it will start all the threads one more time
    * The timer will restart again, using Timer timer = new Timer()
    * Start time and Disaster time will be reset again, farm may face the disaster again*/
    public static boolean isDisasterTime(){
        return fakeTimeInSecond >= disasterTime;
    }

    public static void setDisasterTime(int time) {
        disasterTime = time;
    }
}
