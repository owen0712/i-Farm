package com.util;

public class TimerThread extends Thread {

    @Override
    public void run() {
        while (!Timer.isEnd()) {
            Timer.updateTime();
        }
        System.out.println("Timer Thread End");
    }
}
