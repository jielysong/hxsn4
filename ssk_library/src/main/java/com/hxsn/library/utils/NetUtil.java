package com.hxsn.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;


/**
 * desc:
 * auther:jiely
 * create at 2015/10/21 14:28
 */
public class NetUtil {

    /**
     * 判断是否是wifi连接
     * @param
     */
    public static boolean isWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return  wifiManager.isWifiEnabled();
    }

    /**
     * 检查网络是否可用
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean isConnect(Context context) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (localNetworkInfo != null) && (localNetworkInfo.isAvailable());
    }


    /**
     * 检测网络是否可用,如果不可用则弹出提示对话框
     * @return boolean
     */
    public static boolean checkNetwork(final Activity activity){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //网络不可用
        if(null == networkInfo || !networkInfo.isAvailable()){
            new AlertDialog.Builder(activity).setMessage("检查到没有可用的网络连接,请打开网络连接")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    }).show();
            return false;
        }
        return true;
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt 输入
     * @return ip形式
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context  上下文
     * @return 当前IP地址
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
    }

    /**
     * 关闭wifi
     */
    public static void closeWifi(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }

    }

    /**
     * 开启wifi
     */
    public static void openWifi(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
    }
}
