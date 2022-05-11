package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {

    private long startTime;
    private long disasterTime; //Random generated in second, using setter method to set

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    public long getCurrentTimeInSecond(){
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    /*If return true, the thread will throw exception and die
    * In main class, there will be a loop always check whether all threads die already or not
    * If all threads die already, it will start all the threads one more time
    * The timer will restart again, using Timer timer = new Timer()
    * Start time and Disaster time will be reset again, farm may face the disaster again*/
    public boolean isDisasterTime(){
        long differenceInSecond = (System.currentTimeMillis() - startTime) / 1000;
        return differenceInSecond >= disasterTime;
    }

    public void setDisasterTime(long disasterTime) {
        this.disasterTime = disasterTime;
    }
}
