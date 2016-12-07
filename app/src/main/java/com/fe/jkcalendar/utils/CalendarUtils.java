package com.fe.jkcalendar.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;

import com.fe.jkcalendar.R;
import com.fe.jkcalendar.widget.CalendarView;

/**
 * Created by chenpengfei on 2016/11/30.
 */
public class CalendarUtils {

    public static void showCalendar(final Activity activity, int year, int month, int day, final OnSelectDateListener onSelectDateListener) {
        final Dialog dialog = new Dialog(activity, R.style.dialog);
        View calendarContentView = View.inflate(activity, R.layout.activity_calendar, null);
        CalendarView calendarView = (CalendarView) calendarContentView.findViewById(R.id.calendar_view);
        calendarView.setDate(year, month, day);
        dialog.setContentView(calendarContentView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        Window localWindow = dialog.getWindow();
        localWindow.setGravity(Gravity.CENTER);
        localWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        calendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                if(onSelectDateListener != null) {
                    onSelectDateListener.onSelectDate(year, month, day, year + "-" + month);
                }
                dialog.dismiss();
            }
        });
    }

    public interface OnSelectDateListener {
        void onSelectDate(int year, int month, int day, String yearMonth);
    }
}
