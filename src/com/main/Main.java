package com.main;

import com.farmer.Farmer;

import java.util.Random;

public class Main {

    public static void main(String[]args) throws InterruptedException {
        Random random = new Random();
        FarmerSimulator simulator = new FarmerSimulator();
        Farmer[]farmers=simulator.generateFarmers(random.nextInt(100)+100);
        for(Farmer farmer:farmers){
            Thread farmerThread=new Thread(farmer);
            farmerThread.start();
        }

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
    }
}
