package com.dataHandling;

import com.activity.Activity;
import com.util.DAO;
import com.util.IFarmLogger;

import java.util.concurrent.Callable;

public class DataEntryWorker implements Callable {

    Activity activity;
    DAO dao;

    public DataEntryWorker(Activity activity) {
        this.activity = activity;
        this.dao = new DAO();
    }

    @Override
    public Object call() throws Exception {
        IFarmLogger logger = new IFarmLogger(activity.getFarmId());
        boolean success = dao.insertActivityData(activity);
        if (success) {
            logger.logActivities(activity.getFarmId() + " " + activity.getAction() + " " + activity.getType() + " Field " + activity.getField() + " Row " + activity.getRow() + " " + activity.getQuantity() + " " + activity.getUnit() + " at " + activity.getFarmId() + " " + activity.getDate());
            return true;
        }
        return false;
    }
}
