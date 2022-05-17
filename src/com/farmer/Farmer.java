package com.farmer;

import com.activity.Activity;
import com.dataHandling.DataHandling;
import com.farm.Farm;
import com.farm.Status;
import com.util.Logger;
import com.util.Timer;

import java.util.HashMap;
import java.util.Map;

public class Farmer implements Runnable {

    private String _id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Farm[] farmList;
    private Logger logger;
    private final String[] actionList = {"Sowing","Fertilizer","Pesticide","Harvesting"};

    public Farmer(String _id, String name, String email, String password, String phoneNumber, Farm[] farmList) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.farmList = farmList;
    }

    public Farmer() {
        logger = new Logger(this);
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

    public void generateActivity(Farm farm, int id){
        String activityId = "A"+(id+1);
        int field = (int)(Math.random()*farm.getField());
        int row = (int)(Math.random()*farm.getRow());
        Status status = farm.getStatusByRowAndField(row,field);
        String action;

        while (!status.getLock().isHeldByCurrentThread()){
            //check if the thread can lock the row and field
            if (status.getLock().tryLock()){
                status.getLock().lock();

                //check the row and field current action and decide the next action
                if(status.getStatus()==null){
                    action = this.actionList[0];
                }else{
                    action = this.actionList[(int)(Math.random()*(this.actionList.length-1)+1)];
                }

                String type;
                String unit;
                switch (action) {
                    case "Sowing", "Harvesting" -> {
                        int plantIndex = (int) (Math.random() * (farm.getPlants().length));
                        type = farm.getPlants()[plantIndex].getName();
                        unit = farm.getPlants()[plantIndex].getUnitType();
                    }
                    case "Fertilizer" -> {
                        int fertilizerIndex = (int) (Math.random() * (farm.getFertilizes().length));
                        type = farm.getFertilizes()[fertilizerIndex].getName();
                        unit = farm.getFertilizes()[fertilizerIndex].getUnitType();
                    }
                    case "Pesticide" -> {
                        int pesticideIndex = (int) (Math.random() * (farm.getPesticides().length));
                        type = farm.getPesticides()[pesticideIndex].getName();
                        unit = farm.getPesticides()[pesticideIndex].getUnitType();
                    }
                    default -> {
                        type = "N/A";
                        unit = "N/A";
                    }
                }

                Activity activity = new Activity(activityId, farm.get_id(), this._id, Timer.getCurrentTime(), action, type, unit,Math.random()*10, field, row);
//                activity.setFarmId(farm.get_id());
//                activity.setUserId(this._id);
//                activity.setDate(Timer.getCurrentTime());
//                activity.setAction(action);
//                activity.setType(type);
//                activity.setUnit(unit);
//                activity.setQuantity(Math.random()*10);
//                activity.setField(field);
//                activity.setRow(row);
                DataHandling.addElementIntoQueue(activity);

                //switch the action of current row and field to the next action
                //when action is harvesting, the action will change to null
                if (action.equals("Harvesting")){
                    status.setStatus(null);
                }else{
                    status.setStatus(action);
                }

                //log in to logfile
                //format: (Sowing Plant Field 1 Row 1 1.5 UnitType at FarmName 2022-05-18)
                logger.logActivities(activity.getAction()+" "+activity.getType()+" Field "+activity.getField()+" Row "+activity.getRow()+" "+activity.getQuantity()+" "+activity.getUnit()+" at "+farm.getName()+" "+activity.getDate());
                status.getLock().unlock();
                break;
            }
        }
    }


    @Override
    public void run() {
        int totalActNum = 0;

        // Create how many number of activity each farm must have
        Map<String, Integer> farmNumberActivity = new HashMap<>();
        for (Farm farm : farmList) {
            int randNum = (int) Math.floor(Math.random() * (1500 - 1000 + 1) + 1000);
            totalActNum += randNum;
            farmNumberActivity.put(farm.get_id(), randNum);
        }

        // Start for loop
        for (int i = 0; i < totalActNum; i++) {

            // Farmer randomly pick one farm
            int currentFarm = (int) Math.floor(Math.random() * (farmList.length));

            //When the number of activity is cleared
            if(farmNumberActivity.get(farmList[currentFarm].get_id()) <= 0){
                i--;
            }else {
                generateActivity(farmList[currentFarm],i);
                farmNumberActivity.put(farmList[currentFarm].get_id(), (farmNumberActivity.get(farmList[currentFarm].get_id()) - 1));
            }
        }
    }
}
