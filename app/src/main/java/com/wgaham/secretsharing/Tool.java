package com.wgaham.secretsharing;

import java.util.Calendar;

/**
 * 一些判断函数
 *
 * @author Wgaham
 *         Created by zh on 2018/3/13.
 */

public class Tool {

    public static boolean rebirthTimeJudgment(String startTime, String endTime) {
        /**
         * 夹击妹抖略略略
         */
        int startHour, endHour, startMinute, endMinute, hour, minute;
        if (startTime.equals(endTime)) {
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.AM_PM) == 0) {
            hour = calendar.get(Calendar.HOUR);
        } else {
            hour = calendar.get(Calendar.HOUR) + 12;
        }
        minute = calendar.get(Calendar.MINUTE);
    }
}
