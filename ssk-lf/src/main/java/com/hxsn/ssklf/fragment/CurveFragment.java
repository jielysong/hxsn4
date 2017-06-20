package com.hxsn.ssklf.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.library.http.HttpRequest;
import com.hxsn.ssklf.R;
import com.hxsn.ssklf.TApplication;
import com.hxsn.ssklf.model.SiteValue;
import com.hxsn.ssklf.ui.MyLineChart;
import com.hxsn.ssklf.utils.Const;
import com.hxsn.ssklf.utils.JsonUtil;
import com.hxsn.ssklf.utils.Tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 变化曲线页面
 */
@SuppressLint("ValidFragment")
public class CurveFragment extends Fragment implements View.OnClickListener{

    private Context context;
    // 中间菜单选择-四个指标的选择  下面日期模式选择 今天-昨天-前天
    private int menuMidMode=1,dayMode=1;
    // 四个指标文字和数值
    private TextView txtTemp,txtTempVal,txtHumidity,txtHumidityVal,txtSoil,txtSoilVal,txtIllu,txtIlluVal;
    private View viewLine1,viewLine2,viewLine3,viewLine4;//mid_menu宽线条
    private RelativeLayout rlMenu1,rlMenu2,rlMenu3,rlMenu4;//实时监测和变化曲线的四个指标菜单按钮

    //今天-昨天-前天按钮
    private TextView txtToday,txtYesterday,txtThreeDay;
    private View view;
    //处理小数点后为0时不显示
    private DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    private List<SiteValue> siteList;//今天昨天前天小时数据
    private int hour;//当前是几点

    public CurveFragment() {
    }

