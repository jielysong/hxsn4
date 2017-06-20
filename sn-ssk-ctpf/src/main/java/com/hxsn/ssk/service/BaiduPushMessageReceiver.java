package com.hxsn.ssk.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.library.db.Shared;
import com.hxsn.library.utils.JsonUtil;
import com.hxsn.ssk.TApplication;
import com.hxsn.ssk.activity.NewsActivity;
import com.hxsn.ssk.activity.WarnningActivity;
import com.hxsn.ssk.utils.Const;

import java.util.List;

/**
 * Created by jiely on 2016/5/5.
 */
public class BaiduPushMessageReceiver extends PushMessageReceiver {
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
        Log.i(TAG, "onBind-responseString=" + responseString);

    }



    @Override
    public void onUnbind(Context context, int i, String s) {
        Log.i(TAG, "onUnbind-responseString=" + s);
    }

    /**
     * setTags() 的回调函数。
     *
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
        Log.i(TAG, "onSetTags-" + responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        //updateContent(context, responseString);
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.i(TAG, "onDelTags-"+s);
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        Log.i(TAG, "onListTags-"+s);
    }


    /**
     * 接收透传消息的函数。  接到通知后点击
     *
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
        Log.i(TAG, "onMessage-" + messageString);
    }

    /**
     * 接收通知点击的函数。
     *
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
        Log.i(TAG, "onNotificationClicked-notifyString=" + notifyString);

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
     *
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
        Log.i(TAG, "onNotificationArrived-notifyString=" + notifyString);

        // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
        TApplication.notifyInfo = getNotifyInfo(title, description, customContentString);
    }


    private NotifyInfo getNotifyInfo(String title, String description, String customContentString){
        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setTitle(title);
        notifyInfo.setDescription(description);
        NotifyInfo notifyInfo2 = JsonUtil.getNotifyInfo(customContentString);
        notifyInfo.setId(notifyInfo2.getId());
        notifyInfo.setType(notifyInfo2.getType());


        return  notifyInfo;
    }
}
