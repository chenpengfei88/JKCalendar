package com.fe.jkcalendar.utils;

import com.fe.jkcalendar.vo.DateVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chenpengfei on 2016/11/25.
 */
public class DateUtils {

    public static final String DATE_PATTERN_FOUR= "yyyy-MM";
    public static final String DATE_PATTERN_TWO = "yyyy-MM-dd";

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;


    private static SimpleDateFormat sdf;
    private static int[] weekArray = {7, 1, 2, 3, 4, 5, 6};
    private static String[] weekStrArray = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String getCurrentDateStr(String datePattern) {
        sdf = new SimpleDateFormat(datePattern);
        return sdf.format(new Date());
    }

    public static Date getDate(String dateStr, String datePattern){
        sdf = new SimpleDateFormat(datePattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getCurrentYearOrMonthOrDay(int type) {
        int time = 0;
        Calendar cal = Calendar.getInstance();
        switch (type) {
            case YEAR:
                time = cal.get(Calendar.YEAR);
                break;
            case MONTH:
                time = cal.get(Calendar.MONTH) + 1;
                break;
            case DAY:
                time = cal.get(Calendar.DATE);
                break;
            default:
                break;
        }
        return time;
    }

    /**
     *  该月的第一天是周几
     */
    public static int getWeekCurrentMonthDay(Calendar cal){
        return getWeekCurrentMonthDay(cal, 1);
    }

    /**
     *  该月的第day天是周几
     */
    public static int getWeekCurrentMonthDay(Calendar cal, int day){
        int[] weekArray = {7, 1, 2, 3, 4, 5, 6};
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.setTime(cal.getTime());
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return weekArray[week - 1];
    }

    /**
     *  该月的第day天是周几
     */
    public static String getWeekStrCurrentMonthDay(Calendar cal, int day){
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.setTime(cal.getTime());
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return weekStrArray[week - 1];
    }


    /**
     * 得到当前年月下的天数
     * @param yearMonthStr 年月
     * @param currentDay 当前天
     * @param isInit 是否初始化
     * @return
     */
    public static List<DateVO> getYearMonthDayList(String yearMonthStr, int currentDay, boolean isInit) {
        List<DateVO> dateVoList = new ArrayList<>();
        Date yearMonthDate = getDate(yearMonthStr, DATE_PATTERN_FOUR);
        if(yearMonthDate == null) return dateVoList;

        Calendar cal = Calendar.getInstance();
        cal.setTime(yearMonthDate);
        int week = getWeekCurrentMonthDay(cal);
        int monthMaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i < (monthMaxDay + week); i++) {
            DateVO dateVo;
            if(i < week){
                dateVo = new DateVO();
            } else {
                dateVo = new DateVO((i - week) + 1, false);
                dateVo.setCurrentDate(yearMonthStr + "-" + (dateVo.getDate() > 9 ? dateVo.getDate()  : "0" + dateVo.getDate()));
                //得到对应的阴历
                Calendar today = Calendar.getInstance();
                today.setTime(getDate(dateVo.getCurrentDate(), DATE_PATTERN_TWO));
                dateVo.setLunarDate(new LunarUtil(today).toString());
                //周几
                dateVo.setWeek(getWeekStrCurrentMonthDay(cal, dateVo.getDate()));
            }

            if (isInit){
                if(dateVo.getDate() == currentDay){
                    dateVo.setSelect(true);
                }
            } else {
                if(dateVo.getDate() == 1){
                    dateVo.setSelect(true);
                }
            }
            dateVoList.add(dateVo);
        }
        int c = dateVoList.size() % 7;
        if (c != 0) {
            for (int j = 0; j < (7 - c); j++) {
                dateVoList.add(new DateVO());
            }
        }
        return dateVoList;
    }

    /**
     *  得到当前日期的天数
     * @return
     */
    public static List<DateVO> getCurrentDateDayList() {
        return getYearMonthDayList(getCurrentDateStr(DATE_PATTERN_FOUR), getCurrentYearOrMonthOrDay(DAY), true);
    }

    /**
     * 得到当前的日期
     */
    public static DateVO getCurrentDateVo(List<DateVO> dateVOList) {
        int day = DateUtils.getCurrentYearOrMonthOrDay(DateUtils.DAY);
        DateVO currentDateVo = null;
        for(DateVO dateVO : dateVOList) {
            if(dateVO.getDate() == day) {
                currentDateVo = dateVO;
                break;
            }
        }
        return currentDateVo;
    }




}
