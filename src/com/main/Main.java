package com.main;

import com.dataHandling.DataHandling;
import com.farmer.Farmer;
import com.util.DAO;
import com.util.Timer;
import com.util.TimerThread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[]args) throws InterruptedException {
        DAO dao = new DAO();
        dao.deleteAllActivityRecord();
        Thread dataHandlingThread = new Thread(new DataHandling());
        dataHandlingThread.start();
        Random random = new Random();
//        int randomFarmerNumber = random.nextInt(100);
        int randomFarmerNumber = 100;
        ExecutorService farmerThreadPool = Executors.newFixedThreadPool(randomFarmerNumber);
        FarmerSimulator simulator = new FarmerSimulator();
        Farmer[]farmers=simulator.generateFarmers(randomFarmerNumber);
        Timer timer = new Timer();
        timer.setDisasterTime(15);
        TimerThread timerThread = new TimerThread(timer);
        timerThread.start();
        for(Farmer farmer:farmers){
            farmerThreadPool.execute(farmer);
        }
        farmerThreadPool.shutdown();
        while(!farmerThreadPool.isTerminated()){
         //wait all farmer finish job
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