    public CurveFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.in_curve, container, false);
        Log.i("CurveFragment","menuMidMode="+menuMidMode);
        menuMidMode=1;
        addView(view);
        addListener();
        hour = Tools.getHour();
        //addCurve();
        //获取今天
        obtainData(0);
        return view;
    }


    /**
     *
     * @param type 0-今天 1-昨天 2-前天
     */
    private void obtainData(final int type) {
        String url = Const.URL_PRE_THREE+TApplication.curSiteInfo.getUuid()+"&date="+type;
        new HttpRequest(context) {
            @Override
            public void getResponse(String response) {
                if(JsonUtil.getStatus(response)){
                    siteList = JsonUtil.getSiteList(response);
                    addCurve();
                }
            }
        }.doMyGet(url);
    }

    /**
     * 添加曲线数据并画曲线
     */
    private void addCurve() {
        // 制作24个数据点（沿x坐标轴）
        List<Integer> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();
        if(dayMode == 1){
            for(int i=0; i<hour; i++){
                xList.add(i);
            }
        }else {
            for(int i=0; i<24; i++){
                xList.add(i);
            }
        }

        //LineData mLineData = null;
        for(SiteValue siteValue: siteList){
            switch (menuMidMode){
                case 1:
                    yList.add(siteValue.getTemperature());
                    break;
                case 2:
                    yList.add( siteValue.getHumidity());
                    break;
                case 3:
                    yList.add(siteValue.getSoilTemp());
                    break;
                case 4:
                    yList.add( siteValue.getIllu());
                    break;
            }
        }
        MyLineChart chart = (MyLineChart) view.findViewById(R.id.chart);
        chart.setDate(menuMidMode,xList,yList);
        chart.invalidate();
    }

    private void addListener() {
        rlMenu1.setOnClickListener(this);
        rlMenu2.setOnClickListener(this);
        rlMenu3.setOnClickListener(this);
        rlMenu4.setOnClickListener(this);
        txtToday.setOnClickListener(this);
        txtYesterday.setOnClickListener(this);
        txtThreeDay.setOnClickListener(this);
    }

    private void addView(View view) {
        //中间菜单
        txtTemp = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_temp);
        txtTempVal = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_temp_val);
        txtHumidity = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_humidity);
        txtHumidityVal = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_humidity_val);
        txtSoil = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_soil);
        txtSoilVal = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_soil_val);
        txtIllu = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_illu);
        txtIlluVal = (TextView)view.findViewById(R.id.in_mid_menu).findViewById(R.id.txt_illu_val);
        viewLine1 = view.findViewById(R.id.in_mid_menu).findViewById(R.id.view_line1);
        viewLine2 = view.findViewById(R.id.in_mid_menu).findViewById(R.id.view_line2);
        viewLine3 = view.findViewById(R.id.in_mid_menu).findViewById(R.id.view_line3);
        viewLine4 = view.findViewById(R.id.in_mid_menu).findViewById(R.id.view_line4);

        rlMenu1 = (RelativeLayout)view.findViewById(R.id.rl_menu1);
        rlMenu2 = (RelativeLayout)view.findViewById(R.id.rl_menu2);
        rlMenu3 = (RelativeLayout)view.findViewById(R.id.rl_menu3);
        rlMenu4 = (RelativeLayout)view.findViewById(R.id.rl_menu4);

        txtToday = (TextView)view.findViewById(R.id.txt_today);
        txtYesterday = (TextView)view.findViewById(R.id.txt_yesterday);
        txtThreeDay = (TextView)view.findViewById(R.id.txt_three_day);

        if(TApplication.curSiteValue != null){
            txtTempVal.setText(String.valueOf(decimalFormat.format(TApplication.curSiteValue.getTemperature()))+"℃");
            txtHumidityVal.setText(String.valueOf(TApplication.curSiteValue.getHumidity())+"%");
            txtSoilVal.setText(String.valueOf(decimalFormat.format(TApplication.curSiteValue.getSoilTemp()))+"℃");
            txtIlluVal.setText(String.valueOf(TApplication.curSiteValue.getIllu())+"Lx");
        }

        txtTemp.setTextColor(getResources().getColor(R.color.green_none));
        txtTempVal.setTextColor(getResources().getColor(R.color.green_none));
        txtTempVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_green));
        viewLine1.setBackgroundColor(getResources().getColor(R.color.green_none));
    }

    /**
     * 日期图标回复原始状态
     */
    private void clearDayUI(){
        txtToday.setTextColor(getResources().getColor(R.color.black_text_n));
        txtToday.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_gray));
        txtYesterday.setTextColor(getResources().getColor(R.color.black_text_n));
        txtYesterday.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_gray));
        txtThreeDay.setTextColor(getResources().getColor(R.color.black_text_n));
        txtThreeDay.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_gray));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_menu1://空气温度
                if(menuMidMode != 1){
                    clearMidMenu();
                    menuMidMode = 1;
                    txtTemp.setTextColor(getResources().getColor(R.color.green_none));
                    txtTempVal.setTextColor(getResources().getColor(R.color.green_none));
                    txtTempVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_green));
                    viewLine1.setBackgroundColor(getResources().getColor(R.color.green_none));
                    addCurve();
                }
                break;
            case R.id.rl_menu2://空气湿度
                if(menuMidMode != 2){
                    clearMidMenu();
                    menuMidMode = 2;

                    txtHumidity.setTextColor(getResources().getColor(R.color.green_none));
                    txtHumidityVal.setTextColor(getResources().getColor(R.color.green_none));
                    txtHumidityVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_green));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.green_none));
                    addCurve();
                }
                break;
            case R.id.rl_menu3://土壤温度
                if(menuMidMode != 3){
                    clearMidMenu();
                    menuMidMode = 3;
                    txtSoil.setTextColor(getResources().getColor(R.color.green_none));
                    txtSoilVal.setTextColor(getResources().getColor(R.color.green_none));
                    txtSoilVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_green));
                    viewLine3.setBackgroundColor(getResources().getColor(R.color.green_none));
                    addCurve();
                }
                break;
            case R.id.rl_menu4://光照
                if(menuMidMode != 4){
                    clearMidMenu();
                    menuMidMode = 4;
                    txtIllu.setTextColor(getResources().getColor(R.color.green_none));
                    txtIlluVal.setTextColor(getResources().getColor(R.color.green_none));
                    txtIlluVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_green));
                    viewLine4.setBackgroundColor(getResources().getColor(R.color.green_none));
                    addCurve();
                }
                break;
            case R.id.txt_today://今天
                if(dayMode != 1){
                    dayMode = 1;
                    clearDayUI();
                    txtToday.setTextColor(getResources().getColor(R.color.white));
                    txtToday.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_sky));
                   // initSiteDatas();

                    obtainData(0);

                }
                break;
            case R.id.txt_yesterday://昨天
                if(dayMode != 2){
                    dayMode = 2;
                    clearDayUI();
                    txtYesterday.setTextColor(getResources().getColor(R.color.white));
                    txtYesterday.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_sky));
                   // initSiteDatas();

                    obtainData(1);
                }
                break;
            case R.id.txt_three_day://前天
                if(dayMode != 3){
                    dayMode = 3;
                    clearDayUI();
                    txtThreeDay.setTextColor(getResources().getColor(R.color.white));
                    txtThreeDay.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_sky));
                    //initSiteDatas();
                   //
                    obtainData(2);
                }
                break;
        }
    }

    /**
     * 中间图标回复原始状态
     */
    private void clearMidMenu(){
        txtTemp.setTextColor(getResources().getColor(R.color.black_text_n));
        txtTempVal.setTextColor(getResources().getColor(R.color.black_text_n));
        txtTempVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_gray));
        viewLine1.setBackgroundColor(getResources().getColor(R.color.gray_light_n));

        txtHumidity.setTextColor(getResources().getColor(R.color.black_text_n));
        txtHumidityVal.setTextColor(getResources().getColor(R.color.black_text_n));
        txtHumidityVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_gray));
        viewLine2.setBackgroundColor(getResources().getColor(R.color.gray_light_n));

        txtSoil.setTextColor(getResources().getColor(R.color.black_text_n));
        txtSoilVal.setTextColor(getResources().getColor(R.color.black_text_n));
        txtSoilVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_gray));
        viewLine3.setBackgroundColor(getResources().getColor(R.color.gray_light_n));

        txtIllu.setTextColor(getResources().getColor(R.color.black_text_n));
        txtIlluVal.setTextColor(getResources().getColor(R.color.black_text_n));
        txtIlluVal.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_text_gray));
        viewLine4.setBackgroundColor(getResources().getColor(R.color.gray_light_n));
    }
}
