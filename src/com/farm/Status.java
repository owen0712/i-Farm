package com.farm;

import com.plant.Plant;

import java.util.concurrent.locks.ReentrantLock;

public class Status {

    private ReentrantLock lock;
    private String action;
    private Plant plant;

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

    public Plant getPlant() { return plant; }

    public void setPlant(Plant plant) { this.plant = plant; }
}
