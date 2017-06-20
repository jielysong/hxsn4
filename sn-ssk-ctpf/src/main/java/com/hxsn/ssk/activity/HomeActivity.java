package com.hxsn.ssk.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hxsn.library.db.Shared;
import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.hxsn.ssk.fragment.JavaScriptInterface;
import com.hxsn.ssk.fragment.Mine5Fragment;
import com.hxsn.ssk.fragment.Nshui2Fragment;
import com.hxsn.ssk.utils.Const;
import com.snsoft.ctpf.activity.CtpfHomeActivity;
import com.snsoft.ctpf.db.DbManager;


/**
 * 主页
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    private ImageView imgLeft;
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    private TextView txtTitle,txtRight;
    private WebView webView;
    private FrameLayout frameLayout;
    private Fragment mine5Fragment,  njhui2Fragment;//ssk1Fragment,wen3Fragment, nqZhan4Fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private int fragmentMode = 1;
    private String urlWebView;
    private int shouldOverrideUrlLoadingCnt=0;

    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i("HomeActivity", "onCreate");
        //TApplication.activities.add(this);

        addView();

        initWebView();
        addFragment();
        addListener();

        //检查是否有新版本
       // AndroidUtil.updateSskAPP(this, TApplication.URL_UPDATE);
    }


    private void addFragment() {
        //ssk1Fragment = new Ssk1Fragment(this);
        njhui2Fragment = new Nshui2Fragment(this);
        //wen3Fragment = new Wen3Fragment(this);
        //nqZhan4Fragment = new Nqzhan4Fragment(this);
        mine5Fragment = new Mine5Fragment(this);

        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.framelayout, njhui2Fragment);
        transaction.commit();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        clearButton();
        webView.removeAllViews();
        fragmentMode = 1;
        webView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        txtTitle.setText("随时看");
        rb1.setBackgroundResource(R.drawable.bottom1_s);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        if (TApplication.intAndroidSDK >= 17) {
            webView.addJavascriptInterface(myJavaScriptInterface, "ssk");
        }

        urlWebView = Const.URL_SSK_WEB;
        if(TApplication.user == null){
            TApplication.user = Shared.getUser();
            if(TApplication.user != null){
                urlWebView += TApplication.user.getUserId();
            }
        }else{
            urlWebView += TApplication.user.getUserId();
        }

        setWebView();
    }

    private void addListener() {
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
        rb5.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        txtRight.setOnClickListener(this);
    }

    private void addView() {
        imgLeft = (ImageView)findViewById(R.id.img_left);
        imgLeft.setVisibility(View.INVISIBLE);
        rb1 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_1);
        rb2 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_2);
        rb3 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_3);
        rb4 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_4);
        rb5 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_5);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtRight = (TextView)findViewById(R.id.txt_right);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        webView = (WebView) findViewById(R.id.webView);
        rb1.setBackgroundResource(R.drawable.bottom1_s);
    }

    //设置webv
    private void setWebView() {
        webView.setVisibility(View.VISIBLE);
        webView.removeAllViews();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
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
        });
        webView.loadUrl(urlWebView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_1:
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 1) {
                    clearButton();
                    txtRight.setVisibility(View.VISIBLE);
                    txtRight.setText("视频监控");
                    frameLayout.setVisibility(View.GONE);
                    rb1.setBackgroundResource(R.drawable.bottom1_s);
                    fragmentMode = 1;
                    urlWebView = Const.URL_SSK_WEB+ TApplication.user.getUserId();
                    setWebView();
                    txtTitle.setText("随时看");
                }
                break;
            case R.id.rb_2:
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 2) {
                    clearButton();
                    txtRight.setVisibility(View.INVISIBLE);
                    fragmentMode = 2;
                    webView.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb2.setBackgroundResource(R.drawable.bottom2_s);
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout, njhui2Fragment);
                    transaction.commit();
                    txtTitle.setText("农事汇");
                }
                break;
            case R.id.rb_3:
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 3) {
                    clearButton();
                    txtRight.setVisibility(View.VISIBLE);
                    txtRight.setText("提问题");
                    fragmentMode = 3;
                    frameLayout.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb3.setBackgroundResource(R.drawable.bottom3_s);
                    Log.i("HomeActivity","TApplication.user.getUserId="+ TApplication.user);
                    urlWebView = Const.URL_WNN_LIST+ TApplication.user.getUserId();
                    setWebView();
                    txtTitle.setText("问专家");
                }
                break;
            case R.id.rb_4:
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 4) {
                    clearButton();
                    txtRight.setVisibility(View.INVISIBLE);
                    fragmentMode = 4;
                    frameLayout.setVisibility(View.GONE);
                    rb4.setBackgroundResource(R.drawable.bottom4_s);
                    urlWebView = Const.URL_NQZHAN_WEB+ TApplication.user.getUserId();
                    setWebView();
                    txtTitle.setText("农情站");
                }
                break;
            case R.id.rb_5:
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 5) {
                    clearButton();
                    fragmentMode = 5;
                    txtRight.setVisibility(View.INVISIBLE);
                    webView.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb5.setBackgroundResource(R.drawable.bottom5_s);
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout, mine5Fragment);
                    transaction.commit();
                    txtTitle.setText("我的");
                }
                break;
            case R.id.img_left:
                onBackPressed();
                break;
            case R.id.txt_right:
                Intent intent = new Intent();
                if(fragmentMode == 1){
                    String value  = com.snsoft.ctpf.db.Shared.getInstance(this).getValue("welcome_ctpf");//TApplication.getValue("welcome");
                    if (value.length() == 0) {//首次安装打开
                        DbManager.getInstance().initdb(this); //初始化数据库
                        com.snsoft.ctpf.db.Shared.getInstance(this).setValue("welcome_ctpf", "welcome_ctpf");
                    }
                    intent.setClass(this, CtpfHomeActivity.class);
                }else if(fragmentMode == 3){
                    intent.setClass(this,SendQuestActivity.class);
                }

                startActivity(intent);
                break;
        }
        Log.i("HomeActivity", "urlWebView=" + urlWebView);
    }

    private void clearButton() {
        rb1.setBackgroundResource(R.drawable.bottom1_n);
        rb2.setBackgroundResource(R.drawable.bottom2_n);
        rb3.setBackgroundResource(R.drawable.bottom3_n);
        rb4.setBackgroundResource(R.drawable.bottom4_n);
        rb5.setBackgroundResource(R.drawable.bottom5_n);
       // txtRight.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onRestart() {
        Log.i("HomeActivity","onRestart");
        if(rb1==null || rb2==null || rb3==null || rb4==null || rb5==null || txtRight==null){
            return;
        }
        switch (fragmentMode){
            case 1:
                txtRight.setVisibility(View.VISIBLE);
                rb1.setBackgroundResource(R.drawable.bottom1_s);
                break;
            case 2:
                txtRight.setVisibility(View.INVISIBLE);
                rb2.setBackgroundResource(R.drawable.bottom2_s);
                break;
            case 3:
                rb3.setBackgroundResource(R.drawable.bottom3_s);
                txtRight.setVisibility(View.VISIBLE);
                break;
            case 4:
                txtRight.setVisibility(View.INVISIBLE);
                rb4.setBackgroundResource(R.drawable.bottom4_s);
                break;
            case 5:
                txtRight.setVisibility(View.INVISIBLE);
                rb5.setBackgroundResource(R.drawable.bottom5_s);
                break;
        }
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        Log.i("onBackPressed", "canGoBack=" + webView.canGoBack()+",fragmentMode="
                +fragmentMode+",shouldOverrideUrlLoadingCnt="+shouldOverrideUrlLoadingCnt);
        if(shouldOverrideUrlLoadingCnt == 0 && fragmentMode == 1){
            super.onBackPressed();
        }else{
            if(shouldOverrideUrlLoadingCnt <= 0){
                imgLeft.setVisibility(View.INVISIBLE);
                initWebView();
            }else {
                if(webView.canGoBack()) {
                    shouldOverrideUrlLoadingCnt--;
                    if(shouldOverrideUrlLoadingCnt<0){
                        initWebView();
                        if(shouldOverrideUrlLoadingCnt<-1){
                            super.onBackPressed();
                        }
                    }else if(shouldOverrideUrlLoadingCnt >= 0){
                        webView.goBack();//返回上一页面
                    }

                } else {
                    imgLeft.setVisibility(View.INVISIBLE);
                    initWebView();
                    if(fragmentMode == 1 || shouldOverrideUrlLoadingCnt<0){
                        super.onBackPressed();
                    }
                }
            }
        }

        if(shouldOverrideUrlLoadingCnt <= 0){
            imgLeft.setVisibility(View.INVISIBLE);
        }

        Log.i("Home-onBackPressed","shouldOverrideUrlLoadingCnt="+shouldOverrideUrlLoadingCnt);
    }

    public void exit(){

        if (!isExit) {
            isExit = true;
            AndroidUtil.show(getApplicationContext(), "再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && shouldOverrideUrlLoadingCnt <= 0) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
