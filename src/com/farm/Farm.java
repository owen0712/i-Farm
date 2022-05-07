package com.farm;

import java.util.List;

public class Farm {

    private String _id;
    private String name;
    private String address;
    private List<String> pesticides;
    private List<String> fertilizes;
    private List<String> plants;
    private int row;
    private int field;

    public Farm(){

    }

    public Farm(String _id, String name, String address, List<String> pesticides, List<String> fertilizes, List<String> plants, int row, int field) {
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

    public List<String> getPesticides() {
        return pesticides;
    }

    public void setPesticides(List<String> pesticides) {
        this.pesticides = pesticides;
    }

    public List<String> getFertilizes() {
        return fertilizes;
    }

    public void setFertilizes(List<String> fertilizes) {
        this.fertilizes = fertilizes;
    }

    public List<String> getPlants() {
        return plants;
    }

    public void setPlants(List<String> plants) {
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
