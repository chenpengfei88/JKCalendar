package com.fe.jkcalendar.vo;

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

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
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
