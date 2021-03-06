package com.pesticide;

public class Pesticide {

    private String _id;
    private String name;
    private String unitType;

    public Pesticide(String _id, String name, String unitType) {
        this._id = _id;
        this.name = name;
        this.unitType = unitType;
    }

    public Pesticide() {}

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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
