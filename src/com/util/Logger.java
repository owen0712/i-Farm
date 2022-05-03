package com.util;

import com.farmer.Farmer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Logger {
    private java.util.logging.Logger logger;
    private FileHandler fileHandler;

    public Logger(Farmer farmer) {
        //to set a specific logger for the received farmer
        logger = java.util.logging.Logger.getLogger(Farmer.class.getName()+farmer.getName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        try {
            fileHandler = new FileHandler("log/farmer"+farmer.getName()+"_"+formatter.format(new Date())+".txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logActivities(String text){
        logger.info(text);
    }
}
