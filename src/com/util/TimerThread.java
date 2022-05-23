package com.util;

public class TimerThread extends Thread {

    Timer timer;

    public TimerThread(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        while (!Timer.isEnd()) {
            timer.updateTime();
//            if (timer.isDisasterTime()) {
//                break;
//            }
        }
        System.out.println("Timer Thread End");
    }
}
