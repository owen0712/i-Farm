package com.activity;

public class Activity {

    private String _id;
    private String farmId;
    private String userId;
    private String date;
    private String action;
    private String type;
    private String unit;
    private Double quantity;
    private Integer field;
    private Integer row;

    public Activity(String farmId, String userId, String date, String action, String type, String unit, Double quantity, Integer field, Integer row) {
        this.farmId = farmId;
        this.userId = userId;
        this.date = date;
        this.action = action;
        this.type = type;
        this.unit = unit;
        this.quantity = quantity;
        this.field = field;
        this.row = row;
    }

    public Activity() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getField() {
        return field;
    }

    public void setField(Integer field) {
        this.field = field;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

}
