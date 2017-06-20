package com.hxsn.zzd.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 *  Created by jiely on 2016/5/3.
 */
public class NotificationService extends Service {

    private final IBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 将接收推送消息任务放入后台执行
        new ZeroMQMessageTask().execute();

        return Service.START_STICKY;

    }

    public class MyBinder extends Binder {
        public NotificationService getService() {
            return NotificationService.this;
        }
    }


    private class ZeroMQMessageTask extends AsyncTask<String, Void, String> {

        public ZeroMQMessageTask() {
        }

        @Override
        protected String doInBackground(String... params) {

            /*ZMQ.Context context = ZMQ.context(1);
            ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
            subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
            subscriber.connect("tcp://x.x.x.x:xxxx");
            while (true) {                  // 通过不终止的循环来保证接收消息
                message = subscriber.recvStr();
                if (!message.equals("0")) {  // 0是由我自己定义的空消息标识，可以替换成自定义的其它标识

                    // 显示推送消息
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

                    int icon = R.drawable.icon;
                    CharSequence tickerText = "Demo - " + message;
                    long when = System.currentTimeMillis();

                    Notification notification = new Notification(icon, tickerText, when);
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    Context uiContext = getApplicationContext();
                    CharSequence contentTitle = "Demo";
                    CharSequence contentText = message;
                    Intent notificationIntent = new Intent(uiContext, NotificationService.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(uiContext, 0, notificationIntent, 0);
                    notification.setLatestEventInfo(uiContext, contentTitle, contentText, contentIntent);

                    mNotificationManager.notify(1, notification);
                }
            }*/
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}
