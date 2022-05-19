package com.main;

import com.farmer.Farmer;
import com.util.Timer;
import com.util.TimerThread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[]args) throws InterruptedException {
//        Random random = new Random();
//        FarmerSimulator simulator = new FarmerSimulator();
//        Farmer[]farmers=simulator.generateFarmers(random.nextInt(100)+100);
//        for(Farmer farmer:farmers){
//            Thread farmerThread=new Thread(farmer);
//            farmerThread.start();
//        }

        Random random = new Random();
        int randFarmer = random.nextInt(100)+100;
        ExecutorService farmerThreadPool = Executors.newFixedThreadPool(randFarmer);
        FarmerSimulator simulator = new FarmerSimulator();
        Farmer[]farmers=simulator.generateFarmers(randFarmer);
        Timer timer = new Timer();
        timer.setDisasterTime(15);
        TimerThread timerThread = new TimerThread(timer);
        timerThread.start();
        for(Farmer farmer:farmers){
            farmerThreadPool.execute(farmer);
        }
        farmerThreadPool.shutdown();
        while(!farmerThreadPool.isTerminated()){
         //do something
        }
        timer.setEnd(true);
        System.out.println("All thread have been terminated");

        // I did another version using threadPool see see
//        Random random = new Random();
//        int randFarmer = random.nextInt(0)+100;
//        ExecutorService farmerThreadPool = Executors.newFixedThreadPool(randFarmer);
//        Farmer[]farmers=simulator.generateFarmers(randFarmer);
//        for(Farmer farmer:farmers){
//            Thread farmerThread=new Thread(farmer);
//            farmerThreadPool.execute(farmer);
//        }
//        ThreadPool.shutdown();
//        while(true){
//            if(ThreadPool.isTerminated()){
//                Timer.setEnd(true);
//                break;
//            }
//        }

        // How to create TimerThread
//        Timer timer = new Timer();
//        timer.setDisasterTime(15);
//        TimerThread timerThread = new TimerThread(timer);
//        timerThread.start();
//        for(int i = 0; i < 10; i++){ // Just a testing
//            System.out.println(timer.getFakeTime());
//            Thread.sleep(1000);
//        }
    }
}
