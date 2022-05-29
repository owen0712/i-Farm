package com.farmer;

import com.activity.Activity;
import com.dataHandling.DataHandling;
import com.farm.Farm;
import com.farm.Status;
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
    private IFarmLogger logger;
    private final String[] actionList = {"sowing","fertilizer","pesticide","harvest","sales"};

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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public void generateActivity(Farm farm){
        int field = (int)(Math.random()*farm.getField());
        int row = (int)(Math.random()*farm.getRow());
        Status status = farm.getStatusByRowAndField(row,field);
        String action;

        status.getLock().lock();
        //check the row and field current action and decide the next action
        if(status.getAction()==null){
            action = this.actionList[0];
        }else{
            action = this.actionList[(int)(Math.random()*(this.actionList.length-1)+1)];
        }

        String type;
        String unit;
        switch (action) {
            case "sowing", "harvest", "sales" -> {
                int plantIndex = (int) (Math.random() * (farm.getPlants().length));
                type = farm.getPlants()[plantIndex].getName();
                unit = farm.getPlants()[plantIndex].getUnitType();
            }
            case "fertilizer" -> {
                int fertilizerIndex = (int) (Math.random() * (farm.getFertilizes().length));
                type = farm.getFertilizes()[fertilizerIndex].getName();
                unit = farm.getFertilizes()[fertilizerIndex].getUnitType();
            }
            case "pesticide" -> {
                int pesticideIndex = (int) (Math.random() * (farm.getPesticides().length));
                type = farm.getPesticides()[pesticideIndex].getName();
                unit = farm.getPesticides()[pesticideIndex].getUnitType();
            }
            default -> {
                type = "N/A";
                unit = "N/A";
            }
        }

        Activity activity = new Activity(farm.get_id(), this._id, Timer.getCurrentTime(), action, type, unit,Math.random()*10, field, row);
        Future<Boolean> success=DataHandling.addElementIntoQueue(activity);

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
        }else{
            status.setAction(action);
        }
        //log in to logfile
        //format: (Sowing Plant Field 1 Row 1 1.5 UnitType at FarmName 2022-05-18)
        if(logger==null) {
            logger = new IFarmLogger(this);
        }
        logger.logFarmerActivities(Timer.getFakeTime()+": "+activity.getAction()+" "+activity.getType()+" Field "+activity.getField()+" Row "+activity.getRow()+" "+activity.getQuantity()+" "+activity.getUnit()+" at "+farm.get_id()+" "+activity.getDate());
        status.getLock().unlock();
    }

    @Override
    public void run() {
        int totalActNum = 0;

        // Create how many number of activity each farm must have
        Map<Farm, Integer> farmNumberActivity = new HashMap<>();
        for (Farm farm : farmList) {
//            int randNum = (int) Math.floor(Math.random() * (500) + 1 + 1000);
            int randNum = 100;
            totalActNum += randNum;
            farmNumberActivity.put(farm, randNum);
        }

        // Start for loop
        for (int i = 0; i < totalActNum; i++) {

            // Farmer randomly pick one farm
            int currentFarm = (int) Math.floor(Math.random() * (farmNumberActivity.size()));


            //When the number of activity is cleared
            Farm farm = (Farm) farmNumberActivity.keySet().toArray()[currentFarm];
            generateActivity(farm);
            if(farmNumberActivity.get(farm) == 1){
                farmNumberActivity.remove(farm);
            }
            else {
                farmNumberActivity.put(farm, (farmNumberActivity.get(farm) - 1));
            }
        }
    }
}
