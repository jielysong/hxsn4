package com.snsoft.ctpf.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hxsn.ssk.TApplication;


/**
 * @desc: Created by jiely on 2015/11/6.
 */
public class DebugUtil {

    private static final String TAG = "CTPF";

    public static void show(Context context, String text) {
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

    public static void i(Object msg) {
        //1，在用户真机上运行，打到logcat没用，还占时间
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.i(TAG, String.valueOf(msg));
        }

    }

    public static void d( Object msg) {
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.i(TAG, String.valueOf(msg));
        }
    }

    public static void m( Object msg) {
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.d(TAG, String.valueOf(msg));
        }
    }

    public static void e(String msg) {

        if (TApplication.versionType != Const.RELEASE_VERTION) {
            Log.e(TAG, String.valueOf(msg));
        }
    }
}
