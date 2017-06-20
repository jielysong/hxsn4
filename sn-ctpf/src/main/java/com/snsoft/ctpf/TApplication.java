package com.snsoft.ctpf;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.snsoft.ctpf.beans.CardInfo;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.beans.SysParam;
import com.snsoft.ctpf.db.Shared;
import com.snsoft.ctpf.util.Const;

import java.util.List;

/**
 * @ description Created by jiely on 2016/5/13.
 */
public class TApplication extends Application {

    public static int versionType = Const.DEBUG_VERTION;  //0-text 1-debug, 2-release
    public static Context context;
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
        mInstance = this;

        context = this;
        sysParam = Shared.getInstance(this).getSysParam();
        // 在使用 SDK 各组间之前初始化 context 信息，传入ApplicationContext
        SDKInitializer.initialize(this);
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
