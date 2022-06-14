package com.main;

import com.farm.Farm;
import com.farmer.Farmer;
import com.fertilizer.Fertilizer;
import com.pesticide.Pesticide;
import com.plant.Plant;
import com.util.DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FarmerSimulator implements FarmerSimulatorInterface {

    @Override
    public Farmer[] generateFarmers(int numberOfFarmers) {

        //Declare farmers array to store specific number of farmers later
        Farmer[] farmers;

        //Create DAO to retrieve farmer and farm data
        DAO dao = new DAO();

        //Retrieve farm data into Main class farms array
        Main.farms = dao.getFarmData();
        Farmer[] farmerList = dao.getFarmerData();

        List<Farm> farmList = new ArrayList<>();
        for(int i=0;i<Main.farms.length;i++){
            farmList.add(Main.farms[i]);
        }

        Random random = new Random();

        //random generate plant, pesticide and fertilizer for each farm
        for(Farm farm:farmList){
            List<Plant> plantList = dao.getPlantData();
            int numberOfPlant = random.nextInt(plantList.size()-1)+1;
            List<Plant> tempPlant = new ArrayList<>();
            for(int i=0;i<numberOfPlant;i++){
                Plant plant=plantList.remove(random.nextInt(plantList.size()));
                tempPlant.add(plant);
                dao.insertFarmPlant(farm.get_id(),plant.get_id());
            }
            farm.setPlants(tempPlant.toArray(new Plant[tempPlant.size()]));

            List<Pesticide> pesticideList = dao.getPesticideData();
            int numberOfPesticide = random.nextInt(pesticideList.size()-1)+1;
            List<Pesticide> tempPesticide = new ArrayList<>();
            for(int i=0;i<numberOfPesticide;i++){
                Pesticide pesticide=pesticideList.remove(random.nextInt(pesticideList.size()));
                tempPesticide.add(pesticide);
                dao.insertFarmPesticide(farm.get_id(),pesticide.get_id());
            }
            farm.setPesticides(tempPesticide.toArray(new Pesticide[tempPesticide.size()]));

            List<Fertilizer> fertilizerList = dao.getFertilizerData();
            int numberOfFertilizer = random.nextInt(fertilizerList.size()-1)+1;
            List<Fertilizer> tempFertilizer = new ArrayList<>();

            for(int i=0;i<numberOfFertilizer;i++){
                Fertilizer fertilizer=fertilizerList.remove(random.nextInt(fertilizerList.size()));
                tempFertilizer.add(fertilizer);
                dao.insertFarmFertilizer(farm.get_id(), fertilizer.get_id());
            }
            farm.setFertilizers(tempFertilizer.toArray(new Fertilizer[tempFertilizer.size()]));
        }

        //Retrieve farmer data and pass the selected number of farmers into farmers array
        farmers = Arrays.copyOfRange(farmerList, 0, numberOfFarmers);

        //random generate farm for each farmer
        for(Farmer farmer:farmers){
            List<Farm> tempFarm = new ArrayList<>();
            List<Integer> indexList = new ArrayList<>();
            for(int i=0;i< farmList.size();i++){
                indexList.add(i);
            }
            int numberOfFarm = random.nextInt(farmList.size()-1)+1;
            for(int i=0;i<numberOfFarm;i++){
                Farm farm=farmList.get(indexList.remove(random.nextInt(indexList.size())));
                tempFarm.add(farm);
                dao.insertUserFarm(farmer.get_id(),farm.get_id());
            }
            farmer.setFarmList(tempFarm.toArray(new Farm[tempFarm.size()]));
        }

        //Return the selected number of farmers back to the Main class farmers array
        return farmers;
    }
}
