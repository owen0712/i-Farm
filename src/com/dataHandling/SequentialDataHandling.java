package com.dataHandling;

import com.activity.Activity;
import com.farm.Farm;
import com.main.Main;
import com.util.DAO;

import java.util.concurrent.Future;

public class SequentialDataHandling {

    private static int activityId = 1;
    private static DAO dao;

    //constructor
    public SequentialDataHandling() {
        if(dao==null){
            dao = new DAO();
        }
    }

    public void submitAvtivity(Activity activity) {
        //set activity id
        activity.set_id("A" + activityId);
        activityId++;

        //insert record into database,return true if successfully insert the record, else return false
        boolean success = dao.insertActivityData(activity);

        //write record into the log if successfully insert into database
        if(success) {
            String logText = String.format("%s: %-5s -> %-10s %-50s Field %2d Row %2d %6.2f%-5s", activity.getDate(), activity.get_id(), activity.getAction(), activity.getType(), activity.getField(), activity.getRow(), activity.getQuantity(), activity.getUnit(), activity.getFarmId());
            Farm farm = Main.farms[Integer.parseInt(activity.getFarmId().substring(2)) - 1];
            farm.logActivityRecord(logText);
        }
    }

}
