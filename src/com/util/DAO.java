package com.util;

import com.activity.Activity;
import com.farm.Farm;
import com.farmer.Farmer;
import com.fertilizer.Fertilizer;
import com.pesticide.Pesticide;
import com.plant.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ifarm";
    private final String username = "root";
    private final String password = "root";
    private static Connection connection;
    private static Farm[] tempFarm;

    //constructor to initiate connection with database
    public DAO() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllActivityRecord() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("TRUNCATE activities");
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Plant[] getPlantDataByFarmId(String farmId) {
        PreparedStatement statement;
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

    public Fertilizer[] getFertilizerDataByFarmId(String farmId) {
        PreparedStatement statement;
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

    public Pesticide[] getPesticideDataByFarmId(String farmId) {
        PreparedStatement statement;
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
        PreparedStatement statement;
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
                farmer.setFarmList(getFarmDataByFarmerId(farmer.get_id()));
                farmerList.add(farmer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmerList.toArray(new Farmer[farmerList.size()]);
    }

    public Farm[] getFarmData() {
        PreparedStatement statement;
        List<Farm> farmList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM farms");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to farm
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
        tempFarm = farmList.toArray(new Farm[farmList.size()]);
        return tempFarm;
    }

    public Farm[] getFarmDataByFarmerId(String farmerId) {
        PreparedStatement statement;
        List<Farm> farmList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM user_farm where user_id=?");
            statement.setString(1, farmerId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //to ensure all farmer get same farm object
                int index = Integer.parseInt(result.getString("farm_id").substring(2)) - 1;
                farmList.add(tempFarm[index]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmList.toArray(new Farm[farmList.size()]);
    }

    public static boolean insertActivityData(Activity activity) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO activities(_id,farmId,userId,date,action,type,unit,quantity,field,farm_row) VALUES(?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, activity.get_id());
            statement.setString(2, activity.getFarmId());
            statement.setString(3, activity.getUserId());
            statement.setString(4, activity.getDate());
            statement.setString(5, activity.getAction());
            statement.setString(6, activity.getType());
            statement.setString(7, activity.getUnit());
            statement.setDouble(8, activity.getQuantity());
            statement.setInt(9, activity.getField());
            statement.setInt(10, activity.getRow());
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            e.printStackTrace();
            IFarmLogger logger = new IFarmLogger();
            logger.logErrorMessage(e.getMessage());
            return false;
        }
    }

    //get the record for data visualizer mode 1
    public List<Activity> getActivityByFarmId(String farmId) {
        PreparedStatement statement;
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM activities where farmId=? ORDER BY date ASC");
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
                activity.setRow(result.getInt("farm_row"));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }

    //get the record for data visualizer mode 2
    public List<Activity> getActivityByFarmerId(String userId) {
        PreparedStatement statement;
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM activities where userId=? ORDER BY date ASC");
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
                activity.setRow(result.getInt("farm_row"));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }

    //get the record for data visualizer mode 3
    public List<Activity> getActivityByFarmerIdAndType(String farmId, String displayType) {
        PreparedStatement statement;
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM activities where farmId=? and type=? ORDER BY date ASC");
            statement.setString(1, farmId);
            statement.setString(2, displayType);
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
                activity.setRow(result.getInt("farm_row"));
                activityList.add(activity);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }

    //get the record for data visualizer mode 4
    public List<Activity> getActivityByFarmerIdAndTypeBetweenDate(String farmId, String displayType, String date1, String date2) {
        PreparedStatement statement;
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM activities where farmId=? and type=? and date BETWEEN ? and ? ORDER BY date ASC");
            statement.setString(1, farmId);
            statement.setString(2, displayType);
            statement.setString(3, date1);
            statement.setString(4, date2);
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
                activity.setRow(result.getInt("farm_row"));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }

    //get the record for data visualizer mode 5
    public List<Activity> getActivityByFarmerIdAndTypeBetweenDateWithFieldRow(String farmId, String displayType, String date1, String date2, int field, int row) {
        PreparedStatement statement;
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM activities where farmId=? and type=? and field=? and farm_row=? and date BETWEEN ? and ? ORDER BY date ASC");
            statement.setString(1, farmId);
            statement.setString(2, displayType);
            statement.setInt(3, field);
            statement.setInt(4, row);
            statement.setString(5, date1);
            statement.setString(6, date2);
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
                activity.setRow(result.getInt("farm_row"));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }

    //get the startDate of simulation and endDate of simulation for data visualizer
    public List<Activity> getFirstAndLastRecord(String farmId) {
        PreparedStatement statement;
        List<Activity> activityList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("(SELECT * from activities where farmId=? order by Date ASC LIMIT 1) UNION " +
                    "(SELECT * from activities order by Date DESC LIMIT 1)");
            statement.setString(1, farmId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Activity activity = new Activity();
                activity.setFarmId(result.getString("farmId"));
                activity.setUserId(result.getString("userId"));
                activity.setDate(result.getString("date"));
                activity.setAction(result.getString("action"));
                activity.setType(result.getString("type"));
                activity.setUnit(result.getString("unit"));
                activity.setQuantity(result.getDouble("quantity"));
                activity.setField(result.getInt("field"));
                activity.setRow(result.getInt("farm_row"));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityList;
    }
}
