package com.wgaham.secretsharing;

import java.util.Calendar;

/**
 * 一些判断函数
 *
 * @author Wgaham
 *         Created by zh on 2018/3/13.
 */

public class Tool {
    public static final int MINUTE = 60;

    public static boolean rebirthTimeJudgment(String startTime, String endTime) {
        // 检测目前的时间是否符合重构时间

        int startMinutes, endMinutes, nowMinutes, startHour, endHour, startMinute, endMinute, nowHour, nowMinute;
        if (startTime.equals(endTime)) {
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.AM_PM) == 0) {
            nowHour = calendar.get(Calendar.HOUR);
        } else {
            nowHour = calendar.get(Calendar.HOUR) + 12;
        }

        nowMinute = calendar.get(Calendar.MINUTE);
        nowMinutes = nowHour * MINUTE + nowMinute;
        String start[] = startTime.split(":");
        startHour = Integer.getInteger(start[0]);
        startMinute = Integer.getInteger(start[1]);
        String end[] = endTime.split(":");
        endHour = Integer.getInteger(end[0]);
        endMinute = Integer.getInteger(end[1]);
        startMinutes = startHour * MINUTE + startMinute;
        endMinutes = endHour * MINUTE + endMinute;
        return startMinutes <= nowMinutes && nowMinutes < endMinutes;
    }
}
