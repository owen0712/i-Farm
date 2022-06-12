package com.farmer;

import com.activity.Activity;
import com.dataHandling.SequentialDataHandling;
import com.farm.Farm;
import com.farm.Status;
import com.plant.Plant;
import com.util.IFarmLogger;
import com.util.Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Farmer implements Runnable {

    private String _id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Farm[] farmList;
    private final String[] actionList = {"sowing","fertilizer","pesticide","harvest","sales"};
    private Map<Farm, Integer> farmNumberActivity = new HashMap<>();
    private int totalActNum;

    //constructor
    public Farmer(String _id, String name, String email, String password, String phoneNumber, Farm[] farmList) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.farmList = farmList;
    }

    public Farmer() {
    }

    //all the setter and getter
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        Thread.currentThread().setName(_id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Farm[] getFarmList() {
        return farmList;
    }

    public void setFarmList(Farm[] farmList) {
        this.farmList = farmList;
    }

    //Generate Activity Method in concurrent approach
    public void generateActivity(Farm farm){
        //When there is disaster happened, the system will throw this exception display disaster message on current Farmer thread
        if(Timer.isDisasterTime()){
            throw new RuntimeException("Disaster happened at "+Thread.currentThread().getName());
        }

        //Randomly get field and row of the farm
        int field = (int)(Math.random()*farm.getField());
        int row = (int)(Math.random()*farm.getRow());
        Status status = farm.getStatusByRowAndField(row,field);
        String action;

        //lock the row and field
        status.getLock().lock();
        //check the row and field current action and decide the next action
        if(status.getAction()==null){
            action = this.actionList[0];
        }else if(status.getAction().equals("harvest")){
            action = this.actionList[4];
        }else{
            action = this.actionList[(int)(Math.random()*(this.actionList.length-2)+1)];
        }

        //get plant/fertilizer/pesticide based on the action
        String type;
        String unitType;
        Plant plant = null;
        switch (action) {
            case "sowing" -> {
                int plantIndex = (int) (Math.random() * (farm.getPlants().length));
                plant = farm.getPlants()[plantIndex];
                type = plant.getName();
                unitType = plant.getUnitType();
                status.setPlant(plant);
            }
            case "harvest", "sales" -> {
                //get the previous plant in the row and field
                plant = status.getPlant();
                type = plant.getName();
                unitType = plant.getUnitType();
            }
            case "fertilizer" -> {
                int fertilizerIndex = (int) (Math.random() * (farm.getFertilizes().length));
                type = farm.getFertilizes()[fertilizerIndex].getName();
                unitType = farm.getFertilizes()[fertilizerIndex].getUnitType();
            }
            case "pesticide" -> {
                int pesticideIndex = (int) (Math.random() * (farm.getPesticides().length));
                type = farm.getPesticides()[pesticideIndex].getName();
                unitType = farm.getPesticides()[pesticideIndex].getUnitType();
            }
            default -> {
                type = "N/A";
                unitType = "N/A";
            }
        }

        String unit;
        double quantity;
        // Randomly generate unit either [kg or g] for mass, [l or ml] for volume, [pack(1kg) or pack(500g)]for pack
        // Randomly generate quantity for kg (within 10) or g (within 1000)
        // Math.round(value*100.0)/100.0 to convert double value to two decimal
        switch (unitType) {
            case "mass" -> {
                unit = (Math.random()<0.5) ? "kg" : "g";
                quantity = (unit.equals("kg")) ? Math.round((Math.random()*10)*100.0)/100.0 : Math.round((Math.random()*1000)*100.0)/100.0;
            }
            case "volume" -> {
                unit = (Math.random()<0.5) ? "l" : "ml";
                quantity = (unit.equals("l")) ? Math.round((Math.random()*10)*100.0)/100.0 : Math.round((Math.random()*1000)*100.0)/100.0;
            }
            case "pack" -> {
                unit = (Math.random()<0.5) ? "packs(1kg)" : "packs(500g)";
                quantity = (int)(Math.random()*10+1);
            }
            default -> {
                unit = "N/A";
                quantity = -1.0;
            }
        }

        //Submit activity into data handling through farm and get the future
        Activity activity = new Activity(farm.get_id(), this._id, Timer.getFakeTime(), action, type, unit, quantity, field, row);
        Future<Boolean> success=farm.submitActivity(activity);

        while(true){
            try {
                if (!!success.get()) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //switch the action of current row and field to the next action
        //when action is harvesting, the action will change to null
        if (action.equals("sales")){
            status.setAction(null);
            status.setPlant(null);
        }else{
            status.setAction(action);
        }

        //unlock the row and field
        status.getLock().unlock();
    }

    @Override
    public void run() {
        // Create how many number of activity each farm must have
        if(farmNumberActivity.isEmpty()){
            totalActNum = 0;
            for (Farm farm : farmList) {
//            int randNum = (int) Math.floor(Math.random() * (500) + 1 + 1000);
                int randNum = 100;
                totalActNum += randNum;
                farmNumberActivity.put(farm, randNum);
            }
        }

        else{
            totalActNum = 0;
            for (Integer counter : farmNumberActivity.values()) {
                totalActNum += counter;
            }
        }

        // Start for loop
        for (int i = 0; i < totalActNum; i++) {

            // Farmer randomly pick one farm
            int currentFarm = (int) Math.floor(Math.random() * (farmNumberActivity.size()));

            Farm farm = (Farm) farmNumberActivity.keySet().toArray()[currentFarm];
            generateActivity(farm);

            //When the number of activity is cleared, the farm will be removed
            //else the activityNumber will decrease by 1
            if(farmNumberActivity.get(farm) == 1){
                farmNumberActivity.remove(farm);
            }
            else {
                farmNumberActivity.put(farm, (farmNumberActivity.get(farm) - 1));
            }
        }
    }

    //SEQUENTIAL APPROACH
    //Generate Activity Method in sequential approach
    public void generateSequentialActivity(Farm farm){
        //Randomly get field and row of the farm
        int field = (int)(Math.random()*farm.getField());
        int row = (int)(Math.random()*farm.getRow());
        Status status = farm.getStatusByRowAndField(row,field);
        String action;

        //check the row and field current action and decide the next action
        if(status.getAction()==null){
            action = this.actionList[0];
        }else if(status.getAction().equals("harvest")){
            action = this.actionList[4];
        }else{
            action = this.actionList[(int)(Math.random()*(this.actionList.length-2)+1)];
        }

        //get plant/fertilizer/pesticide based on the action
        String type;
        String unitType;
        Plant plant = null;
        switch (action) {
            case "sowing" -> {
                int plantIndex = (int) (Math.random() * (farm.getPlants().length));
                plant = farm.getPlants()[plantIndex];
                type = plant.getName();
                unitType = plant.getUnitType();
                status.setPlant(plant);
            }
            case "harvest", "sales" -> {
                plant = status.getPlant();
                type = plant.getName();
                unitType = plant.getUnitType();
            }
            case "fertilizer" -> {
                int fertilizerIndex = (int) (Math.random() * (farm.getFertilizes().length));
                type = farm.getFertilizes()[fertilizerIndex].getName();
                unitType = farm.getFertilizes()[fertilizerIndex].getUnitType();
            }
            case "pesticide" -> {
                int pesticideIndex = (int) (Math.random() * (farm.getPesticides().length));
                type = farm.getPesticides()[pesticideIndex].getName();
                unitType = farm.getPesticides()[pesticideIndex].getUnitType();
            }
            default -> {
                type = "N/A";
                unitType = "N/A";
            }
        }

        String unit;
        double quantity;
        // Randomly generate unit either [kg or g] for mass, [l or ml] for volume, [pack(1kg) or pack(500g)]for pack
        // Randomly generate quantity for kg (within 10) or g (within 1000)
        // Math.round(value*100.0)/100.0 to convert double value to two decimal
        switch (unitType) {
            case "mass" -> {
                unit = (Math.random()<0.5) ? "kg" : "g";
                quantity = (unit.equals("kg")) ? Math.round((Math.random()*10)*100.0)/100.0 : Math.round((Math.random()*1000)*100.0)/100.0;
            }
            case "volume" -> {
                unit = (Math.random()<0.5) ? "l" : "ml";
                quantity = (unit.equals("l")) ? Math.round((Math.random()*10)*100.0)/100.0 : Math.round((Math.random()*1000)*100.0)/100.0;
            }
            case "pack" -> {
                unit = (Math.random()<0.5) ? "packs(1kg)" : "packs(500g)";
                quantity = (int)(Math.random()*10+1);
            }
            default -> {
                unit = "N/A";
                quantity = -1.0;
            }
        }

        Activity activity = new Activity(farm.get_id(), this._id, Timer.getFakeTime(), action, type, unit, quantity, field, row);

        //Submit activity into data handling which in sequential approach
        SequentialDataHandling dataHandling = new SequentialDataHandling();
        dataHandling.submitAvtivity(activity);

        //switch the action of current row and field to the next action
        //when action is harvesting, the action will change to null
        if (action.equals("sales")){
            status.setAction(null);
            status.setPlant(null);
        }else{
            status.setAction(action);
        }
    }

    // Run the class to generate all the farmer activities in sequential approach
    public void runSequentialTask() {
        // Create how many number of activity each farm must have
        totalActNum = 0;
        for (Farm farm : farmList) {
//        int randNum = (int) Math.floor(Math.random() * (500) + 1 + 1000);
            int randNum = 100;
            totalActNum += randNum;
            farmNumberActivity.put(farm, randNum);
        }

        // Start for loop
        for (int i = 0; i < totalActNum; i++) {

            // Farmer randomly pick one farm
            int currentFarm = (int) Math.floor(Math.random() * (farmNumberActivity.size()));

            Farm farm = (Farm) farmNumberActivity.keySet().toArray()[currentFarm];
            generateSequentialActivity(farm);

            //When the number of activity is cleared, the farm will be removed
            //else the activityNumber will decrease by 1
            if(farmNumberActivity.get(farm) == 1){
                farmNumberActivity.remove(farm);
            }
            else {
                farmNumberActivity.put(farm, (farmNumberActivity.get(farm) - 1));
            }
        }
    }
}
