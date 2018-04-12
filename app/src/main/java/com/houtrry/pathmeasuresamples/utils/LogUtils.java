package com.houtrry.pathmeasuresamples.utils;

import android.util.Log;

/**
 * @author: houtrry
 * @time: 2018/4/12
 * @desc: ${TODO}
 */
public class LogUtils {

    private static boolean sIsShowLog = true;

    public static void isShowLog(boolean sIsShowLog) {
        LogUtils.sIsShowLog = sIsShowLog;
    }

    public static void d(String message) {
        if (sIsShowLog) {
            Log.d(Thread.currentThread().getName(), message);
        }
    }

    public static void e(String message) {
        if (sIsShowLog) {
            Log.e(Thread.currentThread().getName(), message);
        }
    }

}
