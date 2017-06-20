package com.hxsn.zzd.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.library.db.Shared;
import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.zzd.MainActivity;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.activity.AboutUsActivity;
import com.hxsn.zzd.activity.LoginActivity;
import com.hxsn.zzd.activity.PasswordEditActivity;
import com.hxsn.zzd.activity.SettingActivity;
import com.hxsn.zzd.activity.SystemSettingActivity;
import com.hxsn.zzd.activity.VersionInfoActivity;
import com.hxsn.zzd.utils.Const;


/**
 * A simple {@link Fragment} subclass.  早知道
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */

@SuppressLint("ValidFragment")
public class Mine5Fragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView txt2, txt3, txt4, txt5,txt6, txtName, txtVersion,txtSystemSetting;
    private RelativeLayout rl1;
    private String version;


    public Mine5Fragment() {
    }

    public Mine5Fragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine5, container, false);
        //TApplication.mode = 5;
        addView(view);
        addListener();
        return view;
    }

    private void addListener() {
        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);
        txt4.setOnClickListener(this);
        txt5.setOnClickListener(this);
        txt6.setOnClickListener(this);

        if(TApplication.versionType != Const.RELEASE_VERTION){
            rl1.setOnClickListener(this);
        }

        txtSystemSetting.setOnClickListener(this);
    }

    private void addView(View v) {
        rl1 = (RelativeLayout)v.findViewById(R.id.rl_1);
        txt2 = (TextView) v.findViewById(R.id.txt2);
        txt3 = (TextView) v.findViewById(R.id.txt3);
        txt4 = (TextView) v.findViewById(R.id.txt4);
        txt5 = (TextView) v.findViewById(R.id.txt5);
        txt6 = (TextView) v.findViewById(R.id.txt6);
        txtSystemSetting = (TextView)v.findViewById(R.id.txt_system_setting);
        txtVersion = (TextView) v.findViewById(R.id.txt_version);
        version = "zzd_v" + AndroidUtil.getThisAppVersion(context);
        switch (TApplication.versionType) {
            case Const.TEST_VERTION:
                version += "T";
                break;
            case Const.TEST_VERTION1:
                version += "T1";
                break;
            case Const.DEBUG_VERTION:
                version += "D";
                break;
            case Const.RELEASE_VERTION:
                version += "R";
                break;
        }
        txtVersion.setText(version);
        txtName = (TextView) v.findViewById(R.id.txt_name);
        if(TApplication.user != null){
            txtName.setText(TApplication.user.getUserName());
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.txt2:
                intent.setClass(context, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.txt3:
                intent.setClass(context, PasswordEditActivity.class);
                startActivity(intent);
                break;
            case R.id.txt4:
                intent.setClass(context, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.txt5://退出
                getActivity().finish();
                AndroidUtil.exit(context, MainActivity.class);
                //System.exit(0);
                break;
            case R.id.txt6://退出登录
                TApplication.user = null;
                Shared.clearUser();
                //intent.setClass(context, NewsActivity.class);
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.rl_1:
                intent.setClass(context, VersionInfoActivity.class);
                intent.putExtra("version",version);
                startActivity(intent);
                break;
            case R.id.txt_system_setting:
                intent.setClass(context, SystemSettingActivity.class);
                intent.putExtra("version",version);
                startActivity(intent);
        }
    }
}
