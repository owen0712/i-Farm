package com.farm;

import com.fertilizer.Fertilizer;
import com.pesticide.Pesticide;
import com.plant.Plant;

public class Farm {

    private String _id;
    private String name;
    private String address;
    private Pesticide[] pesticides;
    private Fertilizer[] fertilizes;
    private Plant[] plants;
    private int row;
    private int field;

    public Farm(){

    }

    public Farm(String _id, String name, String address, Pesticide[] pesticides, Fertilizer[] fertilizes, Plant[] plants, int row, int field) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.pesticides = pesticides;
        this.fertilizes = fertilizes;
        this.plants = plants;
        this.row = row;
        this.field = field;
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
        return fertilizes;
    }

    public void setFertilizes(Fertilizer[] fertilizes) {
        this.fertilizes = fertilizes;
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
}
