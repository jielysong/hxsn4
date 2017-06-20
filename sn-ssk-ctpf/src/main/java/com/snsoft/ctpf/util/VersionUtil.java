package com.snsoft.ctpf.util;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import com.hxsn.ssk.TApplication;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by jiely on 2016/6/23.
 */
public class VersionUtil {

    //private static final String SERVICE_URL = "http://192.168.12.94:8998";
    private static final String VERSION_PATH = "/download/version.xml";                //数据更新地址
    private static final String APK_PATH = "/download/sn-ctpf-phone-1.0-android.apk";
    private static final String DB_PATH = "/download/sn_ctpf_client.sqlite3";


    /**
     * 从服务器获取数据库版本号或APK版本号
     * @param url 数据服务器地址
     * @param name  版本名称 "version":apk版本号   "dataVersion":数据库版本号
     * @return int
     */
    public static Integer getVersionForUrl(String url,String name){
        if(url.length() == 0){
            return null;
        }
        url = url+VERSION_PATH;
        NLCallable mCallable = new NLCallable(url,name);
        FutureTask<Integer> task = new FutureTask<>(mCallable);
        new Thread(task).start();
        Integer version = 0;
        try {
            version = task.get();
        } catch (Exception e) {
            Log.i("", "服务器异常，e=" + e);
            e.printStackTrace();
            version = null;
        }
        return version;
    }

    /**
     * 获取下载数据库的地址如 http://192.168.12.94:8998/download/sn-ctpf-phone-1.0-android.apk
     * @param context d
     * @return url
     */
    public static String getDbUrl(Context context){
        String url = TApplication.sysParam.getUrl();
        if(url.length() == 0){
            AndroidUtil.show(context, "请设置数据库地址");
            return "";
        }
        return url+DB_PATH;
    }


    /**
     * 获取下载apk的地址如 http://192.168.12.94:8998/download/sn_ctpf_client.sqlite3
     * @param context d
     * @return url
     */
    public static String getApkUrl(Context context){
        String url = TApplication.sysParam.getUrl();
        if(url.length() == 0){
            AndroidUtil.show(context, "请设置数据库地址");
            return "";
        }
        return url+APK_PATH;
    }

    private static class NLCallable implements Callable<Integer> {
        private String url;
        private String name;
        public NLCallable(String url,String name){
            this.url = url;
            this.name = name;
        }

        @Override
        public Integer call() throws Exception {
            URL url = new URL(this.url);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            InputStream is =conn.getInputStream();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "utf-8");
            int type = parser.getEventType();
            int version = 0;
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if (name.equals(parser.getName())) {
                            version = Integer.parseInt(parser.nextText());
                        }
                        break;
                }
                type = parser.next();
            }
            return version;
        }
    }


}
