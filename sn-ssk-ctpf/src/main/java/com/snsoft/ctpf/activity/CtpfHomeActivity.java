package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.db.service.DBSearch;
import com.snsoft.ctpf.fragment.City1Fragment;
import com.snsoft.ctpf.fragment.City2Fragment;
import com.snsoft.ctpf.fragment.EWMFragment;
import com.snsoft.ctpf.fragment.FertilizerTechnologyFragment;
import com.snsoft.ctpf.fragment.HistoryFragment;
import com.snsoft.ctpf.fragment.Map1Fragment;
import com.snsoft.ctpf.fragment.Map2Fragment;
import com.snsoft.ctpf.fragment.MoreFragment;
import com.snsoft.ctpf.fragment.OfflineMapFragment;
import com.snsoft.ctpf.fragment.ShortMsgFragment;
import com.snsoft.ctpf.fragment.SysConfigFragment;
import com.snsoft.ctpf.fragment.UpdateDataFragment;
import com.snsoft.ctpf.fragment.UpdateSysFragment;
import com.snsoft.ctpf.fragment.UserFragment;
import com.snsoft.ctpf.fragment.YangFenFragment;
import com.snsoft.ctpf.util.Config;

import java.util.ArrayList;

public class CtpfHomeActivity extends Activity implements View.OnClickListener{
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    private TextView txtCity;
    private FrameLayout frameLayout;
    private RadioGroup radioGroup;
    private int selectMode = 1;
    private FragmentManager fm;
    private MoreFragment moreFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ctpf_home);

        if(!Config.init()){
            System.exit(1);
        }

        addView();

        String city = DBSearch.getInstance().getCountyName();
        TApplication.city = city;
        txtCity.setText(city);

        addListener();
        addFragment();
        initWidgetStatic();

    }


    private void addListener() {

    }

    private void addView() {
        rb1 = (RadioButton) findViewById(R.id.tab01);
        rb2 = (RadioButton) findViewById(R.id.tab02);
        rb3 = (RadioButton) findViewById(R.id.tab03);
        rb4 = (RadioButton) findViewById(R.id.tab04);
        rb5 = (RadioButton) findViewById(R.id.tab05);
        txtCity = (TextView) findViewById(R.id.txt_city);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        ImageView imgLeft = (ImageView)findViewById(R.id.img_left);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addFragment() {
        TApplication.fragmentList = new ArrayList<>();
        City1Fragment city1Fragment = new City1Fragment(this);
        City2Fragment city2Fragment = new City2Fragment(this);
        EWMFragment ewmFragment = new EWMFragment(this);
        HistoryFragment historyFragment = new HistoryFragment(this);
        Map1Fragment map1Fragment = new Map1Fragment(this);
        Map2Fragment map2Fragment = new Map2Fragment(this);
        ShortMsgFragment shortMsgFragment = new ShortMsgFragment(this);
        SysConfigFragment sysConfigFragment = new SysConfigFragment(this);
        UserFragment userFragment = new UserFragment(this);
        OfflineMapFragment offlineMapFragment = new OfflineMapFragment(this);
        UpdateDataFragment updateDataFragment = new UpdateDataFragment(this);
        UpdateSysFragment updateSysFragment = new UpdateSysFragment(this);
        YangFenFragment yangFenFragment = new YangFenFragment(this);
        FertilizerTechnologyFragment fertilizerTechnologyFragment = new FertilizerTechnologyFragment(this);
        moreFragment = new MoreFragment(this);

        TApplication.fragmentList.add(yangFenFragment);
        TApplication.fragmentList.add(map1Fragment);
        TApplication.fragmentList.add(ewmFragment);
        TApplication.fragmentList.add(map2Fragment);

        TApplication.fragmentList.add(city1Fragment);
        TApplication.fragmentList.add(historyFragment);
        TApplication.fragmentList.add(offlineMapFragment);

        TApplication.fragmentList.add(sysConfigFragment);
        TApplication.fragmentList.add(userFragment);
        TApplication.fragmentList.add(city2Fragment);

        TApplication.fragmentList.add(shortMsgFragment);
        TApplication.fragmentList.add(updateDataFragment);
        TApplication.fragmentList.add(updateSysFragment);
        TApplication.fragmentList.add(fertilizerTechnologyFragment);

        fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for(int i=0; i<4; i++){
            Log.i("HomeActivity","Config"+i+"="+Config.baseIndexs[i]);
        }

        transaction.add(R.id.framelayout, TApplication.fragmentList.get(Config.baseIndexs[0]));
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

    }

    /** 初始化控件 */
    protected void initWidgetStatic() {

        /** 选项卡的所有标签 */
        RadioButton[] radioButtons = {rb1, rb2, rb3, rb4, rb5};
        for (int i = 0; i < radioButtons.length; i++) {
            //radioButtons[i] = (RadioButton) findViewById(radioButtonIds[i]);
            Drawable drawable = ContextCompat.getDrawable(this, Config.cinfigint[i]);
            radioButtons[i].setText(Config.configTabStr[i]);
            if (i < 4) {
                radioButtons[i].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            } else {
                radioButtons[i].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            }

            radioButtons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    switch(v.getId()){
                        case R.id.tab01:
                            if(selectMode != 1){
                                selectMode = 1;
                                transaction.replace(R.id.framelayout, TApplication.fragmentList.get(Config.baseIndexs[0]));
                                transaction.commit();
                            }
                            break;
                        case R.id.tab02:
                            if(selectMode != 2){
                                selectMode = 2;
                                transaction.replace(R.id.framelayout, TApplication.fragmentList.get(Config.baseIndexs[1]));
                                transaction.commit();
                            }
                            break;
                        case R.id.tab03:
                            if(selectMode != 3){
                                selectMode = 3;
                                transaction.replace(R.id.framelayout, TApplication.fragmentList.get(Config.baseIndexs[2]));
                                transaction.commit();
                            }
                            break;
                        case R.id.tab04:
                            if(selectMode != 4){
                                selectMode = 4;
                                transaction.replace(R.id.framelayout, TApplication.fragmentList.get(Config.baseIndexs[3]));
                                transaction.commit();
                            }
                            break;
                        case R.id.tab05:
                            //if(selectMode != 5){
                                selectMode = 5;
                                transaction.replace(R.id.framelayout, moreFragment);
                                transaction.commit();
                            //}
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        RadioButton[] radioButtons = {rb1, rb2, rb3, rb4, rb5};
        switch(selectMode){
            case 1:
                super.onBackPressed();
                break;
            case 5:
                selectMode = 2;
                Drawable drawable = ContextCompat.getDrawable(this, Config.cinfigint[4]);
                radioButtons[4].setText(Config.configTabStr[4]);
                radioButtons[4].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);

                transaction.replace(R.id.framelayout, moreFragment);
                transaction.commit();
                break;
            default:
                selectMode = 1;
                Drawable drawable1 = ContextCompat.getDrawable(this, Config.cinfigint[0]);
                radioButtons[0].setText(Config.configTabStr[0]);
                radioButtons[0].setCompoundDrawablesWithIntrinsicBounds(null, drawable1, null, null);

                transaction.replace(R.id.framelayout, TApplication.fragmentList.get(Config.baseIndexs[0]));
                transaction.commit();
                break;
        }
    }

    /**
     * 回调接口
     * @author zhaoxin5
     *
     */
    public interface MyTouchListener {
         void onTouchEvent(MotionEvent event);
    }

    /*
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add( listener );
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove( listener );
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
