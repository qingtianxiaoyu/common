package com.li.common;


import java.util.Calendar;

/**
 * Created by liweifa on 2016/10/8.
 */

public class Date {
    private int mMonth;
    private int mYear;
    private int mDate;
    private int mDate0fWeek;

    public Date() {
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date schedulingDate = (Date) obj;
            if (mMonth == schedulingDate.mMonth && mDate == schedulingDate.getDate() && mMonth == schedulingDate.getMonth()) {
                return true;
            }
        }
        return super.equals(obj);
    }

    public Date(int year, int month, int date, int dateOfWeek) {
        mMonth = month;
        mYear = year;
        mDate = date;
        mDate0fWeek = dateOfWeek;
    }

    public Date(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        mMonth = month;
        mYear = year;
        mDate = date;
        mDate0fWeek = CalendarUtil.getInstance().getDayOfWeek(calendar);
    }


    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    public int getDate() {
        return mDate;
    }

    public void setDate(int mDate) {
        this.mDate = mDate;
    }

    public void setDateOfWeek(int dateOfWeek) {
        mDate0fWeek = dateOfWeek;
    }

    public int getDateOfWeek() {
        return mDate0fWeek;
    }


    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", mYear, mMonth + 1, mDate);
    }
}