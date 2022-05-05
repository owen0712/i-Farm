package com.util;

import com.activity.Activity;
import com.fertilizer.Fertilizer;
import com.pesticide.Pesticide;
import com.plant.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/ifarm";
    private String username = "root";
    private String password = "root";
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

    public List<Plant> getPlantData() {
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
        return plantList;
    }

    public List<Fertilizer> getFertilizerData() {
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
        return fertilizerList;
    }

    public List<Pesticide> getPesticideData() {
        List<Pesticide> pesticideList = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM plants");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //mapping result set to plant
                Pesticide pesticide = new Pesticide();
                pesticide.set_id(result.getString("_id"));
                pesticide.setName(result.getString("name"));
                pesticide.setUnitType(result.getString("unitType"));
                pesticideList.add(pesticide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesticideList;
    }

    public void insertActivityData(Activity activity) {
        try {
            statement = connection.prepareStatement("INSERT INTO activities('_id','farmId','userId','date','action','type','unit','quantity','row','field') VALUES(?,?,?,?,?,?,?,?,?,?)");
            statement.setString(0,activity.get_id());
            statement.setString(1, activity.getFarmId());
            statement.setString(2, activity.getUserId());
            statement.setString(3,activity.getDate());
            statement.setString(4,activity.getAction());
            statement.setString(5,activity.getType());
            statement.setString(6,activity.getUnit());
            statement.setDouble(7,activity.getQuantity());
            statement.setInt(8,activity.getRow());
            statement.setInt(9,activity.getField());
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
}
