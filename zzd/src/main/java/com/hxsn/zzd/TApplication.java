package com.hxsn.zzd;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.hxsn.library.Memory;
import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.library.beans.User;
import com.hxsn.library.db.Shared;
import com.hxsn.library.utils.VolleyUtil;
import com.hxsn.zzd.utils.Const;
import com.videogo.openapi.EZOpenSDK;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Administrator on 16-4-7.
 */
public class TApplication extends Application {
    public static int versionType = Const.RELEASE_VERTION;  //0-text 1-debug, 2-release
    public static String URL_ZZD_STRING,URL_CMS_STRING;//农事汇url,在另一个IP下

    public static Context context;
    public static Resources resources;
    public static int intAndroidSDK = android.os.Build.VERSION.SDK_INT;
    public static User user;

    public static List<Activity> activities;
    public static NotifyInfo notifyInfo;
    public static boolean isNotify = false;                     //判断有百度透传消息
    public static String newsUrl = "";                          //农事汇或咨询的url
    public static String webUrl="";                                //当前web的地址
    public static Handler.Callback baiduNotifyCallback;         //百度推送的回调消息

    public static int mode = 1;

    private String AppKey = "896e18cccd614e9ab54daf169e9e7348";

    @Override
    public void onCreate() {
        super.onCreate();
        initSDK();
        context = getApplicationContext();
        resources = getResources();

        VolleyUtil.initialize(this);
        Memory.requestQueue = VolleyUtil.getRequestQueue();
        Shared.init(getApplicationContext(),"zzd_dic");
        activities = new ArrayList<>();
        switch (versionType) {
            case Const.TEST_VERTION:
                URL_ZZD_STRING = "http://192.168.12.121:8480/aiot2/app/";
                URL_CMS_STRING = "http://192.168.12.121:8280/zzdcms/app/";
                break;
            case Const.TEST_VERTION1:
                URL_ZZD_STRING = "http://115.28.140.121:8480/aiot2/app/";
                URL_CMS_STRING = "http://115.28.140.121:8280/zzdcms/app/";
                break;
            case Const.DEBUG_VERTION:
                URL_ZZD_STRING = "http://192.168.12.121:8480/aiot2/app/";
                URL_CMS_STRING = "http://192.168.12.121:8280/zzdcms/app/";
                break;
            case Const.RELEASE_VERTION:
                URL_ZZD_STRING = "http://115.28.140.121:8480/aiot2/app/";
                URL_CMS_STRING = "http://115.28.140.121:8280/zzdcms/app/";
                break;
        }
    }

    /**
     * 萤石云视频监控初始化
     */
    private void initSDK() {
        /**********国内版本初始化EZOpenSDK**************/
        {
            /**
             * sdk日志开关，正式发布需要设置未false
             */
            EZOpenSDK.showSDKLog(false);

            /**
             * 设置是否支持P2P取流,详见api
             */
            EZOpenSDK.enableP2P(true);

            /**
             * APP_KEY请替换成自己申请的
             */
            EZOpenSDK.initLib(this, AppKey, "");
        }
    }

    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }
}
