package com.util;

import com.farm.Farm;
import com.farmer.Farmer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;

public class IFarmLogger {
    private Logger logger;
    private FileHandler fileHandler;

    public IFarmLogger(Farmer farmer) {
        //to set a specific logger for the received farmer
        logger = Logger.getLogger(Farmer.class.getName()+farmer.get_id());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        try {
            fileHandler = new FileHandler("log/farmer/"+farmer.get_id()+".txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IFarmLogger(String farmId){
        logger = Logger.getLogger(Farm.class.getName()+farmId);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        try {
            fileHandler = new FileHandler("log/farm/"+farmId+".txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logActivities(String text){
        logger.info(text);
        fileHandler.close();
    }
}
