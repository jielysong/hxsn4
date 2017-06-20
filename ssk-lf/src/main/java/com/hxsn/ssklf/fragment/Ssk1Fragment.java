package com.hxsn.ssklf.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.ssklf.R;
import com.hxsn.ssklf.TApplication;
import com.hxsn.ssklf.activity.SelectSiteActivity;

import java.lang.reflect.Field;


/**
 *  随时看页面
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class Ssk1Fragment extends Fragment implements View.OnClickListener {
    private Context context;
    private RelativeLayout layout1, layout2, layout4, layout5;//随时看五个顶部菜单
    private ImageView img1, img2,  img4, img5;
    private TextView txt1, txt2, txt4, txt5;
    private int menuTopMode = 1,/*顶部菜单5个 实时数据-变化曲线-报警预警-历史数据-更多*/menuMidMode=1;//


    private Fragment fragmentRealTime,fragmentCurve,fragmentHistory,fragmentMore;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    //private TextView txtSiteName;

    public Ssk1Fragment() {
    }

    public Ssk1Fragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Ssk1Fragment","onCreate");
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ssk1, container, false);
        addView(view);
        addFragment(view);

        Log.i("Ssk1Fragment","onCreateView");

        addListener();

        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addFragment(View view) {
        fragmentRealTime = new RealTimeFragment(context);
        fragmentCurve = new CurveFragment(context);
        fragmentHistory = new HistoryFragment(context);


        fm = getChildFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.framelayout_ssk, fragmentRealTime);
        transaction.commit();
    }

    @Override
    public void onResume() {
        Log.i("Ssk1Fragment","onResume");
        super.onResume();
    }

    @Override
    /**
     *  解决 IllegalStateException: No activity问题
     */
    public void onDetach() {
        super.onDetach();
        Log.i("Ssk1Fragment","onDetach");
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void addListener() {
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
       // layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        layout5.setOnClickListener(this);
    }

    /**
     * 初始化视图
     * @param view fragment视图
     */
    private void addView(View view) {
        layout1 = (RelativeLayout) view.findViewById(R.id.layout1);
        img1 = (ImageView) view.findViewById(R.id.img1);
        txt1 = (TextView) view.findViewById(R.id.txt1);
        layout2 = (RelativeLayout) view.findViewById(R.id.layout2);
        img2 = (ImageView) view.findViewById(R.id.img2);
        txt2 = (TextView) view.findViewById(R.id.txt2);
        layout4 = (RelativeLayout) view.findViewById(R.id.layout4);
        img4 = (ImageView) view.findViewById(R.id.img4);
        txt4 = (TextView) view.findViewById(R.id.txt4);
        layout5 = (RelativeLayout) view.findViewById(R.id.layout5);
        img5 = (ImageView) view.findViewById(R.id.img5);
        txt5 = (TextView) view.findViewById(R.id.txt5);

        TextView txtSiteName = (TextView) view.findViewById(R.id.txt_siteName);
        txtSiteName.setText(TApplication.curSiteInfo.getName());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout1://实时监控
                if (menuTopMode != 1) {
                    clearTopMenu();
                    menuTopMode = 1;
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout_ssk, fragmentRealTime);
                    transaction.commit();
                    img1.setBackgroundResource(R.drawable.detect_s);
                    txt1.setTextColor(getResources().getColor(R.color.green));
                }
                break;
            case R.id.layout2://变化曲线
                if (menuTopMode != 2) {
                    clearTopMenu();
                    menuTopMode = 2;
                    img2.setBackgroundResource(R.drawable.curve_s);
                    txt2.setTextColor(getResources().getColor(R.color.green));
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout_ssk, fragmentCurve);
                    transaction.commit();
                }
                break;
            case R.id.layout4://历史数据
                if (menuTopMode != 4) {
                    clearTopMenu();
                    menuTopMode = 4;
                    img4.setBackgroundResource(R.drawable.history_s);
                    txt4.setTextColor(getResources().getColor(R.color.green));
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.framelayout_ssk, fragmentHistory);
                    transaction.commit();
                }
                break;
            case R.id.layout5://更多
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SelectSiteActivity.class);
                    startActivity(intent);
                break;
        }
    }

    /**
     * 顶部视图复原
     */
    private void clearTopMenu() {
        img1.setBackgroundResource(R.drawable.detect_n);
        txt1.setTextColor(getResources().getColor(R.color.gray));
        img2.setBackgroundResource(R.drawable.curve_n);
        txt2.setTextColor(getResources().getColor(R.color.gray));
        img4.setBackgroundResource(R.drawable.history_n);
        txt4.setTextColor(getResources().getColor(R.color.gray));
        img5.setBackgroundResource(R.drawable.more_n);
        txt5.setTextColor(getResources().getColor(R.color.gray));
    }
}
