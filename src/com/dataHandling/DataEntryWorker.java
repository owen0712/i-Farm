package com.dataHandling;

import com.activity.Activity;
import com.util.DAO;
import com.util.IFarmLogger;
import com.util.Timer;

import java.util.concurrent.Callable;

public class DataEntryWorker implements Callable {

    Activity activity;

    public DataEntryWorker(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Object call() throws Exception {
        IFarmLogger logger = new IFarmLogger(activity.getFarmId(), activity.get_id());
        boolean success = DAO.insertActivityData(activity);
        if (success) {
            try {
                logger.logFarmActivities(Timer.getFakeTime()+": "+activity.get_id() + "-> " + activity.getFarmId() + " " + activity.getAction() + " " + activity.getType() + " Field " + activity.getField() + " Row " + activity.getRow() + " " + activity.getQuantity() + " " + activity.getUnit() + " at " + activity.getFarmId() + " " + activity.getDate());
            } catch (Exception e) {
                IFarmLogger errorlogger = new IFarmLogger();
                errorlogger.logErrorMessage(e.getMessage());
            }
            return true;
        }
        return false;
    }
}
