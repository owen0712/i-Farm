package com.util;

public class UnitConverter {

    public UnitConverter() {
    }

    // static method to convert the value to "kg" based on unitType
    public static double getConvertValue(double value, String unit){
        if (unit.equals("kg") || unit.equals("l") || unit.equals("packs(1kg)")){
            return value;
        } else if(unit.equals("packs(500g)")){
            return value*500/1000;
        } else {
            return value/1000.0;
        }
    }
}
