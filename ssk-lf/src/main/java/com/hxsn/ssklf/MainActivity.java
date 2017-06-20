package com.hxsn.ssklf;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.ssklf.activity.HomeActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主Activity  loading页，5秒自动进入主页，点击按钮进入主页
 */
public class MainActivity extends Activity {

    TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String time1 = "12月29日 16:57";
        String realTime = "12月29日 17:47";
        boolean is = Tools.isRealTime1(time1,realTime);
        Log.i("MainActivity","is="+is);

        time1 = "12月29日 16:57";
        realTime = "12月20日 17:47";
        is = Tools.isRealTime1(time1,realTime);
        Log.i("MainActivity","is="+is);

        time1 = "12月29日 23:00";
        realTime = "12月30日 00:47";
        is = Tools.isRealTime1(time1,realTime);
        Log.i("MainActivity","is="+is);*/

        /**
         * 时间定时任务
         */
        task = new TimerTask(){
            public void run(){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };

        delay(5000);

        TextView txtTest = (TextView)findViewById(R.id.txt_test);
        TextView txtComeIn = (TextView)findViewById(R.id.txt_come_in);

        txtComeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                task.cancel();
                finish();
            }
        });

        txtTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStartApplicationWithPackageName("com.videogo");

            }
        });

        TextView txtTest1 = (TextView)findViewById(R.id.txt_test1);
        txtTest1.setVisibility(View.INVISIBLE);
        txtTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
               // intent.setClass(MainActivity.this, RtChartsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 启动延时任务
     * @param time 毫秒
     */
    private void delay(int time){
        Timer timer = new Timer();
        timer.schedule(task, time);
    }


    /**
     * 打开另一个APP
     * @param packagename 另一个APP的包名
     */
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等

        String webUrl = "http://m.appchina.com/app/com.videogo?channel=qr-code";

        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MainActivity","没有萤石云视频");
        }
        if (packageinfo == null) {
            AndroidUtil.show(this,"请安装萤石云视频");

            RelativeLayout rlMain = (RelativeLayout)findViewById(R.id.rl_main);
            rlMain.setVisibility(View.GONE);
            WebView webView = (WebView)findViewById(R.id.webView);

            webView.setVisibility(View.VISIBLE);
            webView.removeAllViews();
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    Log.i("HomeActivity", "shouldOverrideUrlLoading-url="+url);
                    // imgLeft.setVisibility(View.VISIBLE);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    Log.i("HomeActivity", "onPageStarted-url="+url);
                    super.onPageStarted(view, url, favicon);
                }
            });
            webView.loadUrl(webUrl);
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }
}
