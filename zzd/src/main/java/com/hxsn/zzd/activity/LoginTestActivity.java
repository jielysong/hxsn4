package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.videogo.camera.CameraInfoEx;
import com.videogo.camera.CameraManager;
import com.videogo.cameramgt.CameraMgtCtrl;
import com.videogo.constant.Config;
import com.videogo.device.DeviceInfoEx;
import com.videogo.device.DeviceManager;
import com.videogo.devicemgt.storage.Storage;
import com.videogo.devicemgt.storage.StorageCtrl;
import com.videogo.deviceupgrade.DeviceUpgradeCtrl;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.req.AddDeviceInfo;
import com.videogo.openapi.bean.req.BaseDeviceInfo;
import com.videogo.openapi.model.req.AddDeviceReq;
import com.videogo.openapi.model.req.SetDeviceReq;
import com.videogo.openapi.model.req.WebLoginReq;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.DevPwdUtil;
import com.videogo.util.HttpUtils;
import com.videogo.util.LocalInfo;
import com.videogo.util.LogUtil;
import com.videogo.util.Utils;
import com.videogo.widget.TitleBar;
import com.videogo.widget.WebViewEx;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginTestActivity extends Activity {

    private static String TAG = "LoginTestActivity";

    private RelativeLayout relativeLayout;
    private TitleBar titleBar;
    private WebViewEx webViewEx = null;
    private ImageView imageView;

    private int mode = 0;
    private Animation animation;
    private String webUrl = null;
    private EzvizAPI ezvizAPI = null;
    private CameraInfoEx cameraInfoEx = null;
    private DeviceInfoEx deviceInfoEx = null;
    private List<Storage> storageList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.isValue()) {
            this.initView();
            this.addListener();
            this.setView();
        }
    }

    private boolean isValue() {
        this.mode = this.getIntent().getIntExtra("com.videogo.EXTRA_WEBVIEW_ACTION", 0);
        if (this.mode != 0 || TextUtils.isEmpty(LocalInfo.getInstance().getAccessToken()) || EzvizAPI.platformType == EZConstants.EZPlatformType.EZPlatformTypeGLOBALSDK && TextUtils.isEmpty(LocalInfo.getInstance().getAreaDomain())) {
            this.ezvizAPI = EzvizAPI.getInstance();
            this.animation = new RotateAnimation(0.0F, 720.0F, 1, 0.5F, 1, 0.5F);
            this.animation.setInterpolator(new LinearInterpolator());
            this.animation.setDuration(1200L);
            this.animation.setRepeatCount(-1);
            this.animation.setRepeatMode(1);
            return true;
        } else {
            Intent intent = new Intent();
            intent.setAction("com.videogo.action.OAUTH_SUCCESS_ACTION");
            this.sendBroadcast(intent);
            LogUtil.debugLog(TAG, "sendBroadcast:com.videogo.action.OAUTH_SUCCESS_ACTION");
            this.finish();
            return false;
        }
    }

    private void initView() {
        this.relativeLayout = new RelativeLayout(this);
        this.relativeLayout.setId(1);
        this.relativeLayout.setBackgroundColor(Color.rgb(240, 240, 243));
        FrameLayout.LayoutParams layoutParamsFrame = new FrameLayout.LayoutParams(-1, -1);
        this.addContentView(this.relativeLayout, layoutParamsFrame);
        this.titleBar = new TitleBar(this);
        this.titleBar.setId(2);
        RelativeLayout.LayoutParams titleBarLp = new RelativeLayout.LayoutParams(-1, -2);
        this.relativeLayout.addView(this.titleBar, titleBarLp);
        this.webViewEx = new WebViewEx(this);
        this.webViewEx.setId(3);
        RelativeLayout.LayoutParams webViewLp = new RelativeLayout.LayoutParams(-1, -1);
        webViewLp.addRule(3, 2);
        this.relativeLayout.addView(this.webViewEx, webViewLp);
    }

    private void addListener() {
        boolean showBackButton = Config.SHOW_WEBVIEW_BACK_BUTTON;
        if (showBackButton) {
            this.titleBar.addBackButton(new View.OnClickListener() {
                public void onClick(View v) {
                    if (LoginTestActivity.this.webViewEx.canGoBack()) {
                        LoginTestActivity.this.webViewEx.goBack();
                    } else {
                        LoginTestActivity.this.onBackPressed();
                    }

                }
            });
        }

        this.imageView = this.titleBar.addRightProgress();
        this.imageView.setId(4);
        this.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (LoginTestActivity.this.imageView.getAnimation() == null) {
                    LoginTestActivity.this.setMode(true);
                }

            }
        });
    }

    private void setView() {
        this.webViewEx.getSettings().setJavaScriptEnabled(true);
        this.webViewEx.getSettings().setBuiltInZoomControls(true);
        this.webViewEx.getSettings().setSupportZoom(true);
        this.webViewEx.addJavascriptInterface(this, "deviceOperate");
        this.webViewEx.setWebViewClient(new MyWebViewClient());
        this.webViewEx.setDownloadListener(new MyDownloadListener());
        this.setMode(false);
    }

    private void setMode(boolean reload) {
        String deviceSerial = null;
        ArrayList nvp = null;
        Intent intent = this.getIntent();
        List nvp1;
        switch (this.mode) {
            case 0:
                if (!reload) {
                    long addDeviceReq1 = System.currentTimeMillis();
                    this.webUrl = this.ezvizAPI.getOpenWebUrl() + "/oauth/authorize" + WebLoginReq.getReqData();
                    int setDeviceReq1 = this.getIntent().getIntExtra("Param", -1);
                    if (setDeviceReq1 >= 0) {
                        this.webUrl = this.webUrl + "&areaId=" + setDeviceReq1;
                    }

                    this.webUrl = this.webUrl + "&timesnap=" + Long.toString(addDeviceReq1);
                    this.webViewEx.loadUrl(this.webUrl);
                } else {
                    this.webViewEx.reload();
                }
                break;
            case 1:
                this.webUrl = this.ezvizAPI.getServerUrl() + "/api/web/addPage";
                deviceSerial = intent.getStringExtra("com.videogo.EXTRA_DEVICE_ID");
                AddDeviceReq addDeviceReq = new AddDeviceReq();
                AddDeviceInfo addDeviceInfo = new AddDeviceInfo();
                addDeviceInfo.setDeviceId(deviceSerial);
                addDeviceInfo.setCode(intent.getStringExtra("com.videogo.EXTRA_DEVICE_CODE"));
                nvp1 = addDeviceReq.buidParams(addDeviceInfo);
                this.webViewEx.postUrl(this.webUrl, HttpUtils.getPostParam(nvp1));
                break;
            case 2:
                this.webUrl = this.ezvizAPI.getServerUrl() + "/api/web/devicePage";
                SetDeviceReq setDeviceReq = new SetDeviceReq();
                BaseDeviceInfo baseDeviceInfo = new BaseDeviceInfo();
                deviceSerial = intent.getStringExtra("com.videogo.EXTRA_DEVICE_ID");
                baseDeviceInfo.setDeviceId(deviceSerial);
                nvp1 = setDeviceReq.buidParams(baseDeviceInfo);
                this.webViewEx.postUrl(this.webUrl, HttpUtils.getPostParam(nvp1));
                break;
            case 3:
                this.webUrl = this.ezvizAPI.getServerUrl() + "/api/cloud/cloudpay/authEntry";
                Intent i = this.getIntent();
                deviceSerial = i.getStringExtra("serial");
                nvp = new ArrayList();
                nvp.add(new BasicNameValuePair("accessToken", LocalInfo.getInstance().getAccessToken()));
                nvp.add(new BasicNameValuePair("clientType", String.valueOf(13)));
                nvp.add(new BasicNameValuePair("featureCode", LocalInfo.getInstance().getHardwareCode()));
                nvp.add(new BasicNameValuePair("osVersion", Build.VERSION.RELEASE));
                nvp.add(new BasicNameValuePair("clientVersion", "v4.3.0.20161215"));
                nvp.add(new BasicNameValuePair("netType", EzvizAPI.getInstance().getNetType()));
                nvp.add(new BasicNameValuePair("sdkVersion", "v4.3.0.20161215"));
                nvp.add(new BasicNameValuePair("deviceSerial", deviceSerial));
                nvp.add(new BasicNameValuePair("appKey", EzvizAPI.getInstance().getAppKey()));
                nvp.add(new BasicNameValuePair("sessionId", LocalInfo.getInstance().getAccessToken()));
                nvp.add(new BasicNameValuePair("serial", deviceSerial));
                nvp.add(new BasicNameValuePair("cf", "osa1"));
                this.webViewEx.postUrl(this.webUrl, HttpUtils.getPostParam(nvp));
                break;
            case 4:
                this.webUrl = this.ezvizAPI.getServerUrl() + "/api/web/changePassword";
                nvp = new ArrayList();
                nvp.add(new BasicNameValuePair("accessToken", LocalInfo.getInstance().getAccessToken()));
                nvp.add(new BasicNameValuePair("clientType", String.valueOf(13)));
                nvp.add(new BasicNameValuePair("featureCode", LocalInfo.getInstance().getHardwareCode()));
                nvp.add(new BasicNameValuePair("sdkVersion", "v4.3.0.20161215"));
                nvp.add(new BasicNameValuePair("netType", EzvizAPI.getInstance().getNetType()));
                nvp.add(new BasicNameValuePair("osVersion", Build.VERSION.RELEASE));
                nvp.add(new BasicNameValuePair("appKey", EzvizAPI.getInstance().getAppKey()));
                this.webViewEx.postUrl(this.webUrl, HttpUtils.getPostParam(nvp));
        }

    }

    private void addDevice(String mDeviceId, int cameraNo) {
        if (this.cameraInfoEx == null || this.deviceInfoEx == null) {
            this.cameraInfoEx = CameraManager.getInstance().getAddedCamera(mDeviceId, cameraNo);
            if (this.cameraInfoEx == null) {
                try {
                    DeviceManager.getInstance().getDeviceInfoExFromOnlineToLocal(mDeviceId, cameraNo);
                    this.cameraInfoEx = CameraManager.getInstance().getAddedCamera(mDeviceId, cameraNo);
                    this.deviceInfoEx = DeviceManager.getInstance().getDeviceInfoExById(mDeviceId);
                } catch (BaseException var4) {
                    var4.printStackTrace();
                    return;
                }
            }

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.webViewEx.canGoBack()) {
            this.webViewEx.goBack();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void finish() {
        if (this.mode == 2) {
            this.e(this.getIntent().getStringExtra("com.videogo.EXTRA_CAMERA_ID"));
        }

        super.finish();
    }

    private void e(final String cameraId) {
        (new Thread() {
            public void run() {
                if (!TextUtils.isEmpty(cameraId)) {
                    try {
                        CameraMgtCtrl.getCameraDetail(cameraId);
                    } catch (BaseException var2) {
                        var2.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @JavascriptInterface
    public void evaluate(String operate_str, String data) {
        LogUtil.i(TAG, "evaluate, operator:" + operate_str + ", data:" + data);
        if (TextUtils.isEmpty(operate_str)) {
            LogUtil.i(TAG, "evaluate receive null operate_str, return");
        } else {
            JSONObject e;
            final String type;
            if (operate_str.equalsIgnoreCase("changeTitle")) {
                try {
                    e = new JSONObject(data);
                    type = e.optString("val");
                    if (!TextUtils.isEmpty(type)) {
                        this.runOnUiThread(new Runnable() {
                            public void run() {
                                LoginTestActivity.this.titleBar.setTitle(type);
                            }
                        });
                    }
                } catch (JSONException var8) {
                    var8.printStackTrace();
                }
            } else if (operate_str.equalsIgnoreCase("postMessage")) {
                LogUtil.i(TAG, "evaluate: postMessage");
                if (TextUtils.isEmpty(data)) {
                    return;
                }

                try {
                    e = new JSONObject(data);
                    type = e.optString("type");
                    int resultCode = e.optInt("resultCode");
                    if (resultCode == 0) {
                        this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginTestActivity.this, "修改密码成功", 0).show();
                                LoginTestActivity.this.finish();
                            }
                        });
                    } else {
                        this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginTestActivity.this, type + " " + "fail", 0).show();
                            }
                        });
                    }
                } catch (JSONException var7) {
                    var7.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private class FormatDiskTask extends AsyncTask<Integer, Void, Integer> {
        private String deviceId;
        private int cameraNo;

        public FormatDiskTask(String deviceId, int cameraNo) {
            this.deviceId = deviceId;
            this.cameraNo = cameraNo;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Integer a(Integer... params) {
            if (!ConnectionDetector.isNetworkAvailable(LoginTestActivity.this)) {
                LogUtil.debugLog(LoginTestActivity.TAG, "isNetworkAvailable not");
                return null;
            } else {
                addDevice(this.deviceId, this.cameraNo);
                if (LoginTestActivity.this.cameraInfoEx != null) {
                    int position = params[0].intValue();
                    int errorCode = 0;

                    try {
                        StorageCtrl.getInstance().formatDiskByCAS(LoginTestActivity.this.cameraInfoEx.getDeviceID(), position + 1);
                    } catch (BaseException var5) {
                        errorCode = var5.getErrorCode();
                    }

                    return Integer.valueOf(errorCode);
                } else {
                    return null;
                }
            }
        }

        protected void a(Integer result) {
            super.onPostExecute(result);
            if (result != null && result.intValue() == 0) {
                LogUtil.debugLog(LoginTestActivity.TAG, "FormatDiskTask " + result.toString());
                LoginTestActivity.this.webViewEx.loadUrl("javascript:diskFormatSucceed()");
            } else {
                LogUtil.debugLog(LoginTestActivity.TAG, "FormatDiskTask fail:" + result);
                LoginTestActivity.this.webViewEx.loadUrl("javascript:diskFormatFailed()");
            }

        }
    }

    private class UpgradeDeviceTask extends AsyncTask<Void, Void, Integer> {
        private String deviceId;
        private int cameraNo;

        public UpgradeDeviceTask(String deviceId, int cameraNo) {
            this.deviceId = deviceId;
            this.cameraNo = cameraNo;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Integer a(Void... params) {
            if (!ConnectionDetector.isNetworkAvailable(LoginTestActivity.this)) {
                LogUtil.debugLog(LoginTestActivity.TAG, "isNetworkAvailable not");
                return null;
            } else {
                LoginTestActivity.this.addDevice(this.deviceId, this.cameraNo);
                if (LoginTestActivity.this.deviceInfoEx != null) {
                    int result = DeviceUpgradeCtrl.getInstance().startDeviceUpgrade(LoginTestActivity.this.deviceInfoEx);
                    return result;
                } else {
                    return null;
                }
            }
        }

        protected void a(Integer result) {
            super.onPostExecute(result);
            if (result != null && result.intValue() == 0) {
                LogUtil.debugLog(LoginTestActivity.TAG, "UpgradeDeviceTask " + result.toString());
                LoginTestActivity.this.webViewEx.loadUrl("javascript:upgradeSucceed()");
            } else {
                LogUtil.debugLog(LoginTestActivity.TAG, "UpgradeDeviceTask fail:" + result);
                LoginTestActivity.this.webViewEx.loadUrl("javascript:updateFailed()");
            }
        }
    }

    private class MyDownloadListener implements DownloadListener {
        private MyDownloadListener() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            LoginTestActivity.this.startActivity(intent);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        }

        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (LoginTestActivity.this.webViewEx.getTitle() != null) {
                LoginTestActivity.this.titleBar.setTitle(LoginTestActivity.this.webViewEx.getTitle());
            }

            LoginTestActivity.this.imageView.clearAnimation();
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtil.infoLog(LoginTestActivity.TAG, "onPageStarted " + url);
            if (Build.VERSION.SDK_INT >= 14 || !this.execute(url)) {
                super.onPageStarted(view, url, favicon);
                LoginTestActivity.this.titleBar.setTitle("loading...");
                LoginTestActivity.this.imageView.startAnimation(LoginTestActivity.this.animation);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.infoLog(LoginTestActivity.TAG, "shouldOverrideUrlLoading" + url);
            return !this.execute(url) ? super.shouldOverrideUrlLoading(view, url) : true;
        }

        private String[] f(String url) {
            Pattern p = Pattern.compile("operate\\/(.*)\\?paramId=(.*)&");
            Matcher m = p.matcher(url);
            m.find();
            String[] result = new String[]{m.group(1), m.group(2)};
            return result;
        }

        private boolean execute(String url) {
            LogUtil.infoLog(LoginTestActivity.TAG, "checkUrlLoading=" + url);
            String cameraNo;
            String pwd;
            if (url.contains("ysopensdkbridge")) {
                String[] deviceId2 = this.f(url);
                cameraNo = deviceId2[0];
                pwd = deviceId2[1];
                LogUtil.infoLog(LoginTestActivity.TAG, "checkUrlLoading, operate code is:" + cameraNo + ", paramId is:" + pwd);
                if (!TextUtils.isEmpty(cameraNo) && !TextUtils.isEmpty(pwd)) {
                    String jsUrl = "javascript:window.deviceOperate.evaluate(\'" + cameraNo + "\',YsOpenSdkBridge.requestParam[\"" + pwd + "\"])";
                    LogUtil.i(TAG, jsUrl);
                    webViewEx.loadUrl(jsUrl);
                }

                return true;
            } else {
                String deviceId;
                if (!url.contains("/oauth/success") && !url.contains("/oauth/authorize/success")) {
                    if (url.contains("/api/web/notice")) {
                        int deviceId1 = 0;

                        try {
                            deviceId1 = Integer.valueOf(Utils.getUrlValue(url, "resultCode=", "&")).intValue();
                        } catch (NumberFormatException var7) {
                            var7.printStackTrace();
                        }

                        if (LoginTestActivity.this.mode == 1 && deviceId1 == 200) {
                            cameraNo = Utils.getUrlValue(url, "devcieId=", "&");
                            pwd = Utils.getUrlValue(url, "code=", "&");
                            DevPwdUtil.savePwd(cameraNo, pwd);
                            Intent intent2 = new Intent();
                            intent2.setAction("com.videogo.action.ADD_DEVICE_SUCCESS_ACTION");
                            intent2.putExtra("com.videogo.EXTRA_DEVICE_ID", cameraNo);
                            LoginTestActivity.this.sendBroadcast(intent2);
                            LoginTestActivity.this.finish();
                            return true;
                        }

                        if (LoginTestActivity.this.mode == 2 && deviceId1 == 0) {
                            LoginTestActivity.this.ezvizAPI.gotoLoginPage();
                            LoginTestActivity.this.finish();
                            return true;
                        }
                    } else {
                        int cameraNo1;
                        if (url.contains("upload")) {
                            deviceId = LoginTestActivity.this.getIntent().getStringExtra("com.videogo.EXTRA_DEVICE_ID");
                            cameraNo1 = LoginTestActivity.this.getIntent().getIntExtra("com.videogo.EXTRA_CAMERA_NO", 1);
                            (LoginTestActivity.this.new UpgradeDeviceTask(deviceId, cameraNo1)).execute(new Void[0]);
                            return true;
                        }

                        if (url.contains("diskFormat?")) {
                            deviceId = LoginTestActivity.this.getIntent().getStringExtra("com.videogo.EXTRA_DEVICE_ID");
                            cameraNo1 = LoginTestActivity.this.getIntent().getIntExtra("com.videogo.EXTRA_CAMERA_NO", 1);
                            (LoginTestActivity.this.new FormatDiskTask(deviceId, cameraNo1)).execute(new Integer[]{Integer.valueOf(0)});
                            return true;
                        }

                        if (url.contains("diskFormatProgress?")) {
                            deviceId = LoginTestActivity.this.getIntent().getStringExtra("com.videogo.EXTRA_DEVICE_ID");
                            cameraNo1 = LoginTestActivity.this.getIntent().getIntExtra("com.videogo.EXTRA_CAMERA_NO", 1);
                            (LoginTestActivity.this.new GetStoragesStatuseTask(deviceId, cameraNo1)).execute(new Void[0]);
                            return true;
                        }
                    }
                } else {
                    deviceId = Utils.getUrlValue(url, "access_token=", "&");
                    cameraNo = Utils.getUrlValue(url, "expires_in=", "&");
                    pwd = Utils.getUrlValue(url, "areaDomain=", "&");
                    int intent = 0;

                    try {
                        intent = Integer.parseInt(cameraNo);
                    } catch (Exception var8) {
                        var8.printStackTrace();
                    }

                    if (deviceId != null) {
                        LoginTestActivity.this.ezvizAPI.setAccessToken(deviceId);
                        LoginTestActivity.this.ezvizAPI.setTokenExpire(intent);
                        LoginTestActivity.this.ezvizAPI.setAreaDomain(pwd);
                        LogUtil.debugLog(LoginTestActivity.TAG, "t:" + deviceId.substring(0, 5) + " expire:" + intent);
                        Intent intent1 = new Intent();
                        intent1.setAction("com.videogo.action.OAUTH_SUCCESS_ACTION");
                        LoginTestActivity.this.sendBroadcast(intent1);
                        LogUtil.debugLog(LoginTestActivity.TAG, "sendBroadcast:com.videogo.action.OAUTH_SUCCESS_ACTION");
                        LoginTestActivity.this.finish();
                        return true;
                    }
                }

                return false;
            }
        }
    }

    private class GetStoragesStatuseTask extends AsyncTask<Void, Void, Integer> {
        private String deviceId;
        private int cameraNo;

        public GetStoragesStatuseTask(String deviceId, int cameraNo) {
            this.deviceId = deviceId;
            this.cameraNo = cameraNo;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Integer a(Void... params) {
            if (!ConnectionDetector.isNetworkAvailable(LoginTestActivity.this)) {
                LogUtil.debugLog(LoginTestActivity.TAG, "isNetworkAvailable not");
                return null;
            } else {
                LoginTestActivity.this.addDevice(this.deviceId, this.cameraNo);
                if (LoginTestActivity.this.deviceInfoEx == null) {
                    return null;
                } else {
                    int errorCode = 0;

                    try {
                        if (LoginTestActivity.this.deviceInfoEx != null) {
                            LoginTestActivity.this.storageList = StorageCtrl.getInstance().getStoragesStatusByCAS(LoginTestActivity.this.deviceInfoEx.getDeviceID(), "");
                        }
                    } catch (BaseException var4) {
                        var4.printStackTrace();
                        errorCode = var4.getErrorCode();
                    }

                    return Integer.valueOf(errorCode);
                }
            }
        }

        protected void a(Integer result) {
            super.onPostExecute(result);
            if (result != null) {
                int errorCode = result.intValue();
                Storage storage = null;
                if (LoginTestActivity.this.storageList != null && LoginTestActivity.this.storageList.size() > 0) {
                    storage = LoginTestActivity.this.storageList.get(0);
                }

                if (storage != null && errorCode == 0) {
                    LogUtil.debugLog(LoginTestActivity.TAG, "GetStoragesStatuseTask success" + storage.getFormatRate());
                    LoginTestActivity.this.webViewEx.loadUrl("javascript:diskFormatProgress(0," + storage.getFormatRate() + "," + storage.getStatus() + ")");
                } else {
                    LogUtil.debugLog(LoginTestActivity.TAG, "GetStoragesStatuseTask fail" + errorCode);
                    if (storage != null) {
                        LoginTestActivity.this.webViewEx.loadUrl("javascript:diskFormatProgress(" + errorCode + ", " + storage.getFormatRate() + "," + storage.getStatus() + ")");
                    } else {
                        LoginTestActivity.this.webViewEx.loadUrl("javascript:diskFormatProgress(" + errorCode + ", 0, 0)");
                    }
                }
            }
        }
    }
}
