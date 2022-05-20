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

    public static Queue<DataEntryWorker> processActivities;
    private static ExecutorService threadPool;
    private static volatile int activityId;

    public DataHandling() {
        this.threadPool = Executors.newFixedThreadPool(100);
        this.processActivities = new LinkedList<DataEntryWorker>();
        this.activityId = 1;
    }

    public synchronized static Future<Boolean> addElementIntoQueue(Activity activity) {
        activity.set_id("A" + activityId);
        activityId++;
//        processActivities.add(new DataEntryWorker(activity));
        return threadPool.submit(new DataEntryWorker(activity));
    }

    @Override
    public void run() {
        while (true) {
//            if (!processActivities.isEmpty()) {
//                threadPool.submit(processActivities.poll());
//            } else if (Timer.isEnd()&&processActivities.isEmpty()) {
//                break;
//            }
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
