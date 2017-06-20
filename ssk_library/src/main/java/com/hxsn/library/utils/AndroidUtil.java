package com.hxsn.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.hxsn.library.beans.AppVersion;
import com.hxsn.library.db.Shared;
import com.hxsn.library.http.HttpRequest;

import java.io.File;


public class AndroidUtil {

    private static String urlString;
    private static final String CODE_NSH_VERSION_KEY="nsh_version_key";
    public static final File SDCARD_ROOT = Environment.getExternalStorageDirectory();// .getAbsolutePath();
    private static final String PATH_APK = SDCARD_ROOT + "/apkDownload/";

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    /**
     * desc: APP版本升级
     * auther:jiely
     * create at 2015/11/4 11:06
     */
    public static void updateAPP(final Context context,String url) {
        if (NetUtil.isWifi(context)) {
            updateNowifiApp(context,url);
        }
    }


    /**
     *  没有wifi情况下也能升级
     * @param context 11
     * @param url 11
     */
    public static void updateNowifiApp(final Context context,String url) {
        new HttpRequest(context) {
            @Override
            public void getResponse(String response) {
                String jsonString = HttpRequest.result;
                AppVersion appVersion = JsonUtil.getAppVersion(jsonString);
                urlString = appVersion.getUrl();
                if (appVersion != null) {
                    String thisAppVersion = AndroidUtil.getThisAppVersion(context);
                    if (AndroidUtil.isNewVersion(appVersion.getVersion(), thisAppVersion)) {
                        new AlertDialog.Builder(context)
                                .setMessage("是否升级")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AndroidUtil.show(context, "APP在下载中，请稍等");
                                        try {
                                            new DownApkTask(context).execute(urlString);
                                        } catch (Exception e) {
                                            ExceptionUtil.handle(e);
                                        }
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                }
            }
        }.doGet(url);
    }


    public static void updateSskAPP(final Context context,String url) {
        if (NetUtil.isWifi(context)) {
            new HttpRequest(context) {
                @Override
                public void getResponse(String response) {
                    String jsonString = HttpRequest.result;
                    Log.i("AppBiz", "result=" + jsonString);
                    AppVersion appVersion = JsonUtil.getAppVersion(jsonString);
                    int nshVersionInt = appVersion.getNshversion();

                    //农事汇图标更新
                    //MyApplication.isUpdateNongshImage = false;
                    String nshVersionStr = Shared.getValue(CODE_NSH_VERSION_KEY);
                    if(nshVersionStr.length() == 0){
                        Shared.setValue(CODE_NSH_VERSION_KEY,String.valueOf(nshVersionStr));
                        //TApplication.isUpdateNongshImage = true;
                    }else{
                        int oldNshVersion = Integer.parseInt(Shared.getValue(CODE_NSH_VERSION_KEY));
                        if(nshVersionInt > oldNshVersion){
                            Shared.setValue(CODE_NSH_VERSION_KEY,String.valueOf(nshVersionStr));
                            //TApplication.isUpdateNongshImage = true;
                        }
                    }

                    urlString = appVersion.getUrl();
                    if (appVersion != null) {
                        String thisAppVersion = AndroidUtil.getThisAppVersion(context);
                        if (AndroidUtil.isNewVersion(appVersion.getVersion(), thisAppVersion)) {
                            new AlertDialog.Builder(context)
                                    .setMessage("是否升级")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AndroidUtil.show(context, "APP在下载中，请稍等");
                                            try {
                                                new DownApkTask(context).execute(urlString);
                                            } catch (Exception e) {
                                                ExceptionUtil.handle(e);
                                            }
                                        }
                                    }).setNegativeButton("取消", null).show();
                        }
                    }
                }
            }.doGet(url);
        }

    }

    /**
     * desc: 获取当前版本
     * auther:jiely
     * create at 2015/11/24 10:00
     */
    public static String getThisAppVersion(Context context) {

        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //dp与px转换的方法：
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getWidthPix(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取手机屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getHeightPix(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 打电话
     *
     * @param activity
     * @param phoneNumber
     */
    public static void callPhone(Activity activity, String phoneNumber) {
        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        activity.startActivity(phoneIntent);
    }

    /**
     * 获取android系统版本
     *
     * @return
     */
    public static int getAndroidOSVersion() {
        int osVersion;
        try {
            osVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            osVersion = 0;
        }

        return osVersion;
    }

    /**
     * desc:判断版本号是否是新的
     * auther:jiely
     * create at 2015/11/23 19:39
     */
    public static boolean isNewVersion(String newVersion, String oldVersion) {
        String[] olds = oldVersion.split("\\.");
        String[] news = newVersion.split("\\.");

        for (int i = 0; i < olds.length; i++) {
            Integer newv = Integer.parseInt(news[i]);
            Integer oldv = Integer.parseInt(olds[i]);

            if (newv > oldv) {
                return true;
            } else if (newv < oldv) {
                return false;
            } else {
                if (i == olds.length - 1) {
                    return false;
                }
            }
        }
        return false;
    }


    static class DownApkTask extends AsyncTask<String, Void, Integer> {
        private String absolutePath;
        private Context context;
        public DownApkTask(Context context){
            this.context = context;
        }

        @Override
        protected Integer doInBackground(String... params) {
            long appLength;
            try {
                Boolean isSuccess = MyFileUtil.deleteAllFile(PATH_APK);
                if (!isSuccess) {
                    return 600;
                }
                String fileName = MyFileUtil.getFileName(urlString);
                //MyFileUtil.deleteAndCreateFile(Const.UPDATE_APK_PATH, fileName);
                absolutePath = PATH_APK + fileName;
                appLength = DownloadUtil.downloadFileToLocal(urlString, absolutePath, null);//.downloadFileToLocal(urlString, absolutePath, null);
                if (appLength > 0) {
                    return 200;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
                return 500;
            }

            return 500;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result == 200) {
                // 下载完成，点击安装
                File file = new File(absolutePath);
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            } else if (result == 500) {
                AndroidUtil.show(context, "下载失败");
            } else {
                AndroidUtil.show(context, "文件错误");
            }
        }
    }

    public static final int  EXIT_APPLICATION = 0x0001;
    //完全退出应用
    public static void exit(Context mContext,Class mainActivity){

//      1.5 - 2.1之前下面两行是ok的,2.2之后就不行了，所以不通用
//      ActivityManager am = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
//      am.restartPackage("com.tutor.exit");

        Intent mIntent = new Intent();
        mIntent.setClass(mContext, mainActivity);
        //这里设置flag还是比较 重要的
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //发出退出程序指示
        mIntent.putExtra("flag", EXIT_APPLICATION);
        mContext.startActivity(mIntent);
    }
}
