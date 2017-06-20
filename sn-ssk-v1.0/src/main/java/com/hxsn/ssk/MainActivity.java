package com.hxsn.ssk;


import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.baidu.android.pushservice.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.hxsn.library.beans.User;
import com.hxsn.library.db.Shared;
import com.hxsn.ssk.activity.HomeActivity;
import com.hxsn.ssk.activity.LoginActivity;
import com.hxsn.ssk.activity.WelcomeActivity;
import com.hxsn.ssk.utils.DebugUtil;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        BasicPushNotificationBuilder bBuilder = new BasicPushNotificationBuilder();
        bBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        bBuilder.setStatusbarIcon(R.mipmap.icon_logo);
        //bBuilder.setNotificationVibrate(new long[]{500, 250, 250, 500, 500, 250, 250, 500, 500, 250, 250, 500});
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.alarm);
        bBuilder.setNotificationSound(uri.toString());

        //设置手机震动
     //   //第一个，0表示手机静止的时长，第二个，1000表示手机震动的时长
        //   //第三个，1000表示手机震动的时长，第四个，1000表示手机震动的时长
        //   //此处表示手机先震动1秒，然后静止1秒，然后再震动1秒
        long[] vibrates = {0, 1000, 1000, 1000};
      //notification.vibrate = vibrates;
        bBuilder.setNotificationVibrate(vibrates);
        //PushManager.setDefaultNotificationBuilder(this, bBuilder);
        PushManager.setNotificationBuilder(this, 1, bBuilder);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "jAvXSGRoxPzaUBSwaMSNG3gm");
        Intent intent = new Intent();
        String value = Shared.getValue("welcome");

        if (value.length() == 0) {
            Shared.setValue("welcome", "welcome");
            intent.setClass(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (TApplication.user == null) {
                TApplication.user = Shared.getUser();
                User user = TApplication.user;
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    DebugUtil.d("跳转HomeActivity userid="+user.getUserId());
                    intent.setClass(this, HomeActivity.class);
                }
            } else {
                intent.setClass(this, HomeActivity.class);
                startActivity(intent);
            }
            startActivity(intent);
            finish();
        }
    }
}
