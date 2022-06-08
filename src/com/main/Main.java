package com.main;

import com.dataHandling.DataHandling;
import com.dataVisualization.DataVisualizer;
import com.farm.Farm;
import com.farmer.Farmer;
import com.util.DAO;
import com.util.IFarmLogger;
import com.util.Timer;
import com.util.TimerThread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static Farm[] farms;

    public static void main(String[] args) throws InterruptedException {
        sequential();
    }

    public static void sequential() {
        //Delete previous activity record
        DAO dao = new DAO();
        dao.deleteAllActivityRecord();

        //Getting farms from db
        farms = dao.getFarmData();

        //Start data handling thread
        Thread dataHandlingThread = new Thread(new DataHandling());
        dataHandlingThread.start();

        //Generate Farmer
        Random random = new Random();
//        int randomFarmerNumber = random.nextInt(100);
        int randomFarmerNumber = 100;
        ExecutorService farmerThreadPool = Executors.newFixedThreadPool(randomFarmerNumber);
        FarmerSimulator simulator = new FarmerSimulator();
        Farmer[] farmers = simulator.generateFarmers(randomFarmerNumber);

        //Setup timer and timer thread
        TimerThread timerThread = new TimerThread();
        timerThread.start();
        String startTime = Timer.getCurrentTime();

        //Start farmer thread
        for (Farmer farmer : farmers) {
            Thread farmerThread = new Thread(farmer);
            farmerThread.run();
        }
        Timer.setEnd(true);

        //Record End Time
        String endTime = Timer.getCurrentTime();
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("All thread have been terminated");

        try{
            timerThread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        //Idk should i declare at timerThread there or....
        DataVisualizer dataVisualizer = new DataVisualizer();

        dataVisualizer.startDataVisualizer();
    }

    public static void parallel() {
        //Delete previous activity record
        DAO dao = new DAO();
        dao.deleteAllActivityRecord();

        //Getting farms from db
//        farms = dao.getFarmData();

        //Start data handling thread
        Thread dataHandlingThread = new Thread(new DataHandling());
        dataHandlingThread.start();

        //Generate Farmer
        Random random = new Random();
//        int randomFarmerNumber = random.nextInt(100);
        int randomFarmerNumber = 100;
        ExecutorService farmerThreadPool = Executors.newFixedThreadPool(randomFarmerNumber);
        FarmerSimulator simulator = new FarmerSimulator();
        Farmer[] farmers = simulator.generateFarmers(randomFarmerNumber);

        //Setup timer and timer thread
        TimerThread timerThread = new TimerThread();
        timerThread.start();
        String startTime = Timer.getCurrentTime();

        //Start farmer thread
        for (Farmer farmer : farmers) {
            farmerThreadPool.execute(farmer);
        }
        farmerThreadPool.shutdown();
        while (!farmerThreadPool.isTerminated()) {
            //wait all farmer finish job
        }
        Timer.setEnd(true);

        //Record End Time
        String endTime = Timer.getCurrentTime();
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("All thread have been terminated");

        try{
            timerThread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        DataVisualizer dataVisualizer = new DataVisualizer();

        dataVisualizer.startDataVisualizer();
    }
}
