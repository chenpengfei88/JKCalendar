package com.fe.jkcalendar.vo;

import com.fe.jkcalendar.utils.StringUtils;

import java.util.List;

/**
 * Created by chenpengfei on 2016/11/29.
 */
public class YMonthVO {

    /**
     *  月份
     */
    private String yearMonth;

    /**
     *  月份下的日期
     */
    private List<DateVO> dateVOList;

    /**
     * 年
     */

    private int year;
    /**
     *  月
     */
    private int month;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        if(StringUtils.isEmpty(yearMonth)) return;
        String[] yearMonthArray = yearMonth.split("-");
        if(yearMonthArray.length < 2) return;
        year = Integer.valueOf(yearMonthArray[0]);
        month = Integer.valueOf(yearMonthArray[1]);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public List<DateVO> getDateVOList() {
        return dateVOList;
    }

    public void setDateVOList(List<DateVO> dateVOList) {
        this.dateVOList = dateVOList;
    }

    @Override
    public String toString() {
        String[] dateArray = yearMonth.split("-");
        return dateArray[0] + "年" + dateArray[1] + "月";
    }


}
