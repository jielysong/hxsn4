package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.zzd.MainActivity;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.base.BaseTitle;
import com.hxsn.zzd.utils.Const;
import com.hxsn.zzd.utils.DebugUtil;


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
        DebugUtil.i("WarnningActivity","url = "+url);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
        //AndroidUtil.clearActivitys();
        super.onBackPressed();
    }
}
