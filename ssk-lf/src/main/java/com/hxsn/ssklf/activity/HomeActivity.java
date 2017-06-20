package com.hxsn.ssklf.activity;


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

import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.ssklf.R;
import com.hxsn.ssklf.fragment.Mine5Fragment;
import com.hxsn.ssklf.fragment.Nshui2Fragment;
import com.hxsn.ssklf.fragment.PestFragment;
import com.hxsn.ssklf.fragment.Ssk1Fragment;
import com.hxsn.ssklf.utils.Const;

/**
 *  主界面
 */
public class HomeActivity extends Activity implements View.OnClickListener{

    private ImageView imgLeft;
    /**
     * 5个底部菜单按钮
     */
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    //标题栏
    private TextView txtTitle;//txtRight;
    private WebView webView;
    private FrameLayout frameLayout;
    //fragment 我的 随时看 农事汇 病虫害
    private Fragment mine5Fragment,sskFragment,nshui2Fragment,pestFragment;//ssk1Fragment,wen3Fragment, nqZhan4Fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    //底部菜单模式
    private int fragmentMode = 1;
    //webView的URL地址
    private String urlWebView;
    //webView的层级数
    private int shouldOverrideUrlLoadingCnt=0;
    //是否退出
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Log.i("HomeActivity", "onCreate");

        //初始化视图
        addView();
        //初始化Fragment
        addFragment();
        //初始化监听器
        addListener();

