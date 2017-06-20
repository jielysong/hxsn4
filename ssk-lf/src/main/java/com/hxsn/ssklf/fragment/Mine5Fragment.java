package com.hxsn.ssklf.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.ssklf.R;
import com.hxsn.ssklf.TApplication;
import com.hxsn.ssklf.activity.AboutUsActivity;
import com.hxsn.ssklf.utils.Const;


/**
 * 我的页面
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */

@SuppressLint("ValidFragment")
public class Mine5Fragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView txt4,  txtName, txtVersion;
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

        addView(view);
        addListener();
        return view;
    }

    private void addListener() {

        txt4.setOnClickListener(this);

    }

    /**
     * 添加视图控件，显示版本号
     * @param v view
     */
    private void addView(View v) {
        txt4 = (TextView) v.findViewById(R.id.txt4);
        txtVersion = (TextView) v.findViewById(R.id.txt_version);
        version = "sn-ssk-lfqx-" + AndroidUtil.getThisAppVersion(getActivity());
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

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.txt4://帮助
                intent.setClass(context, AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
