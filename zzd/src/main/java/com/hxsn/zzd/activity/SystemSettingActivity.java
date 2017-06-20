package com.hxsn.zzd.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.hxsn.library.db.Shared;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.fragment.JavaScriptInterface;
import com.hxsn.zzd.utils.Const;

public class SystemSettingActivity extends Activity {

    private int shouldOverrideUrlLoadingCnt=0;
    private WebView webView;
    private ImageView imgLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);

        imgLeft = (ImageView)findViewById(R.id.img_left);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        webView = (WebView)findViewById(R.id.web_view);

        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        if (TApplication.intAndroidSDK >= 17) {
            webView.addJavascriptInterface(myJavaScriptInterface, "zzd");
        }

        webView.setVisibility(View.VISIBLE);
        webView.removeAllViews();
        webView.getSettings().setJavaScriptEnabled(true);
        MyWebViewClient webViewClient = new MyWebViewClient();
        webView.setWebViewClient(webViewClient);

        String urlString = Const.URL_SYSTEM_SETTING + TApplication.user.getUserId();
        webView.loadUrl(urlString);
    }

    @Override
    public void onBackPressed() {
        if(shouldOverrideUrlLoadingCnt == 0 ){
            super.onBackPressed();
        }else{
            if(shouldOverrideUrlLoadingCnt <= 0){
            }else {
                if(webView.canGoBack()) {
                    shouldOverrideUrlLoadingCnt--;
                    if(shouldOverrideUrlLoadingCnt<0){
                        if(shouldOverrideUrlLoadingCnt<-1){
                            super.onBackPressed();
                        }
                    }else if(shouldOverrideUrlLoadingCnt >= 0){
                        webView.goBack();//返回上一页面
                    }

                } else {
                    if(shouldOverrideUrlLoadingCnt<0){
                        super.onBackPressed();
                    }
                }
            }
        }

        Log.i("Home-onBackPressed","shouldOverrideUrlLoadingCnt="+shouldOverrideUrlLoadingCnt);
    }

    public class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            TApplication.webUrl = url;
            if(url.contains(Const.URL_CONTROL)){
                Shared.setValue("controlUrl",url);
            }
            Log.i("HomeActivity", "shouldOverrideUrlLoading-url="+url);
            imgLeft.setVisibility(View.VISIBLE);
            shouldOverrideUrlLoadingCnt++;
            Log.i("Home-Override", "shouldOverrideUrlLoadingCnt=" + shouldOverrideUrlLoadingCnt);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i("HomeActivity", "onPageStarted-url="+url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("HomeActivity", "onPageFinished-url="+url);
            TApplication.webUrl = url;
            if(url.contains(Const.URL_CONTROL)){
                Shared.setValue("controlUrl",url);
            }
        }
    }

}
