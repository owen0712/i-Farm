package com.util;

import com.farm.Farm;
import com.farmer.Farmer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class IFarmLogger {
    private Logger logger;
    private FileHandler fileHandler;

    public IFarmLogger(Farmer farmer) {
        //To set a specific logger for the received farmer
        logger = Logger.getLogger(Farmer.class.getName() + farmer.get_id());
        try {
            //To create new FileHandler for text file path, exp : log/farmer/FMR01.txt
            fileHandler = new FileHandler("log/farmer/" + farmer.get_id() + ".txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IFarmLogger(String farmId) {
        //To set a specific logger for the received farm
        logger = Logger.getLogger(Farm.class.getName() + farmId);
        try {
            //To create new FileHandler for text file path, exp : log/farm/FM01.txt
            fileHandler = new FileHandler("log/farm/" + farmId + ".txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IFarmLogger() {
        //To set a logger for error log
        logger = Logger.getLogger(DAO.class.getName());
        try {
            //To create new FileHandler for text file path, log/error.txt
            fileHandler = new FileHandler("log/error.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //To synchronise the logger info by Farm Activities
    public void logFarmActivities(String text) {
        logger.info(text);
    }

    //To synchronise the logger info by Error Message
    public void logErrorMessage(String text) {
        logger.info(text);
    }
}