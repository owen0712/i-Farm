package com.dataHandling;

import com.activity.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.util.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataHandling implements Runnable {

    private static ExecutorService threadPool;
    private static volatile int activityId;

    public DataHandling() {
        this.threadPool = Executors.newFixedThreadPool(100);
        this.activityId = 1;
    }

    public synchronized static Future<Boolean> addElementIntoQueue(Activity activity) {
        activity.set_id("A" + activityId);
        activityId++;
        return threadPool.submit(new DataEntryWorker(activity));
    }

    @Override
    public void run() {
        while (true) {
            if (Timer.isEnd()) {
                break;
            }
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            //wait all farmer finish job
        }
        System.out.println("All data handling thread have been terminated");
    }
}