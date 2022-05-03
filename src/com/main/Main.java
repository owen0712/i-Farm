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
    }
}
