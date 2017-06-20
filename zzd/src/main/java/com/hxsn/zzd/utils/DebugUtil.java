package com.hxsn.zzd.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hxsn.zzd.TApplication;


/**
 * @desc: Created by jiely on 2015/11/6.
 */
public class DebugUtil {

    public static void show1(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();

    }

    public static void i(String tag, Object msg) {
        //1，在用户真机上运行，打到logcat没用，还占时间
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.i(tag, String.valueOf(msg));
        }

    }

    public static void d(String tag, Object msg) {
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.i(tag, String.valueOf(msg));
        }
    }

    public static void d(Object msg) {
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.i("zzd", String.valueOf(msg));
        }
    }

    public static void m(String tag, Object msg) {
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.d(tag, String.valueOf(msg));
        }
    }

    public static void e(String tag, String msg) {

        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.e(tag, String.valueOf(msg));
        }
    }
}
