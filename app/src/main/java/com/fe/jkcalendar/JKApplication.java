package com.fe.jkcalendar;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenpengfei on 2016/12/7.
 */
public class JKApplication extends Application {

    public static final Map<String, String> HolidayMap = new HashMap<>();
    static final String HOLIDAY = "休";
    static final String WORK = "班";

    public static JKApplication jkApplication;

    public String getInfo(String date) {
        return HolidayMap.get(date);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        jkApplication = this;
        HolidayMap.put("2016-12-31", HOLIDAY);
        HolidayMap.put("2017-01-01", HOLIDAY);
        HolidayMap.put("2017-01-02", HOLIDAY);
        HolidayMap.put("2017-01-22", WORK);
        HolidayMap.put("2017-01-27", HOLIDAY);
        HolidayMap.put("2017-01-28", HOLIDAY);
        HolidayMap.put("2017-01-29", HOLIDAY);
        HolidayMap.put("2017-01-30", HOLIDAY);
        HolidayMap.put("2017-01-31", HOLIDAY);
        HolidayMap.put("2017-02-01", HOLIDAY);
        HolidayMap.put("2017-02-02", HOLIDAY);
        HolidayMap.put("2017-02-04", WORK);

        HolidayMap.put("2017-04-01", WORK);
        HolidayMap.put("2017-04-02", HOLIDAY);
        HolidayMap.put("2017-04-03", HOLIDAY);
        HolidayMap.put("2017-04-04", HOLIDAY);
        HolidayMap.put("2017-04-29", HOLIDAY);
        HolidayMap.put("2017-04-30", HOLIDAY);

        HolidayMap.put("2017-05-01", HOLIDAY);
        HolidayMap.put("2017-05-27", WORK);
        HolidayMap.put("2017-05-28", HOLIDAY);
        HolidayMap.put("2017-05-29", HOLIDAY);
        HolidayMap.put("2017-05-30", HOLIDAY);

        HolidayMap.put("2017-09-30", WORK);

        for(int i = 0; i <= 8; i++) {
            HolidayMap.put("2017-10-0" + i, HOLIDAY);
        }

        HolidayMap.put("2016-" + 12 + "-16", "发工资");
        for(int i = 0; i <= 12; i++) {
            HolidayMap.put("2017-" + (i > 9 ? i : "0" + i) + "-16", "发工资");
        }
    }
}
