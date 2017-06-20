package com.hxsn.zzd.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
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
import com.hxsn.library.http.HttpRequest;
import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.fragment.JavaScriptInterface;
import com.hxsn.zzd.fragment.Mine5Fragment;
import com.hxsn.zzd.utils.Const;

import java.util.List;


/**
 * 主页
 */
@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
public class HomeActivity extends Activity implements View.OnClickListener {
    private ImageView imgLeft;
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    private TextView txtTitle,txtRight;
    private WebView webView;
    private FrameLayout frameLayout;
    private Fragment mine5Fragment;// njhui2Fragment;//ssk1Fragment,wen3Fragment, nqZhan4Fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;
   // private int fragmentMode = 1;
    private String urlWebView;
    private int shouldOverrideUrlLoadingCnt=0;
    private boolean isExit = false;
    //private boolean isNotify = false;

    //private String message;//从百度推送获取的码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addView();

        Log.i("HomeActivity","onCreate");
        TApplication.baiduNotifyCallback = new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == 11){
                    Log.i("HomeActivity", "---------当前页面是控制页面且收到百度推送TApplication.webUrl="+TApplication.webUrl);
                    Bundle bundle = msg.getData();
                    String message = bundle.getString("message");
                    urlWebView = TApplication.webUrl;
                    webView.loadUrl("javascript:funFromjs('"+message+"')");
                }
                return false;
            }
        };

        //initWebView();
        addFragment();
        addListener();

        refreshMode(TApplication.mode);

        //TApplication.mode = 1;
        //检查是否有新版本
        AndroidUtil.updateNowifiApp(this,Const.URL_UPDATE);

        //获取用户未读警情信息数量
        requestReadWarning();

    }



    private void requestReadWarning() {
        String url = Const.URL_WARN_READ + TApplication.user.getUserId();
        new HttpRequest(this) {
            @Override
            public void getResponse(String response) {
                ImageView imgPoint = (ImageView)findViewById(R.id.img_red_point);
                Log.i("HomeActivity","获取用户未读警情信息数量="+HttpRequest.result);
                if(!HttpRequest.result.equals("0")){//没有未读预警
                    imgPoint.setVisibility(View.VISIBLE);
                }else {
                    imgPoint.setVisibility(View.GONE);
                }
            }
        }.doGet(url);
    }

    private void addFragment() {
        mine5Fragment = new Mine5Fragment(this);
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.framelayout, mine5Fragment);
        transaction.commit();
    }


    private void initWebView() {
        clearButton();
        //fragmentMode = 1;
        //webView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        txtTitle.setText("早知道");
        rb1.setBackgroundResource(R.drawable.bottom1_s);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        if (TApplication.intAndroidSDK >= 17) {
            webView.addJavascriptInterface(myJavaScriptInterface, "zzd");
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

        //txtRight.setVisibility(View.VISIBLE);
        //txtRight.setText("看视频");
    }



    //设置webv
    private void setWebView() {
        webView.setVisibility(View.VISIBLE);
        webView.removeAllViews();
        webView.getSettings().setJavaScriptEnabled(true);
        MyWebViewClient webViewClient = new MyWebViewClient();
        webView.setWebViewClient(webViewClient);
        Log.i("HomeActivity-setWebView","urlWebView="+urlWebView);
        webView.loadUrl(urlWebView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_1:
                shouldOverrideUrlLoadingCnt = 0;
                if (TApplication.mode != 1) {
                    clearButton();
                    txtRight.setVisibility(View.VISIBLE);
                    txtRight.setText("视频监控");
                    frameLayout.setVisibility(View.GONE);
                    rb1.setBackgroundResource(R.drawable.bottom1_s);
                    TApplication.mode = 1;
                    urlWebView = Const.URL_ZZD_WEB+ TApplication.user.getUserId();
                    setWebView();
                    txtTitle.setText("早知道");
                    Log.i("HomeActivity", "早知道-urlWebView=" + urlWebView);
                }
                break;
            case R.id.rb_2:
                shouldOverrideUrlLoadingCnt = 0;
                if (TApplication.mode != 2) {
                    clearButton();
                    txtRight.setVisibility(View.INVISIBLE);
                    TApplication.mode = 2;
                    frameLayout.setVisibility(View.GONE);
                    rb2.setBackgroundResource(R.drawable.bottom2_s);
                    urlWebView = Const.URL_NSH_WEB+TApplication.user.getUserName();//"http://192.168.12.121:8280/zzdcms/app/nsh.do?chid=40288c9a55ec488c0155ec4d86720000";
                    setWebView();
                    txtTitle.setText("农事汇");
                    Log.i("HomeActivity", "农事汇-urlWebView=" + urlWebView);
                }
                break;
            case R.id.rb_3:
                shouldOverrideUrlLoadingCnt = 0;
                if (TApplication.mode != 3) {
                    clearButton();
                    txtRight.setVisibility(View.VISIBLE);
                    txtRight.setText("提问题");
                    TApplication.mode = 3;
                    frameLayout.setVisibility(View.GONE);
                    rb3.setBackgroundResource(R.drawable.bottom3_s);
                    urlWebView = Const.URL_NSH_WEN+TApplication.user.getUserId();//"http://192.168.12.121:8280/zzdcms/app/wzj.do?userid="+ TApplication.user.getId();//Const.URL_WNN_LIST+ TApplication.user.getId();
                    setWebView();
                    txtTitle.setText("问专家");
                    Log.i("HomeActivity", "问专家-urlWebView=" + urlWebView);
                }
                break;
            case R.id.rb_4:
                shouldOverrideUrlLoadingCnt = 0;
                if (TApplication.mode != 4) {
                    clearButton();
                    txtRight.setVisibility(View.INVISIBLE);
                    TApplication.mode = 4;
                    frameLayout.setVisibility(View.GONE);
                    rb4.setBackgroundResource(R.drawable.bottom4_s);
                    urlWebView = Const.URL_NQZHAN_WEB+TApplication.user.getUserName();//"http://192.168.12.121:8280/zzdcms/app/nsh.do?chid=40288c9a55ec67500155ec79ebcd0008";
                    setWebView();
                    txtTitle.setText("农情站");
                    Log.i("HomeActivity", "农情站-urlWebView=" + urlWebView);
                }
                break;
            case R.id.rb_5:
                shouldOverrideUrlLoadingCnt = 0;
                if (TApplication.mode != 5) {
                    clearButton();
                    txtRight.setVisibility(View.INVISIBLE);
                    TApplication.mode = 5;
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
                if(TApplication.mode == 1){

                    //YsyunUtil.goToLoginAgain();
                    intent = new Intent(this, LoginTestActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//"com.videogo.EXTRA_WEBVIEW_ACTION"
                    //intent.putExtra(IntentConsts.EXTRA_WEBVIEW_ACTION, EzvizWebViewActivity.WEBVIEW_ACTION_CLOUDPAGE);
                    intent.setFlags(268435456);
                    intent.putExtra("Param", -1);
                    intent.putExtra("com.videogo.EXTRA_WEBVIEW_ACTION", 0);
                    startActivity(intent);


                 /*   intent = new Intent();
                    intent.setAction("com.videogo.action.OAUTH_SUCCESS_ACTION");
                    this.sendBroadcast(intent);
                    this.finish();*/

                }else  if(TApplication.mode == 3){
                    intent.setClass(this,SendQuestActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void clearButton() {
        rb1.setBackgroundResource(R.drawable.bottom1_n);
        rb2.setBackgroundResource(R.drawable.bottom2_n);
        rb3.setBackgroundResource(R.drawable.bottom3_n);
        rb4.setBackgroundResource(R.drawable.bottom4_n);
        rb5.setBackgroundResource(R.drawable.bottom5_n);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("HomeActivity", "onResume-TApplication.isNotify="+TApplication.isNotify);
        if( TApplication.isNotify){//点击通知栏进入
            urlWebView = Shared.getValue("controlUrl");
            TApplication.isNotify = false;
        }else {
            urlWebView = Const.URL_ZZD_WEB + TApplication.user.getUserId();
        }
        Log.i("HomeActivity", "onResume-urlWebView="+urlWebView);
        if(TApplication.mode == 1){
            initWebView();
        }

    }

    @Override
    protected void onRestart() {
        Log.i("HomeActivity","onRestart-TApplication.mode="+TApplication.mode);
        super.onRestart();

        refreshMode(TApplication.mode);
    }

    @Override
    public void onStart() {
        Log.i("HomeActivity","onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Log.i("onBackPressed", "canGoBack=" + webView.canGoBack()+",TApplication.mode="
                +TApplication.mode+",shouldOverrideUrlLoadingCnt="+shouldOverrideUrlLoadingCnt);
        if(shouldOverrideUrlLoadingCnt == 0 && TApplication.mode == 1){
            imgLeft.setVisibility(View.INVISIBLE);
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
                    if(TApplication.mode == 1 || shouldOverrideUrlLoadingCnt<0){
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



    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等

        String webUrl = "http://m.appchina.com/app/com.videogo?channel=qr-code";

        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MainActivity","没有萤石云视频");
        }
        if (packageinfo == null) {
            AndroidUtil.show(this,"请安装萤石云视频");

            //RelativeLayout rlMain = (RelativeLayout)findViewById(R.id.rl_main);
            //rlMain.setVisibility(View.GONE);
            WebView webView = (WebView)findViewById(R.id.webView);

            webView.setVisibility(View.VISIBLE);
            webView.removeAllViews();
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    Log.i("HomeActivity", "shouldOverrideUrlLoading-url="+url);
                    // imgLeft.setVisibility(View.VISIBLE);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    Log.i("HomeActivity", "onPageStarted-url="+url);
                    super.onPageStarted(view, url, favicon);
                }
            });
            webView.loadUrl(webUrl);
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    private void refreshMode(int mode){
        switch (mode) {
            case 1:
                    clearButton();
                    txtRight.setVisibility(View.VISIBLE);
                    txtRight.setText("视频监控");
                    frameLayout.setVisibility(View.GONE);
                    rb1.setBackgroundResource(R.drawable.bottom1_s);
                    urlWebView = Const.URL_ZZD_WEB+ TApplication.user.getUserId();
                    setWebView();
                    txtTitle.setText("早知道");
                break;
            case 2:
                    clearButton();
                    frameLayout.setVisibility(View.GONE);
                    rb2.setBackgroundResource(R.drawable.bottom2_s);
                    urlWebView = Const.URL_NSH_WEB+ TApplication.user.getUserId();//"http://192.168.12.121:8280/zzdcms/app/nsh.do?chid=40288c9a55ec488c0155ec4d86720000";
                    setWebView();
                    txtTitle.setText("农事汇");
                break;
            case 3:
                    clearButton();
                    txtRight.setVisibility(View.VISIBLE);
                    txtRight.setText("提问题");
                    frameLayout.setVisibility(View.GONE);
                    rb3.setBackgroundResource(R.drawable.bottom3_s);
                    urlWebView = Const.URL_NSH_WEN+TApplication.user.getUserId();//"http://192.168.12.121:8280/zzdcms/app/wzj.do?userid="+ TApplication.user.getId();//Const.URL_WNN_LIST+ TApplication.user.getId();
                    setWebView();
                    txtTitle.setText("问专家");
                break;
            case 4:
                    clearButton();
                    frameLayout.setVisibility(View.GONE);
                    rb4.setBackgroundResource(R.drawable.bottom4_s);
                    urlWebView = Const.URL_NQZHAN_WEB+TApplication.user.getUserName();//"http://192.168.12.121:8280/zzdcms/app/nsh.do?chid=40288c9a55ec67500155ec79ebcd0008";
                    setWebView();
                    txtTitle.setText("农情站");
                    Log.i("HomeActivity", "农情站-urlWebView=" + urlWebView);
                break;
            case 5:
                    clearButton();
                    webView.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    rb5.setBackgroundResource(R.drawable.bottom5_s);
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout, mine5Fragment);
                    transaction.commit();
                    txtTitle.setText("我的");
                break;
        }
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
