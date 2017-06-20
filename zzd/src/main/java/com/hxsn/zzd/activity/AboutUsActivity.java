package com.hxsn.zzd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxsn.zzd.R;
import com.hxsn.zzd.base.BaseTitle;
import com.hxsn.zzd.fragment.JavaScriptInterface;
import com.hxsn.zzd.utils.Const;
import com.hxsn.zzd.utils.DebugUtil;


public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        BaseTitle.getInstance(this).setTitle("关于我们");

        WebView webView = (WebView) findViewById(R.id.webView);

        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(myJavaScriptInterface, "zzd");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        DebugUtil.i("AboutUsActivity","webViewUrl = "+Const.URL_ABOUT_WEB);
        webView.loadUrl(Const.URL_ABOUT_WEB);

    }

}
