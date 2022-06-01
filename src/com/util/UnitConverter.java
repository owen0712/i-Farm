package com.util;

public class UnitConverter {

    public UnitConverter() {
    }

    //Directly return String for print
    public static String getConvertValueUnitString(double value, String unit){
        if (unit.equals("kg") || unit.equals("l") || unit.equals("packs(1kg)")){
            return (String.valueOf(value) + "kg");
        } else {
            return (String.valueOf(value/1000.0) + "kg");
        }
    }

    public static double getConvertValue(double value, String unit){
        if (unit.equals("kg") || unit.equals("l") || unit.equals("packs(1kg)")){
            return value;
        } else {
            return value/1000.0;
        }
    }
}
