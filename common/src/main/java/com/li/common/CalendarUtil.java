package com.li.common;


import java.util.Calendar;

/**
 * Created by liweifa on 2016/10/8.
 */

public class CalendarUtil {
    private static final int ONE_DAY = 1;
    private static final int ONE_WEEK = 7;
    private static final int MONDAY = 0;
    private static final int TUESDAY = 1;
    private static final int WEDNESDAY = 2;
    private static final int THURSDAY = 3;
    private static final int FRIDAY = 4;
    private static final int SATURDAY = 5;
    private static final int SUNDAY = 6;
    private static final int DEFAULT_DATE_OF_WEEK = MONDAY;

    private static CalendarUtil calendarUtil;


    private CalendarUtil() {

    }

    public static CalendarUtil getInstance() {
        if (calendarUtil == null) {
            calendarUtil = new CalendarUtil();
        }
        return calendarUtil;
    }

    public Date[] getCurrentWeek(Date currentDate) {
        Date[] week;
        week = new Date[ONE_WEEK];
        Calendar calendar = Calendar.getInstance();
        calendar.set(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate());

        /**
         * 因为 Calendar获取当前日期所对应的星期时是从星期天开始排序的
         * 如果当天刚好是周日
         * 获取的一周开始日期是下星期的所以要再减去七天
         */
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -CalendarUtil.ONE_WEEK);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -(calendar.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY));


        for (int i = 0; i < ONE_WEEK; i++) {
            week[i] = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), i);
            calendar.add(Calendar.DAY_OF_MONTH, ONE_DAY);
        }
        return week;
    }

    public int getDayOfWeek(Calendar calendar) {
        int dayOfWeek = MONDAY;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                dayOfWeek = MONDAY;
                break;
            case Calendar.TUESDAY:
                dayOfWeek = TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                dayOfWeek = THURSDAY;
                break;
            case Calendar.FRIDAY:
                dayOfWeek = FRIDAY;
                break;
            case Calendar.SATURDAY:
                dayOfWeek = SATURDAY;
                break;
            case Calendar.SUNDAY:
                dayOfWeek = SUNDAY;
                break;
            default:
                dayOfWeek = MONDAY;
                break;


        }
        return dayOfWeek;

    }


}
