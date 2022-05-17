package com.dataHandling;

import com.activity.Activity;

import java.util.LinkedList;
import java.util.Queue;

import com.util.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataHandling implements Runnable {

    public static Queue<Activity> processActivities;
    private ExecutorService ThreadPool;
    private Timer timer;

    public DataHandling(Timer timer) {
        this.ThreadPool = Executors.newCachedThreadPool();
        this.timer = timer;
    }

    public static void addElementIntoQueue(Activity activity) {
        processActivities.add(activity);
    }

    @Override
    public void run() {
        while (true) {
            if (!processActivities.isEmpty()) {

            } else if (timer.isEnd()) {
                break;
            }
        }
    }
}
