package com.main;

import com.farmer.Farmer;
import com.util.DAO;

import java.util.Arrays;

public class FarmerSimulator implements FarmerSimulatorInterface {

    @Override
    public Farmer[] generateFarmers(int numberOfFarmers) {

        Farmer[] farmers;

        DAO dao = new DAO();

        Main.farms = dao.getFarmData();
        Farmer[] tempFarmers = dao.getFarmerData();

//        for(int i = 0; i < numberOfFarmers; i++){
//            for (int j = 0; j < tempFarmers[i].getFarmList().length; j++){
//                tempFarmers[i].getFarmList()[j] = Main.farms[j];
//            }
//        }

        farmers = Arrays.copyOfRange(tempFarmers, 0, numberOfFarmers);

        return farmers;
    }
}
