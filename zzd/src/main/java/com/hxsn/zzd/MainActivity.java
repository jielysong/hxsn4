package com.hxsn.zzd;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.android.pushservice.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.hxsn.library.beans.User;
import com.hxsn.library.db.Shared;
import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.zzd.activity.HomeActivity;
import com.hxsn.zzd.activity.LoginActivity;
import com.hxsn.zzd.activity.WelcomeActivity;
import com.hxsn.zzd.utils.DebugUtil;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        int flag = getIntent().getIntExtra("flag", 0);
        if(flag == AndroidUtil.EXIT_APPLICATION){
            System.exit(0);
        }

        BasicPushNotificationBuilder bBuilder = new BasicPushNotificationBuilder();
        bBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        bBuilder.setStatusbarIcon(R.mipmap.icon_logo);
        //bBuilder.setNotificationVibrate(new long[]{500, 250, 250, 500, 500, 250, 250, 500, 500, 250, 250, 500});

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.windows);
        bBuilder.setNotificationSound(uri.toString());
        //设置手机震动
        long[] vibrates = {0, 1000, 1000, 1000};
        //notification.vibrate = vibrates;
        bBuilder.setNotificationVibrate(vibrates);
        PushManager.setNotificationBuilder(this, 1, bBuilder);

        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "8Zvu75UCbx7ZHgj78O38qgFa");
        Intent intent = new Intent();

      /*  intent.setClass(this, WelcomeActivity.class);
        startActivity(intent);
        finish();*/

        String value = Shared.getValue("welcome");

        if (TextUtils.isEmpty(value)) {
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
                    DebugUtil.d("跳转HomeActivity userid=" + user.getUserId());
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

    //这里用来接受退出程序的指令
    @Override
    protected void onStart() {

        super.onStart();
    }

}
