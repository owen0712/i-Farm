package com.util;

import com.activity.Activity;
import com.farm.Farm;
import com.farmer.Farmer;
import com.fertilizer.Fertilizer;
import com.pesticide.Pesticide;
import com.plant.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ifarm";
    private final String username = "root";
    private final String password = "root";
    private Connection connection;
    private PreparedStatement statement;

    public DAO() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Plant[] getPlantData() {
        List<Plant> plantList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM plants");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to plant
                Plant plant = new Plant();
                plant.set_id(result.getString("_id"));
                plant.setName(result.getString("name"));
                plant.setUnitType(result.getString("unitType"));
                plantList.add(plant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plantList.toArray(new Plant[plantList.size()]);
    }

    public Plant[] getPlantDataByFarmId(String farmId) {
        List<Plant> plantList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM farm_plant INNER JOIN plants ON farm_plant.plant_id=plants._id where farm_id=?");
            statement.setString(1, farmId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to plant
                Plant plant = new Plant();
                plant.set_id(result.getString("_id"));
                plant.setName(result.getString("name"));
                plant.setUnitType(result.getString("unitType"));
                plantList.add(plant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plantList.toArray(new Plant[plantList.size()]);
    }

    public Fertilizer[] getFertilizerData() {
        List<Fertilizer> fertilizerList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM fertilizers");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to fertilizer
                Fertilizer fertilizer = new Fertilizer();
                fertilizer.set_id(result.getString("_id"));
                fertilizer.setName(result.getString("name"));
                fertilizer.setUnitType(result.getString("unitType"));
                fertilizerList.add(fertilizer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fertilizerList.toArray(new Fertilizer[fertilizerList.size()]);
    }

    public Fertilizer[] getFertilizerDataByFarmId(String farmId) {
        List<Fertilizer> fertilizerList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM farm_fertilizer INNER JOIN fertilizers ON farm_fertilizer.fertilizer_id=fertilizers._id where farm_id=?");
            statement.setString(1, farmId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to fertilizer
                Fertilizer fertilizer = new Fertilizer();
                fertilizer.set_id(result.getString("_id"));
                fertilizer.setName(result.getString("name"));
                fertilizer.setUnitType(result.getString("unitType"));
                fertilizerList.add(fertilizer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fertilizerList.toArray(new Fertilizer[fertilizerList.size()]);
    }

    public Pesticide[] getPesticideData() {
        List<Pesticide> pesticideList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM pesticides");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to pesticide
                Pesticide pesticide = new Pesticide();
                pesticide.set_id(result.getString("_id"));
                pesticide.setName(result.getString("name"));
                pesticide.setUnitType(result.getString("unitType"));
                pesticideList.add(pesticide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesticideList.toArray(new Pesticide[pesticideList.size()]);
    }

    public Pesticide[] getPesticideDataByFarmId(String farmId) {
        List<Pesticide> pesticideList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM farm_pesticide INNER JOIN pesticides ON farm_pesticide.pesticide_id=pesticides._id where farm_id=?");
            statement.setString(1, farmId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to pesticide
                Pesticide pesticide = new Pesticide();
                pesticide.set_id(result.getString("_id"));
                pesticide.setName(result.getString("name"));
                pesticide.setUnitType(result.getString("unitType"));
                pesticideList.add(pesticide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesticideList.toArray(new Pesticide[pesticideList.size()]);
    }

    public Farmer[] getFarmerData() {
        List<Farmer> farmerList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to farmer
                Farmer farmer = new Farmer();
                farmer.set_id(result.getString("_id"));
                farmer.setName(result.getString("name"));
                farmer.setEmail(result.getString("email"));
                farmer.setPhoneNumber(result.getString("phoneNumber"));
                farmer.setPassword(result.getString("password"));
                //need to reconfirm the name in farmer class
                farmer.setFarmList(Arrays.asList(getFarmDataByFarmerId(farmer.get_id())));
                farmerList.add(farmer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmerList.toArray(new Farmer[farmerList.size()]);
    }

    public Farm[] getFarmData() {
        List<Farm> farmList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM farms");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to farms
                Farm farm = new Farm();
                farm.set_id(result.getString("_id"));
                farm.setName(result.getString("name"));
                farm.setAddress(result.getString("address"));
                farm.setPlants(getPlantDataByFarmId(farm.get_id()));
                farm.setFertilizes(getFertilizerDataByFarmId(farm.get_id()));
                farm.setPesticides(getPesticideDataByFarmId(farm.get_id()));
                farmList.add(farm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmList.toArray(new Farm[farmList.size()]);
    }

    public Farm[] getFarmDataByFarmerId(String farmerId) {
        List<Farm> farmList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM user_farm INNER JOIN farms ON user_farm.farm_id=farms._id where user_id=?");
            statement.setString(1, farmerId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to farms
                Farm farm = new Farm();
                farm.set_id(result.getString("_id"));
                farm.setName(result.getString("name"));
                farm.setAddress(result.getString("address"));
                farm.setPlants(getPlantDataByFarmId(farm.get_id()));
                farm.setFertilizes(getFertilizerDataByFarmId(farm.get_id()));
                farm.setPesticides(getPesticideDataByFarmId(farm.get_id()));
                farmList.add(farm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmList.toArray(new Farm[farmList.size()]);
    }

    public void insertActivityData(Activity activity) {
        try {
            statement = connection.prepareStatement("INSERT INTO activities('_id','farmId','userId','date','action','type','unit','quantity','row','field') VALUES(?,?,?,?,?,?,?,?,?,?)");
            statement.setString(0, activity.get_id());
            statement.setString(1, activity.getFarmId());
            statement.setString(2, activity.getUserId());
            statement.setString(3, activity.getDate());
            statement.setString(4, activity.getAction());
            statement.setString(5, activity.getType());
            statement.setString(6, activity.getUnit());
            statement.setDouble(7, activity.getQuantity());
            statement.setInt(8, activity.getRow());
            statement.setInt(9, activity.getField());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<Activity> getActivityByFarmId(String farmId) {
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM user_farm where farmId=?");
            statement.setString(1, farmId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to activity
                Activity activity = new Activity();
                activity.setFarmId(result.getString("farmId"));
                activity.setUserId(result.getString("userId"));
                activity.setDate(result.getString("date"));
                activity.setAction(result.getString("action"));
                activity.setType(result.getString("type"));
                activity.setUnit(result.getString("unit"));
                activity.setQuantity(result.getDouble("quantity"));
                activity.setField(result.getInt("field"));
                activity.setRow(result.getInt("row"));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }

    public List<Activity> getActivityByFarmerId(String userId) {
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM user_farm where userId=?");
            statement.setString(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to activity
                Activity activity = new Activity();
                activity.setFarmId(result.getString("farmId"));
                activity.setUserId(result.getString("userId"));
                activity.setDate(result.getString("date"));
                activity.setAction(result.getString("action"));
                activity.setType(result.getString("type"));
                activity.setUnit(result.getString("unit"));
                activity.setQuantity(result.getDouble("quantity"));
                activity.setField(result.getInt("field"));
                activity.setRow(result.getInt("row"));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }
}
