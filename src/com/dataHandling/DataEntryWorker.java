package com.dataHandling;

import com.activity.Activity;
import com.util.DAO;
import com.util.IFarmLogger;

import java.util.concurrent.Callable;

public class DataEntryWorker implements Callable {

    Activity activity;

    public DataEntryWorker(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Object call() throws Exception {
        IFarmLogger logger = new IFarmLogger(activity.getFarmId());
        boolean success = DAO.insertActivityData(activity);
        System.out.println(success);
        if (success) {
            logger.logActivities(activity.getFarmId() + " " + activity.getAction() + " " + activity.getType() + " Field " + activity.getField() + " Row " + activity.getRow() + " " + activity.getQuantity() + " " + activity.getUnit() + " at " + activity.getFarmId() + " " + activity.getDate());
            return true;
        }
        return false;
    }
}
