package com.hxsn.zzd.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hxsn.zzd.TApplication;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.EZAccessToken;
import com.videogo.util.Utils;

/**
 * 监听广播
 *
 * @author fangzhihua
 * @data 2013-1-17
 */
public class EzvizBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            EzvizAPI.getInstance().refreshNetwork();//268435456
        } else if (action.equals(Constant.ADD_DEVICE_SUCCESS_ACTION)) {
            String deviceId = intent.getStringExtra(IntentConsts.EXTRA_DEVICE_ID);
            Utils.showToast(context, "设备已添加");
        } else if (action.equals(Constant.OAUTH_SUCCESS_ACTION)) {
            Intent toIntent = new Intent(context, com.videogo.ui.cameralist.EZCameraListActivity.class);
            toIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            /*******   获取登录成功之后的EZAccessToken对象   *****/
            EZAccessToken token = TApplication.getOpenSDK().getEZAccessToken();
            context.startActivity(toIntent);
        }
    }
}
