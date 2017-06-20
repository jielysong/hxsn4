package com.snsoft.ctpf.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.hxsn.ssk.TApplication;


public class AndroidUtil {

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * desc: 获取当前版本
     * auther:jiely
     * create at 2015/11/24 10:00
     */
    public static String getThisAppVersion() {
        Context context = TApplication.context;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //dp与px转换的方法：
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getWidthPix(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取手机屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getHeightPix(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 打电话
     *
     * @param activity
     * @param phoneNumber
     */
    public static void callPhone(Activity activity, String phoneNumber) {
        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        activity.startActivity(phoneIntent);
    }

    /**
     * 获取android系统版本
     *
     * @return
     */
    public static int getAndroidOSVersion() {
        int osVersion;
        try {
            osVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            osVersion = 0;
        }

        return osVersion;
    }

    /**
     * desc:判断版本号是否是新的
     * auther:jiely
     * create at 2015/11/23 19:39
     */
    public static boolean isNewVersion(String newVersion, String oldVersion) {
        String[] olds = oldVersion.split("\\.");
        String[] news = newVersion.split("\\.");

        for (int i = 0; i < olds.length; i++) {
            Integer newv = Integer.parseInt(news[i]);
            Integer oldv = Integer.parseInt(olds[i]);
            DebugUtil.d("oldv=" + oldv);
            if (newv > oldv) {
                return true;
            } else if (newv < oldv) {
                return false;
            } else {
                if (i == olds.length - 1) {
                    return false;
                }
            }
        }
        return false;
    }



}
