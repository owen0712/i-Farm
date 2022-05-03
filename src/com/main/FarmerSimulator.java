package com.main;

import com.farmer.Farmer;

public class FarmerSimulator implements FarmerSimulatorInterface{

    @Override
    public Farmer[] generateFarmers(int numberOfFarmers) {
        Farmer[]farmers=new Farmer[numberOfFarmers];
        for(int i=0;i<farmers.length;i++){
            farmers[i]=new Farmer();
        }
        return farmers;
    }
}
