package com.dataHandling;

import com.activity.Activity;
import com.farm.Farm;
import com.main.Main;
import com.util.DAO;
import com.util.IFarmLogger;

import java.util.concurrent.Callable;

public class DataEntryWorker implements Callable {

    private Activity activity;
    private DAO dao;

    public DataEntryWorker(Activity activity, DAO dao) {
        this.activity = activity;
        this.dao = dao;
    }

    @Override
    public Object call() throws Exception {
        boolean success = dao.insertActivityData(activity);
        if (success) {
            try {
                String logText = String.format("%s: %-5s -> %-10s %-50s Field %2d Row %2d %6.2f%-5s", activity.getDate(), activity.get_id(), activity.getAction(), activity.getType(), activity.getField(), activity.getRow(), activity.getQuantity(), activity.getUnit(), activity.getFarmId());
                Farm farm=Main.farms[Integer.parseInt(activity.getFarmId().substring(2)) - 1];
                farm.logActivityRecord(logText);
            } catch (Exception e) {
                IFarmLogger errorlogger = new IFarmLogger();
                errorlogger.logErrorMessage(e.getMessage());
            }
            return true;
        }
        return false;
    }
}
