package com.fe.jkcalendar.utils;

/**
 * Created by chenpengfei on 2016/11/30.
 */
public class StringUtils {

    /**
     * 两个字符串是否相同
     * @param oneStr
     * @param twoStr
     * @return
     */
    public static boolean isEquals(String oneStr, String twoStr) {
        if(oneStr == null && twoStr == null) return true;
        if(oneStr == null || twoStr == null) return false;
        return oneStr.equals(twoStr);
    }

    public static boolean isEmpty(String temp) {
        if(temp == null || temp == "" || temp == "null") {
            return true;
        }
        return false;
    }
}
