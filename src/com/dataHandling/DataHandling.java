package com.dataHandling;

import com.activity.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.util.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataHandling implements Runnable {

    public static Queue<DataEntryWorker> processActivities;
    private ExecutorService threadPool;
    private Timer timer;
    private static int activityId;

    public DataHandling(Timer timer) {
        this.threadPool = Executors.newCachedThreadPool();
        this.timer = timer;
        this.processActivities = new LinkedList<DataEntryWorker>();
        this.activityId = 1;
    }

    public static void addElementIntoQueue(Activity activity) {
        activity.set_id("A"+activityId);
        processActivities.add(new DataEntryWorker(activity));
        activityId++;
    }

    @Override
    public void run() {
        while (true) {
            if (!processActivities.isEmpty()) {
                threadPool.submit(processActivities.poll());
            } else if (timer.isEnd()) {
                break;
            }
        }
    }
}
