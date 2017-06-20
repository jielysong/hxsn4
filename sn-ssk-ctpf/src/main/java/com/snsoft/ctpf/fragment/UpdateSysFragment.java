package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.db.Shared;
import com.snsoft.ctpf.util.AndroidUtil;
import com.snsoft.ctpf.util.Const;
import com.snsoft.ctpf.util.DownLoadUtil;
import com.snsoft.ctpf.util.VersionUtil;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UpdateSysFragment extends Fragment implements View.OnClickListener{
    private Context context;

    private static final int TIMEOUT = 10 * 1000;
    private static final int DOWN_OK = 1;
    private static final int DOWN_ERROR = 0;
    private static final int UPDATE_DATE = 2;
    private boolean click = true;
    private TextView txtSure,txtBack;
    private TextView txtShow;
    private ProgressBar mUpdateBar;
    private Boolean isUpdateOk = false;

    public UpdateSysFragment() {

    }

    public UpdateSysFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_data, container, false);

        addView(view);
        addListener();

        String url = TApplication.sysParam.getUrl();
        Integer newVersion = VersionUtil.getVersionForUrl(url, Const.KEY_APK_VERSION);
        if(newVersion == null){
            AndroidUtil.show(context,"请设置数据库地址");
            FragmentManager fm = getFragmentManager();
            final FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.framelayout, TApplication.getFragmentFromName("SysConfigFragment"));
            transaction.commit();
            return view;
        }

        int oldVersion = Shared.getInstance(context).getInt(Const.KEY_LOCAL_APK_VERSION);
        if(newVersion > oldVersion){//有升级
            txtSure.setVisibility(View.VISIBLE);
            click = true;
        }else {
            txtShow.setText("当前没有更新");
            click = false;
            txtSure.setVisibility(View.GONE);
        }
        return view;
    }


    private void addListener() {
        txtSure.setOnClickListener(this);
        txtBack.setOnClickListener(this);
    }

    private void addView(View view) {
        txtShow = (TextView) view.findViewById(R.id.txt_update_show);
        txtSure = (TextView) view.findViewById(R.id.txt_sure);
        mUpdateBar = (ProgressBar) view.findViewById(R.id.update_progressBar);
        txtBack = (TextView)view.findViewById(R.id.txt_back);

        txtShow.setText("是否进行系统更新");
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()){
            case R.id.txt_sure:
                if (click) {
                    txtShow.setText("数据正在更新中,请勿退出软件！");
                    txtSure.setText("正在从服务器下载数据。。");
                    txtBack.setVisibility(View.GONE);
                    downloadApk();
                    click = false;
                    mUpdateBar.setVisibility(View.VISIBLE);
                }else {
                    transaction.replace(R.id.framelayout, new MoreFragment(context));
                    transaction.commit();
                }
                if(isUpdateOk){
                    //下载成功，安装APK
                    File file = new File(Const.PATH_APK_FILE);
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    context.startActivity(intent);

                    String url = TApplication.sysParam.getUrl();
                    Integer newVersion = VersionUtil.getVersionForUrl(url, Const.KEY_APK_VERSION);
                    if(newVersion == null) newVersion = 0;
                    Shared.getInstance(context).setInt(Const.KEY_LOCAL_APK_VERSION,newVersion);
                }
                break;
            case R.id.txt_back:
                transaction.replace(R.id.framelayout, new MoreFragment(context));
                transaction.commit();
                break;
        }
    }

    private void downloadApk(){

        Handler.Callback callback = new DownloadHandlerCallback();
        final Handler handler = new Handler(callback);

        File file = new File(Const.PATH_APP);
        if(!file.exists()){
            file.mkdirs();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    message.what = DOWN_OK;
                    handler.sendMessage(message);
                    long downloadSize = DownLoadUtil.downloadUpdateFile(VersionUtil.getApkUrl(context), Const.PATH_APK_FILE);
                    if (downloadSize > 0) {
                        Message msg = new Message();
                        msg.what = UPDATE_DATE;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = DOWN_ERROR;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    class DownloadHandlerCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case DOWN_OK:
                    txtShow.setText("正在更新数据库。。");
                    break;
                case DOWN_ERROR:
                    txtShow.setText("更新失败！");
                    mUpdateBar.setVisibility(View.GONE);
                    break;
                case UPDATE_DATE:
                    isUpdateOk = true;
                    txtShow.setText("更新成功！请安装");
                    txtSure.setText("确定");
                    mUpdateBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            return true;
        }
    }



}
