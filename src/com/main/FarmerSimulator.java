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

        farmers = Arrays.copyOfRange(tempFarmers, 0, numberOfFarmers);

        return farmers;
    }
}
