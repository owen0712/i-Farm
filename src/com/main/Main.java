package com.main;

import com.dataHandling.DataHandling;
import com.dataVisualization.DataVisualizer;
import com.farm.Farm;
import com.farmer.Farmer;
import com.util.DAO;
import com.util.Timer;
import com.util.TimerThread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static Farm[] farms;

    public static void main(String[] args) throws InterruptedException {
        sequential();
//        concurrent();
    }

    public static void sequential() {
        //Delete previous activity record
        DAO dao = new DAO();
        dao.deleteAllActivityRecord();

        //Generate Farmer
        Random random = new Random();
//        int randomFarmerNumber = random.nextInt(100);
        int randomFarmerNumber = 100;
        FarmerSimulator simulator = new FarmerSimulator();
        Farmer[] farmers = simulator.generateFarmers(randomFarmerNumber);

        //Setup timer and timer thread
        TimerThread timerThread = new TimerThread();
        timerThread.start();
        String startTime = Timer.getCurrentTime();

        //Start farmer activity in sequential form
        for (Farmer farmer : farmers) {
            farmer.runSequentialTask();
        }

        //indicate generate activity completed, stop timer thread
        Timer.setEnd(true);

        //Record End Time
        String endTime = Timer.getCurrentTime();
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        try {
            Date startDate=new SimpleDateFormat("YYYY.MM.dd HH.mm.ss").parse(startTime);
            Date endDate=new SimpleDateFormat( "YYYY.MM.dd HH.mm.ss").parse(endTime);
            System.out.println("Time used: "+ (endDate.getTime()-startDate.getTime())+" milliseconds");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("All thread have been terminated");

        //Ensure timer thread end before start DataVisualizer
        try{
            timerThread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        //start DataVisualizer
        DataVisualizer dataVisualizer = new DataVisualizer();

        dataVisualizer.startDataVisualizer();

    }

    public static void concurrent() {
        //Delete previous activity record
        DAO dao = new DAO();
        dao.deleteAllActivityRecord();

        //Generate Farmer
        Random random = new Random();
//        int randomFarmerNumber = random.nextInt(100);
        int randomFarmerNumber = 100;
        ExecutorService farmerThreadPool = Executors.newFixedThreadPool(randomFarmerNumber);
        FarmerSimulator simulator = new FarmerSimulator();
        Farmer[] farmers = simulator.generateFarmers(randomFarmerNumber);

        //Set disaster time to simulate disaster happened
        Timer.setDisasterTime(5);

        //Setup timer and timer thread
        TimerThread timerThread = new TimerThread();
        timerThread.start();
        String startTime = Timer.getCurrentTime();

        //Start farmer thread
        for (Farmer farmer : farmers) {
            farmerThreadPool.execute(farmer);
        }

        //Handle disaster
        if(Timer.getDisasterTime() > 0){
            farmerThreadPool.shutdown();
            while (!farmerThreadPool.isTerminated()) {
                //wait all farmer finish job
            }
            System.out.println("Disaster!!!");
            try{
                Thread.sleep(5000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            //reset all lock and status of field and row
            for(Farm farm: farms){
                farm.initializeStatus();
            }
            farmerThreadPool = Executors.newFixedThreadPool(randomFarmerNumber);
            for (Farmer farmer : farmers) {
                farmerThreadPool.execute(farmer);
            }
        }

        //waiting all farmer to finish the work
        farmerThreadPool.shutdown();
        while (!farmerThreadPool.isTerminated()) {
            //wait all farmer finish job
        }

        //indicate generate activity completed, stop timer thread
        Timer.setEnd(true);

        //Record End Time
        String endTime = Timer.getCurrentTime();

        //Display startTime, endTime and time used
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        try {
            Date startDate=new SimpleDateFormat("YYYY.MM.dd HH.mm.ss").parse(startTime);
            Date endDate=new SimpleDateFormat( "YYYY.MM.dd HH.mm.ss").parse(endTime);
            System.out.println("Time used: "+ (endDate.getTime()-startDate.getTime())+" milliseconds");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("All thread have been terminated");

        //Ensure timer thread end before start DataVisualizer
        try{
            timerThread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        //start DataVisualizer
        DataVisualizer dataVisualizer = new DataVisualizer();

        dataVisualizer.startDataVisualizer();
    }
}
