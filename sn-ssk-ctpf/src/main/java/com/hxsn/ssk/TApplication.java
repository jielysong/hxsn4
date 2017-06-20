package com.hxsn.ssk;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;

import com.baidu.mapapi.SDKInitializer;
import com.hxsn.library.Memory;
import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.library.beans.User;
import com.hxsn.library.db.Shared;
import com.hxsn.library.utils.VolleyUtil;
import com.hxsn.ssk.utils.Const;
import com.snsoft.ctpf.beans.CardInfo;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.beans.SysParam;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by jiely on 2016/6/28.
 */
public class TApplication extends Application{
    public static int versionType = Const.RELEASE_VERTION;  //0-text 1-debug, 2-release
    public static Context context;
    public static Resources resources;
    public static List<Activity> activities;
    public static String URL_STRING,URL_NONGSH_STRING;//农事汇url,在另一个IP下
    public static String URL_UPDATE;//升级的地址有外网和内网
    public static int intAndroidSDK = android.os.Build.VERSION.SDK_INT;
    public static User user;
    public static NotifyInfo notifyInfo;
    public static boolean isUpdateNongshImage= false;//判断是否需要更新图标,默认不需要更新，进入APP首先在APPBiz中判断是否更新
    public static String newsUrl = "";//农事汇或咨询的url

    private static TApplication mInstance;
    public static List<Fragment> fragmentList;
    public static String city;//地区名称
    public static SoilInfo soilInfo;
    public static CardInfo cardInfo;
    public static TApplication getInstance() {
        return mInstance;
    }
    public static SysParam sysParam;  //系统配置信息

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        resources = getResources();

        Shared.init(getApplicationContext(),"ssk_ctpf");

        //后台接口，Volley初始化
        VolleyUtil.initialize(this);
        Memory.requestQueue = VolleyUtil.getRequestQueue();

        activities = new ArrayList<>();

        mInstance = this;

        context = this;
        sysParam = com.snsoft.ctpf.db.Shared.getInstance(this).getSysParam();
        // 在使用 SDK 各组间之前初始化 context 信息，传入ApplicationContext
        SDKInitializer.initialize(this);

        switch (versionType) {
            case Const.TEST_VERTION:
                URL_STRING = "http://192.168.12.94:7990/ssk/sskapp/";
                URL_UPDATE = URL_STRING + "appi/appversion";
                URL_NONGSH_STRING = "http://192.168.12.26:80/sskcms";
                break;
            case Const.TEST_VERTION1:
                URL_STRING = "http://60.10.151.28:7990/ssk/sskapp/";
                URL_UPDATE = URL_STRING + "app/appversion";
                URL_NONGSH_STRING = "http://60.10.151.28:8980/sskcms";
                break;
            case Const.DEBUG_VERTION:
                URL_STRING = "http://192.168.12.94:7990/ssk/sskapp/";
                URL_UPDATE = URL_STRING + "appi/appversion";
                URL_NONGSH_STRING = "http://192.168.12.94:8980/sskcms";
                break;
            case Const.RELEASE_VERTION:
                URL_STRING = "http://60.10.151.28:7990/ssk/sskapp/";
                URL_UPDATE = URL_STRING + "app/appversion";
                URL_NONGSH_STRING = "http://60.10.151.28:8980/sskcms";
                break;
        }
    }

    /**
     * 通过fragment的类名获取Fragment
     * @param fragmentName fragment的类名
     * @return Fragment
     */
    public static Fragment getFragmentFromName(String fragmentName){
        for (Fragment fragment:fragmentList) {
            String name = fragment.getClass().getSimpleName();
            if(name.equals(fragmentName)){
                return fragment;
            }
        }
        return null;
    }
}
