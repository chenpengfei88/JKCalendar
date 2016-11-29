package com.fe.jkcalendar.vo;

/**
 * Created by chenpengfei on 2016/11/25.
 */
public class DateVO {


    /**
     *  阳历
     */
    private int date;

    /**
     *  阳历对应的阴历
     */
    private String lunarDate;

    /**
     *   是否被选中
     */
    private boolean isSelect;

    /**
     * 当前日期
     */
    private String currentDate;

    /**
     *  周几
     */
    private String week;

    public DateVO(int date, boolean isSelect) {
        this.date = date;
        this.isSelect = isSelect;
    }

    public DateVO() {
       this(-1, false);
    }


    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getLunarDate() {
        return lunarDate;
    }

    public void setLunarDate(String lunarDate) {
        this.lunarDate = lunarDate;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }


}
