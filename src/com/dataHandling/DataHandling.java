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
    private static volatile int activityId;

    public DataHandling() {
        this.threadPool = Executors.newCachedThreadPool();
        this.processActivities = new LinkedList<DataEntryWorker>();
        this.activityId = 1;
    }

    public synchronized static void addElementIntoQueue(Activity activity) {
        activity.set_id("A"+activityId);
        activityId++;
        processActivities.add(new DataEntryWorker(activity));
    }

    @Override
    public void run() {
        while (true) {
            if (!processActivities.isEmpty() && !Timer.isEnd()) {
                System.out.println("CHICKYCHACHABOOMBOOM");
                threadPool.submit(processActivities.poll());
            } else if (Timer.isEnd()) {
                break;
            }
        }
        threadPool.isShutdown();
        while(!threadPool.isTerminated()){

        }
    }
}
