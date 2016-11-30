package com.fe.jkcalendar.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * Created by chenpengfei on 2016/11/30.
 */
public class CalendarUtils {

    public static void showCalendar(final Activity activity, int year, final int month, final OnSelectDateListener onSelectDateListener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                onSelectDateListener.onSelectDate(year, monthOfYear, year + "-" + (month > 9 ? month : "0" + month));
            }
        }, year, month, month);
        ((ViewGroup) ((ViewGroup) datePickerDialog.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
        datePickerDialog.show();
    }

    public interface OnSelectDateListener {
        void onSelectDate(int year, int month, String yearMonth);
    }
}
