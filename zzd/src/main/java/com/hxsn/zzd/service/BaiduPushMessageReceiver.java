package com.hxsn.zzd.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.library.db.Shared;
import com.hxsn.library.utils.JsonUtil;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.activity.HomeActivity;
import com.hxsn.zzd.activity.NewsActivity;
import com.hxsn.zzd.activity.WarnningActivity;
import com.hxsn.zzd.utils.Const;

import java.util.List;

/**
 *  Created by jiely on 2016/5/5.
 */
public class BaiduPushMessageReceiver extends PushMessageReceiver {
    
    final static String Tgg1 = "111---baiduPush";
    
    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {

        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;

        String code = Shared.getValue(Const.CODE_CHANNEL_ID);
        if(channelId != null && !channelId.equals(code)){
            Shared.setValue(Const.CODE_CHANNEL_ID,channelId);
        }

        if(errorCode == 0){
            //绑定成功
        }
        Log.i(Tgg1, "onBind-responseString=" + responseString);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        Log.i(Tgg1, "onUnbind-responseString=" + s);
    }

    /**
     * setTags() 的回调函数。
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags
     *            设置成功的tag
     * @param failTags
     *            设置失败的tag
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode, List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " sucessTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.i(Tgg1, "onSetTags-" + responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        //updateContent(context, responseString);
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.i(Tgg1, "onDelTags-"+s);
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        Log.i(Tgg1, "onListTags-"+s);
    }

    /**
     * 接收透传消息的函数。  接到通知后点击
     * @param context
     *            上下文
     * @param message
     *            推送的消息
     * @param customContentString
     *            自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message, String customContentString) {
        String messageString = "透传消息 message=\"" + message + "\" customContentString=" + customContentString;
        Log.i(Tgg1, "onMessage-" + messageString);
         NotificationManager mNotificationManager;
         NotificationCompat.Builder mBuilder;

        TApplication.isNotify = true;
        Log.i(Tgg1, "TApplication.isNotify="+TApplication.isNotify);

        if( TApplication.webUrl.contains(Const.URL_CONTROL)){//当前页面是否是控制页面
            TApplication.isNotify = true;
            Log.i(Tgg1, "TApplication.isNotify="+TApplication.isNotify);
            Log.i(Tgg1, "------收到百度推送并在控制页面------------");
            Handler handler = new Handler(TApplication.baiduNotifyCallback);
            Message msg = handler.obtainMessage();
            msg.what = 11;
            Bundle bundle = new Bundle();
            bundle.putString("message",message);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }else{
            String url = Shared.getValue("controlUrl");
            Log.i(Tgg1, "url="+url);
            if(TextUtils.isEmpty(url)){//如果从未进入过控制页面则不发送通知
                return;
            }
            Log.i(Tgg1, "------收到百度推送这时不在控制页面------------");
            TApplication.isNotify = true;
            Log.i(Tgg1, "TApplication.isNotify="+TApplication.isNotify);
            mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("设备状态改变")
                    .setContentText(message)
                    .setContentIntent(showIntentActivityNotify(context)) //点击的意图ACTION是跳转到Intent
                    .setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                    .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                   // .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                    .setSmallIcon(R.mipmap.icon_logo);

            mNotificationManager.notify(100, mBuilder.build());
        }

       /* Intent intent = new Intent();
        intent.setClass(context, HomeActivity.class);
        intent.putExtra("onNotifyMessage",message);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
    }

    /** 显示通知栏点击跳转到指定Activity */
    public PendingIntent showIntentActivityNotify(Context context){
        //点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(context, HomeActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


    /**
     * 接收通知点击的函数。
     * @param context
     *            上下文
     * @param title
     *            推送的通知的标题
     * @param description
     *            推送的通知的描述
     * @param customContentString
     *            自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        String notifyString = "通知点击 title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.i(Tgg1, "onNotificationClicked-notifyString=" + notifyString);
        NotifyInfo notifyInfo = getNotifyInfo(description,title,customContentString);
        TApplication.notifyInfo = notifyInfo;

        if(notifyInfo != null){
            Intent intent = new Intent();
            switch (notifyInfo.getType()){
                case 1:
                    intent.setClass(context.getApplicationContext(), NewsActivity.class);
                    TApplication.newsUrl = Const.URL_NEWS_WEB+"&nqzuuid="+notifyInfo.getId();
                    break;
                case 2:
                    intent.setClass(context.getApplicationContext(), WarnningActivity.class);
                    break;
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }
    }

    /**
     * 接收通知到达的函数。
     * @param context
     *            上下文
     * @param title
     *            推送的通知的标题
     * @param description
     *            推送的通知的描述
     * @param customContentString
     *            自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {

        String notifyString = "onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.i(Tgg1, "onNotificationArrived-notifyString=" + notifyString);

        // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
        TApplication.notifyInfo = getNotifyInfo(title, description, customContentString);
    }

    private NotifyInfo getNotifyInfo(String title, String description, String customContentString){
        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setTitle(title);
        notifyInfo.setDescription(description);

        NotifyInfo notifyInfo2 = JsonUtil.getNotifyInfo(customContentString);
        if(notifyInfo2 != null){
            notifyInfo.setId(notifyInfo2.getId());
            notifyInfo.setType(notifyInfo2.getType());
        }

        return  notifyInfo;
    }
}
