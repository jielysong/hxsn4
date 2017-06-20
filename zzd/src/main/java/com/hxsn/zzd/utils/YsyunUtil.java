package com.hxsn.zzd.utils;

import com.hxsn.zzd.TApplication;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZGlobalSDK;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZAreaInfo;
import com.videogo.util.LogUtil;

import java.util.List;

/**
 * 萤石云 工具类
 * Created by jiely on 2017/1/5.
 */

public class YsyunUtil {


    /**
     * 处理token过期的错误
     *
     * @throws
     */
    public static void handleSessionException() {
        goToLoginAgain();
    }

    public static void goToLoginAgain() {
        if (EZGlobalSDK.class.isInstance(TApplication.getOpenSDK())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<EZAreaInfo> areaList = EZGlobalSDK.getInstance().getAreaList();
                        if (areaList != null) {
                            LogUtil.debugLog("application", "list count: " + areaList.size());

                            EZAreaInfo areaInfo = areaList.get(0);
                            EZGlobalSDK.getInstance().openLoginPage(areaInfo.getId());
                        }
                    } catch (BaseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            EZOpenSDK.getInstance().openLoginPage();
        }
    }

}
