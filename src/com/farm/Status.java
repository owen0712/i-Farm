package com.farm;

import java.util.concurrent.locks.ReentrantLock;

public class Status {

    private ReentrantLock lock;
    private String status;

    public Status(){
        this.lock = new ReentrantLock();
        this.status = null;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
