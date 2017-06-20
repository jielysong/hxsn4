package com.hxsn.ssk.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.hxsn.ssk.base.BaseTitle;
import com.hxsn.ssk.fragment.JavaScriptInterface;
import com.hxsn.ssk.utils.Const;


public class SystemSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);

        BaseTitle.getInstance(this).setTitle("系统设置");
        final WebView webView = (WebView)findViewById(R.id.web_view);

        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        if (TApplication.intAndroidSDK >= 17) {
            webView.addJavascriptInterface(myJavaScriptInterface, "zzd");
        }

        webView.setVisibility(View.VISIBLE);
        webView.removeAllViews();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
               // Log.i("SystemSettingActivity", "onPageStarted-url="+url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("SystemSettingActivity", "onPageFinished-url="+url);
                //webView.loadUrl("javascript:appNoticeCallback(´hello´)");
               /* String message = "hello setting";
                webView.loadUrl("javascript:funFromjs('"+message+"')");*/
            }
        });
        //DebugUtil.i("HomeActivity","urlWebView="+urlWebView);
        //String urlString = "http://192.168.12.121:8480/aiot2/app/zzd/kongzhi.do?uid=40288cf955ed70810155edb0b60e0004&dyid=f39c0cf955fd2b8901560243c5b5001b";
        //String urlString = "file:///android_asset/new_file1.html";
        //String urlString = "http://192.168.12.121:8480/aiot2/new_file1.html";
        String urlString = Const.URL_SYSTEM_SETTING + TApplication.user.getUserId();
        webView.loadUrl(urlString);
    }

}
