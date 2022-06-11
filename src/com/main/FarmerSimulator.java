package com.main;

import com.farmer.Farmer;
import com.util.DAO;

import java.util.Arrays;

public class FarmerSimulator implements FarmerSimulatorInterface {

    @Override
    public Farmer[] generateFarmers(int numberOfFarmers) {

        //Declare farmers array to store specific number of farmers later
        Farmer[] farmers;

        //Create DAO to retrieve farmer and farm data
        DAO dao = new DAO();

        //Retrieve farm data into Main class farms array
        Main.farms = dao.getFarmData();
        Farmer[] tempFarmers = dao.getFarmerData();

        //Retrieve farmer data and pass the selected number of farmers into farmers array
        farmers = Arrays.copyOfRange(tempFarmers, 0, numberOfFarmers);

        //Return the selected number of farmers back to the Main class farmers array
        return farmers;
    }
}
