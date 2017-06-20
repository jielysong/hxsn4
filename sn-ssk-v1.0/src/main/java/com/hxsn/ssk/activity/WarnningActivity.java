package com.hxsn.ssk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.ssk.MainActivity;
import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.hxsn.ssk.base.BaseTitle;
import com.hxsn.ssk.utils.Const;


public class WarnningActivity extends Activity {

    private WebView webView;
    private NotifyInfo notifyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warnning);
        webView = (WebView) findViewById(R.id.webView);
        notifyInfo = TApplication.notifyInfo;
        if(notifyInfo != null){
            BaseTitle.getInstance(this).setTitle(notifyInfo.getTitle());
            setWebView();
        }else {
            BaseTitle.getInstance(this).setTitle("告警信息");
        }
    }

    //设置webv
    private void setWebView() {
        webView.setVisibility(View.VISIBLE);
        webView.removeAllViews();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        String url = Const.URL_WARN_WEB+"&msgid="+notifyInfo.getId();
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
