package com.hxsn.ssk.utils;

import android.util.Log;

import com.hxsn.ssk.TApplication;

/**
 * @desc: Created by jiely on 2015/11/6.
 */
public class DebugUtil {

    public static void i(String tag, Object msg) {
        //1，在用户真机上运行，打到logcat没用，还占时间
        if (TApplication.versionType !=  Const.RELEASE_VERTION) {
            Log.i(tag, String.valueOf(msg));
        }

    }

    public static void d(String tag, Object msg) {
        if (TApplication.versionType !=  Const.RELEASE_VERTION) {
            Log.i(tag, String.valueOf(msg));
        }
    }

    public static void d(Object msg) {
        if (TApplication.versionType !=  Const.RELEASE_VERTION) {
            Log.i("ssk", String.valueOf(msg));
        }
    }

    public static void m(String tag, Object msg) {
        if (TApplication.versionType !=  Const.RELEASE_VERTION) {
            Log.d(tag, String.valueOf(msg));
        }
    }

    public static void e(String tag, String msg) {

        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.e(tag, String.valueOf(msg));
        }
    }
}
