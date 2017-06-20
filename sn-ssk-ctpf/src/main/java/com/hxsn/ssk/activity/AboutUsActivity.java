package com.hxsn.ssk.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.hxsn.ssk.R;
import com.hxsn.ssk.base.BaseTitle;

public class AboutUsActivity extends Activity implements View.OnClickListener{

    private int mode = 0;
    ImageView imgssk1,imgssk2,imgssk3,imgnsh1,imgnsh2,imgnsh3,imgwen1,imgwen2,imgnqz,imgmine,imgsetting,imgmodify,imgBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        BaseTitle.getInstance(this).setTitle("帮助");

        /*WebView webView = (WebView) findViewById(R.id.webView);

        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(myJavaScriptInterface, "ssk");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(Const.URL_ABOUT_WEB);*/
        imgBig = (ImageView)findViewById(R.id.img_big);
        imgssk1 = (ImageView)findViewById(R.id.img_ssk1);
        //imgssk1.setOnClickListener(this);
        imgssk2 = (ImageView)findViewById(R.id.img_ssk2);
        //imgssk2.setOnClickListener(this);
        imgssk3 = (ImageView)findViewById(R.id.img_ssk3);
        //imgssk3.setOnClickListener(this);

        imgnsh1 = (ImageView)findViewById(R.id.img_nsh1);
        //imgnsh1.setOnClickListener(this);
        imgnsh2 = (ImageView)findViewById(R.id.img_nsh2);
        //imgnsh2.setOnClickListener(this);
        imgnsh3 = (ImageView)findViewById(R.id.img_nsh3);
        //imgnsh3.setOnClickListener(this);

        imgwen1 = (ImageView)findViewById(R.id.img_wen1);
        //imgwen1.setOnClickListener(this);
        imgwen2 = (ImageView)findViewById(R.id.img_wen2);
        //imgwen2.setOnClickListener(this);

        imgnqz = (ImageView)findViewById(R.id.img_nqz);
        //imgnqz.setOnClickListener(this);

        imgmine = (ImageView)findViewById(R.id.img_mine);
        //imgmine.setOnClickListener(this);
        imgsetting = (ImageView)findViewById(R.id.img_setting);
        //imgsetting.setOnClickListener(this);
        imgmodify = (ImageView)findViewById(R.id.img_modify);
        //imgmodify.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        Drawable drawable;
        switch (v.getId()){
            case R.id.img_ssk1:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.ssk1);
                imgBig.setBackgroundDrawable(drawable);
                break;
            case R.id.img_ssk2:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.ssk2);
                imgBig.setBackgroundDrawable(drawable);
                break;
            case R.id.img_ssk3:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.ssk3);
                imgBig.setBackgroundDrawable(drawable);
                break;

            case R.id.img_nsh1:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.nsh);
                imgBig.setBackgroundDrawable(drawable);
                break;
            case R.id.img_nsh2:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.nsh1);
                imgBig.setBackgroundDrawable(drawable);
                break;
            case R.id.img_nsh3:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.nsh2);
                imgBig.setBackgroundDrawable(drawable);
                break;

            case R.id.img_wen1:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.wen);
                imgBig.setBackgroundDrawable(drawable);
                break;
            case R.id.img_wen2:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.tiwen);
                imgBig.setBackgroundDrawable(drawable);
                break;

            case R.id.img_nqz:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.nqz);
                imgBig.setBackgroundDrawable(drawable);
                break;

            case R.id.img_mine:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.mine);
                imgBig.setBackgroundDrawable(drawable);
                break;
            case R.id.img_setting:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.setting);
                imgBig.setBackgroundDrawable(drawable);
                break;
            case R.id.img_modify:
                mode++;
                imgBig.setVisibility(View.VISIBLE);
                drawable  = ContextCompat.getDrawable(this, R.mipmap.modifykey);
                imgBig.setBackgroundDrawable(drawable);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mode>0){
            imgBig.setVisibility(View.GONE);
            mode--;
        }else {
            super.onBackPressed();
        }
    }
}
