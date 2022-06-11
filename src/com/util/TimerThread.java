package com.util;

public class TimerThread extends Thread {

    @Override
    public void run() {
        // Timer Thread will keep updating the fake time
        while (!Timer.isEnd()) {
            Timer.updateTime();
        }
        System.out.println("Timer Thread End");
    }
}
