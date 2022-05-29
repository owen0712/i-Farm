package com.dataHandling;

import com.activity.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.util.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class DataHandling implements Runnable {

    private ExecutorService threadPool;

    public DataHandling() {
        this.threadPool = Executors.newFixedThreadPool(100);
    }

    public synchronized Future<Boolean> addElementIntoQueue(Activity activity) {
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