package com.farmer;

import com.farm.Farm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Farmer implements Runnable {

    private String _id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private List<Farm> farmList;

    public Farmer(String _id, String name, String email, String password, String phoneNumber, List<Farm> farmList) {
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

    public List<Farm> getFarmList() {
        return farmList;
    }

    public void setFarmList(List<Farm> farmList) {
        this.farmList = farmList;
    }

    public void displayFarmList() {
        System.out.println("Farms list that are in charged by " + name + " are :");
        for (Farm farm : farmList) {
//        System.out.println(farm.getName);
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
            int currentFarm = (int) Math.floor(Math.random() * (farmList.size()));
            farmNumberActivity.put(farmList.get(currentFarm).get_id(), (farmNumberActivity.get(farmList.get(currentFarm).get_id())-1));
        }
    }
}