        //检查是否有新版本
        AndroidUtil.updateNowifiApp(this, Const.URL_UPDATE);
    }

    /**
     * 初始化Fragment
     */
    private void addFragment() {
        clearButton();
        fragmentMode = 1;
        webView.removeAllViews();
        webView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        // txtTitle.setText("随时看");
        rb1.setBackgroundResource(R.drawable.bottom1_s);

        nshui2Fragment = new Nshui2Fragment(this);
        mine5Fragment = new Mine5Fragment(this);
        pestFragment = new PestFragment();
        sskFragment = new Ssk1Fragment(this);

        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.framelayout_home, sskFragment);
        transaction.commit();
    }

    /**
     * /初始化监听器
     */
    private void addListener() {
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
        rb5.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
    }

    /**
     * 初始化视图
     */
    private void addView() {
        rb1 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_1);
        rb2 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_2);
        rb3 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_3);
        rb4 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_4);
        rb5 = (RadioButton) findViewById(R.id.in_bottom).findViewById(R.id.rb_5);

        frameLayout = (FrameLayout) findViewById(R.id.framelayout_home);
        webView = (WebView) findViewById(R.id.webView);
        rb1.setBackgroundResource(R.drawable.bottom1_s);

        imgLeft = (ImageView)findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(R.id.txt_middle);
        imgLeft.setVisibility(View.INVISIBLE);
    }


    /**
     * 初始化WebView
     */
    private void initWebView(){
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);
        webView.removeAllViews();
        MyWebViewClient webViewClient = new MyWebViewClient();
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(urlWebView);
    }

    @Override
    protected void onResume() {
        //Log.i("HomeActivity","onResume");
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left://点击返回图标
               onBackPressed();
                break;
            case R.id.rb_1://点击随时看按钮
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 1) {
                    clearButton();
                    imgLeft.setVisibility(View.INVISIBLE);
                    webView.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb1.setBackgroundResource(R.drawable.bottom1_s);
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout_home, sskFragment);
                    transaction.commit();
                    txtTitle.setText("随时看");
                    fragmentMode = 1;
                    //TApplication.fragmentMode = fragmentMode;
                }
                break;
            case R.id.rb_2://点击农情站按钮
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 2) {
                    imgLeft.setVisibility(View.VISIBLE);
                    clearButton();
                    fragmentMode = 2;
                    webView.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb2.setBackgroundResource(R.drawable.bottom2_s);
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout_home, nshui2Fragment);
                    transaction.commit();
                    txtTitle.setText("农事汇");
                }
                break;
            case R.id.rb_3://点击病虫害按钮
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 3) {
                    imgLeft.setVisibility(View.VISIBLE);
                    clearButton();
                    fragmentMode = 3;
                    webView.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb3.setBackgroundResource(R.drawable.bottom3_s);
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout_home,pestFragment );
                    transaction.commit();
                    txtTitle.setText("病虫害");
                }
                break;
            case R.id.rb_4://点击农事汇按钮
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 4) {
                    imgLeft.setVisibility(View.VISIBLE);
                    clearButton();
                    fragmentMode = 4;
                    frameLayout.setVisibility(View.GONE);
                    rb4.setBackgroundResource(R.drawable.bottom4_s);
                    urlWebView = Const.URL_NQZHAN_WEB;
                    //setWebView();
                    initWebView();
                    txtTitle.setText("农情站");
                }
                break;
            case R.id.rb_5://点击我的按钮
                shouldOverrideUrlLoadingCnt = 0;
                if (fragmentMode != 5) {
                    imgLeft.setVisibility(View.VISIBLE);
                    clearButton();
                    fragmentMode = 5;
                    webView.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb5.setBackgroundResource(R.drawable.bottom5_s);
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout_home, mine5Fragment);
                    transaction.commit();
                    txtTitle.setText("我的");
                }
                break;

        }
        Log.i("HomeActivity", "urlWebView=" + urlWebView);
    }

    /**
     * 清除视图到原始状态
     */
    private void clearButton() {
        rb1.setBackgroundResource(R.drawable.bottom1_n);
        rb2.setBackgroundResource(R.drawable.bottom2_n);
        rb3.setBackgroundResource(R.drawable.bottom3_n);
        rb4.setBackgroundResource(R.drawable.bottom4_n);
        rb5.setBackgroundResource(R.drawable.bottom5_n);
    }

    //退出APP
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

    //处理退出的消息
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("HomeActivity", "onKeyDown-shouldOverrideUrlLoadingCnt="+shouldOverrideUrlLoadingCnt);
        if (keyCode == KeyEvent.KEYCODE_BACK && shouldOverrideUrlLoadingCnt <= 0) {
            Log.i("HomeActivity", "onKeyDown-exit()");
            if (fragmentMode != 1) {//未进入webView的二级页面且不在随时看页面是则进入随时看页面
                clearButton();
                imgLeft.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                rb1.setBackgroundResource(R.drawable.bottom1_s);
                transaction = fm.beginTransaction();
                transaction.replace(R.id.framelayout_home, sskFragment);
                transaction.commit();
                txtTitle.setText("随时看");
                fragmentMode = 1;
            }else {//
                exit();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * webView的处理
     */
    public class MyWebViewClient extends WebViewClient {

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

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("HomeActivity", "onPageFinished-url="+url);
        }
    }

    @Override
    public void onBackPressed() {
        Log.i("onBackPressed", "canGoBack=" + webView.canGoBack()+",fragmentMode"+fragmentMode+",shouldOverrideUrlLoadingCnt="+shouldOverrideUrlLoadingCnt);
        if(shouldOverrideUrlLoadingCnt == 0){
            if (fragmentMode != 1) {//未进入webView的二级页面且不在随时看页面是则进入随时看页面
                clearButton();
                imgLeft.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                rb1.setBackgroundResource(R.drawable.bottom1_s);
                transaction = fm.beginTransaction();
                transaction.replace(R.id.framelayout_home, sskFragment);
                transaction.commit();
                txtTitle.setText("随时看");
                fragmentMode = 1;
                //TApplication.fragmentMode = fragmentMode;
            }else {//
                super.onBackPressed();
            }
        }else if(shouldOverrideUrlLoadingCnt > 0){//进入二级页面
            if(webView.canGoBack()) {//未返回到一级页面则webView的层级数－1
                //initMode();
                Log.i("HomeActivity", "未返回到一级页面则webView的层级数－1");
                shouldOverrideUrlLoadingCnt--;
                if(shouldOverrideUrlLoadingCnt<0){
                    initWebView();
                    if(shouldOverrideUrlLoadingCnt<-1){
                        super.onBackPressed();
                    }
                }else if(shouldOverrideUrlLoadingCnt >= 0){
                    webView.goBack();//返回上一页面
                }
            }
        }
    }
}
