package com.farm;

import com.activity.Activity;
import com.dataHandling.DataHandling;
import com.fertilizer.Fertilizer;
import com.pesticide.Pesticide;
import com.plant.Plant;
import com.util.IFarmLogger;

import java.util.concurrent.Future;

public class Farm {

    private String _id;
    private String name;
    private String address;
    private Pesticide[] pesticides;
    private Fertilizer[] fertilizers;
    private Plant[] plants;
    private int row;
    private int field;
    private Status[][] status;
    private IFarmLogger logger;
    private DataHandling dataHandler;

    /* Initialize random number row and field for each farm
       Initialize a double array for the status of each row and field
       Initialize a new data handling
     */
    public Farm() {
        this.row = (int) (Math.random() * 6 + 1);   // total number of row 1 - 5
        this.field = (int) (Math.random() * 6 + 1); // total number of field 1 - 5
        status = new Status[this.row][this.field];
        initializeStatus();
        dataHandler = new DataHandling();
    }

    public Farm(String _id, String name, String address, Pesticide[] pesticides, Fertilizer[] fertilizers, Plant[] plants, int row, int field, Status[][] status) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.pesticides = pesticides;
        this.fertilizers = fertilizers;
        this.plants = plants;
        this.row = row;
        this.field = field;
        this.status = status;
        this.row = (int) (Math.random() * 6 + 1);   // total number of row 1 - 5
        this.field = (int) (Math.random() * 6 + 1); // total number of field 1 - 5
        status = new Status[this.row][this.field];
        initializeStatus();
        dataHandler = new DataHandling();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Pesticide[] getPesticides() {
        return pesticides;
    }

    public void setPesticides(Pesticide[] pesticides) {
        this.pesticides = pesticides;
    }

    public Fertilizer[] getFertilizes() {
        return fertilizers;
    }

    public void setFertilizers(Fertilizer[] fertilizes) {
        this.fertilizers = fertilizes;
    }

    public Plant[] getPlants() {
        return plants;
    }

    public void setPlants(Plant[] plants) {
        this.plants = plants;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public Status getStatusByRowAndField(int row, int field) {
        return this.status[row][field];
    }

    public void setStatusByRowAndField(int row, int field, Status status) {
        this.status[row][field] = status;
    }

    // Initialize the status of row and field of a certain farm
    public void initializeStatus() {
        for (int row = 0; row < status.length; row++) {
            for (int column = 0; column < status[row].length; column++) {
                status[row][column] = new Status();
            }
        }
    }

    // Log the farm activities
    public void logActivityRecord(String log) {
        if (logger == null) {
            logger = new IFarmLogger(_id);
        }
        logger.logFarmActivities(log);
    }

    // Submit activity into data handling to insert the activity data into the database
    public Future<Boolean> submitActivity(Activity activity) {
        return dataHandler.addElementIntoQueue(activity);
    }
}
