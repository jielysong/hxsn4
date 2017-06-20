package com.hxsn.zzd.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.videogo.openapi.EzvizAPI;
import com.videogo.widget.TitleBar;
import com.videogo.widget.WebViewEx;

public class TestActivity extends Activity {

    WebViewEx webViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isValue();
        initView();
    }

    private boolean isValue() {
        EzvizAPI ezvizAPI = EzvizAPI.getInstance();
        RotateAnimation animation = new RotateAnimation(0.0F, 720.0F, 1, 0.5F, 1, 0.5F);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1200L);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(1);
        return true;
    }

    private void initView(){
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(1);
        relativeLayout.setBackgroundColor(Color.rgb(240, 240, 243));
        FrameLayout.LayoutParams layoutParamsFrame = new FrameLayout.LayoutParams(-1, -1);
        this.addContentView(relativeLayout, layoutParamsFrame);
        TitleBar titleBar = new TitleBar(this);
        titleBar.setId(2);
        RelativeLayout.LayoutParams titleBarLp = new RelativeLayout.LayoutParams(-1, -2);
        relativeLayout.addView(titleBar, titleBarLp);
        webViewEx = new WebViewEx(this);
        webViewEx.setId(3);
        RelativeLayout.LayoutParams webViewLp = new RelativeLayout.LayoutParams(-1, -1);
        webViewLp.addRule(3, 2);
        relativeLayout.addView(webViewEx, webViewLp);
    }

    private void setView() {
        webViewEx.getSettings().setJavaScriptEnabled(true);
        webViewEx.getSettings().setBuiltInZoomControls(true);
        webViewEx.getSettings().setSupportZoom(true);
        webViewEx.addJavascriptInterface(this, "deviceOperate");
        //webViewEx.setWebViewClient(new LoginTestActivity.MyWebViewClient());
        //webViewEx.setDownloadListener(new LoginTestActivity.MyDownloadListener());
        //this.setMode(false);
    }
}
