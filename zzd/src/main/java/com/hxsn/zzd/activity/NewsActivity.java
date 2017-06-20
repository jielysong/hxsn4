package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.fragment.JavaScriptInterface;
import com.hxsn.zzd.utils.Const;
import com.hxsn.zzd.utils.DebugUtil;


public class NewsActivity extends Activity {

    private WebView webView;
    private NotifyInfo notifyInfo;
    private String id;
    private TextView txtTitle;
    private ImageView imgLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        DebugUtil.d("-------------------NewsActivity-------------------");
        addView();
        Intent intent = getIntent();
        String url;
        id = intent.getStringExtra("id");
        if(id != null && id.length() != 0){//从农事汇进入的
            String name = intent.getStringExtra("name");

            txtTitle.setText(name);
            url = Const.URL_NSH_WEB+ TApplication.user.getUserId();
        }else {//从通知栏进入的
            notifyInfo = TApplication.notifyInfo;
            txtTitle.setText("农情站");//notifyInfo.getTitle()
            url = Const.URL_NEWS_WEB+notifyInfo.getId();
        }
        setWebView(url);

        DebugUtil.d("NewsActivity","url="+url);

    }

    private void addView() {
        webView = (WebView) findViewById(R.id.webView);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgLeft = (ImageView) findViewById(R.id.img_left);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //设置webv
    private void setWebView(String url1) {
        try{
            WebSettings wSet = webView.getSettings();
            wSet.setJavaScriptEnabled(true);
            final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
            if (TApplication.intAndroidSDK >= 17) {
                webView.addJavascriptInterface(myJavaScriptInterface, "zzd");
            }
            webView.removeAllViews();
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

            webView.loadUrl(url1);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if(id == null || id.length() == 0){//从通知栏进入的
            notifyInfo = null;
            Intent intent = new Intent();
            intent.setClass(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            if(webView.canGoBack()) {
                webView.goBack();//返回上一页面
            } else {
                super.onBackPressed();
            }
        }
    }

}
