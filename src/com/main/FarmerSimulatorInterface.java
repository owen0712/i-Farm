package com.main;

import com.farmer.Farmer;
import com.util.DAO;

public interface FarmerSimulatorInterface {
	Farmer[] generateFarmers(int numberOfFarmers, DAO dao);
}
