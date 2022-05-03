package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

}
