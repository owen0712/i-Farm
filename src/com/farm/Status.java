package com.farm;

import java.util.concurrent.locks.ReentrantLock;

public class Status {

    private ReentrantLock lock;
    private String action;
    private String plant;

    public Status(){
        this.lock = new ReentrantLock();
        this.action = null;
        this.plant = null;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
