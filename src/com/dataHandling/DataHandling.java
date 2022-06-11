package com.dataHandling;

import com.activity.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.util.DAO;
import com.util.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class DataHandling implements Runnable {

    private ExecutorService threadPool_1;
    private ExecutorService threadPool_2;
    private static AtomicInteger activityId;
    private DAO dao_1;
    private DAO dao_2;

    public DataHandling() {
        this.threadPool_1 = Executors.newCachedThreadPool();
        this.threadPool_2 = Executors.newCachedThreadPool();
        this.activityId = new AtomicInteger(1);
        this.dao_1 = new DAO();
        this.dao_2 = new DAO();
    }

    public Future<Boolean> addElementIntoQueue(Activity activity) {
        activity.set_id("A" + activityId);
        activityId.getAndIncrement();
        if(Integer.parseInt(activity.get_id().substring(1,2))%2==0)
            return threadPool_1.submit(new DataEntryWorker(activity, dao_1));
        else
            return threadPool_2.submit(new DataEntryWorker(activity, dao_2));
    }

    @Override
    public void run() {
        while (true) {
            if (Timer.isEnd()) {
                break;
            }
        }
        threadPool_1.shutdown();
        threadPool_2.shutdown();
        System.out.println("All data handling thread have been terminated");
    }
}