package com.util;

public class TimerThread extends Thread{

    Timer timer;

    public TimerThread(Timer timer){
        this.timer = timer;
    }

    @Override
    public void run() {
        while(true){
            timer.updateTime();
            if(timer.isDisasterTime()){
                break;
            }
            if(Timer.isEnd()){
                break;
            }
        }
    }
}
