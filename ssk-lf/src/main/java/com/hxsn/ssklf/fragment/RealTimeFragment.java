package com.hxsn.ssklf.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.library.http.HttpRequest;
import com.hxsn.ssklf.R;
import com.hxsn.ssklf.TApplication;
import com.hxsn.ssklf.model.SiteValue;
import com.hxsn.ssklf.model.Threshold;
import com.hxsn.ssklf.utils.Const;
import com.hxsn.ssklf.utils.JsonUtil;
import com.hxsn.ssklf.utils.Tools;

import java.text.DecimalFormat;
import java.util.List;

/**
 * create an instance of this fragment.
 * 实时监控的fragment
 */
@SuppressLint("ValidFragment")
public class RealTimeFragment extends Fragment implements View.OnClickListener{

    private Context context;
    /**
     * 中间菜单模式 空气温度 空气湿度，土壤温度 光照
     */
    private int menuMidMode=1;
    /**
     * 中间菜单的TextView
     */
    private TextView txtTemp,txtTempVal,txtHumidity,txtHumidityVal,txtSoil,txtSoilVal,txtIllu,txtIlluVal;
    /**
     * mid_menu宽线条
     */
    private View viewLine1,viewLine2,viewLine3,viewLine4;//
    /**
     * 主界面背景图，即波浪形
     */
    private ImageView imgBgColor;                       //
    /**
     * 主界面背景颜色，根据阈值颜色变化
     */
    private LinearLayout lineBgColor;
    /**
     * 实时监测和变化曲线的四个指标菜单按钮   空气温度 空气湿度，土壤温度 光照
     */
    private RelativeLayout rlMenu1,rlMenu2,rlMenu3,rlMenu4;
    /**
     * 当前值，判断偏高偏低适宜，区间范围,当前建议
     */
    private TextView txtCurValue,txtCheck,txtZone,txtInfo;

    /**
     * 今天TextView   五天的TextView  5个星期  5个趋势箭头 5个值
     */
    private TextView txtToday,txtWeek1,txtWeek2,txtWeek3,txtWeek4,txtWeek5,txtArrow11,txtArrow12,txtArrow13,txtArrow14,txtArrow15,txtValue11,txtValue12,txtValue13,txtValue14,txtValue15;
    /**
     * 6小时TextView，时间，趋势，数值
     */
    private TextView txtHour1,txtHour2,txtHour3,txtHour4,txtHour5,txtHour6,txtArrow1,txtArrow2,txtArrow3,txtArrow4,txtArrow5,txtArrow6,txtValue1,txtValue2,txtValue3,txtValue4,txtValue5,txtValue6;
    /**
     * 实时监测数值 包括空气温度 空气湿度，土壤温度 光照
     */
    //private SiteValue curSiteValue;

    /**
     *  空气温度 空气湿度，土壤温度 光照的阈值
     */
    private Threshold threshold;

    /**
     * 6小时数据
     */
    private List<SiteValue> siteValue6HourList;
    /**
     * 5天数据
     */
    private List<SiteValue> siteValue5DaysList;
    /**
     * 当前时间view
     */
    private TextView txtCurTime;
    /**
     * 处理小数点为0的数据显示
     */
    private DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
    /**
     * 是否是实时数据
     */
    private boolean isRealTime = true;

    public RealTimeFragment() {
    }

    public RealTimeFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("RealTimeFragment","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_time, container, false);
        Log.i("RealTimeFragment","onCreateView");
        Log.i("RealTimeFragment","menuMidMode="+menuMidMode);
        menuMidMode=1;
        addView(view);
        addListener();
        clearBackGround();
        initUI();

        //获取实时数据
        obtainRealTimeData();

        return view;
    }

    /**
     * 网络获取实时数据，并刷新
     */
    private void obtainRealTimeData() {
        String url = Const.URL_REAL_TIME+TApplication.curSiteInfo.getUuid();
        new HttpRequest(context) {
            @Override
            public void getResponse(String response) {
                if(JsonUtil.getStatus(response)){
                    TApplication.curSiteValue = JsonUtil.getSiteValue(response);
                    Log.i("RealTimeFragment","siteVlue="+TApplication.curSiteValue.toString());
                    threshold = JsonUtil.getThreshold(response);
                    if(TApplication.curSiteValue != null){
                        String time = TApplication.curSiteValue.getTimeInfo();
                        refreshCurUi();
                        if(!Tools.isRealTime(time,60)){
                            clearBackGround();
                        }

                        //获取6小时数据
                        obtain6HourData();
                        //获取5天数据
                        obtain5DaysData();
                    }else{
                        clearBackGround();
                    }
                }
            }
        }.doMyGet(url);
    }

    /**
     * 获取6小时数据,并刷新
     */
    private void obtain6HourData() {
        String url = Const.URL_SIX_HOUR+TApplication.curSiteInfo.getUuid();
        new HttpRequest(context) {
            @Override
            public void getResponse(String response) {
                if(JsonUtil.getStatus(response)){
                    siteValue6HourList = JsonUtil.getSiteList(response);
                    if(siteValue6HourList.size() == 6 ){
                        refresh6HourUi();
                    }
                }
            }
        }.doMyGet(url);

//        request6Hour.doMyGet(url);
    }


    /**
     *  获取5天数据
     */
    private void obtain5DaysData() {
        String url = Const.URL_FIVE_DAYS+TApplication.curSiteInfo.getUuid();
        new HttpRequest(context) {
            @Override
            public void getResponse(String response) {
                if(JsonUtil.getStatus(response)){
                    siteValue5DaysList = JsonUtil.getSiteList(response);
                    if(siteValue5DaysList.size()==5){
                        refresh5DayUi();
                    }

                }
            }
        }.doMyGet(url);
    }

    /**
     * 添加监听按钮
     */
    private void addListener() {
        rlMenu1.setOnClickListener(this);
        rlMenu2.setOnClickListener(this);
        rlMenu3.setOnClickListener(this);
        rlMenu4.setOnClickListener(this);
    }

    /**
     * 添加View控件
     * @param view view
     */
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

        imgBgColor = (ImageView)view.findViewById(R.id.img_bg_color);
        lineBgColor = (LinearLayout) view.findViewById(R.id.line_bg_color);

        rlMenu1 = (RelativeLayout)view.findViewById(R.id.rl_menu1);
        rlMenu2 = (RelativeLayout)view.findViewById(R.id.rl_menu2);
        rlMenu3 = (RelativeLayout)view.findViewById(R.id.rl_menu3);
        rlMenu4 = (RelativeLayout)view.findViewById(R.id.rl_menu4);

        txtCurValue = (TextView)view.findViewById(R.id.txt_value_cur);
        txtCheck = (TextView)view.findViewById(R.id.txt_check);
        txtZone = (TextView)view.findViewById(R.id.txt_zone);
        txtInfo = (TextView)view.findViewById(R.id.txt_info);
//txtToday,txtHours,txtArrows,txtValues,txtDay1,txtDay2,txtDay3,txtDay4,txtDay5;
        txtToday = (TextView)view.findViewById(R.id.txt_today);
        txtHour1 = (TextView)view.findViewById(R.id.txt_hour1);
        txtHour2 = (TextView)view.findViewById(R.id.txt_hour2);
        txtHour3 = (TextView)view.findViewById(R.id.txt_hour3);
        txtHour4 = (TextView)view.findViewById(R.id.txt_hour4);
        txtHour5 = (TextView)view.findViewById(R.id.txt_hour5);
        txtHour6 = (TextView)view.findViewById(R.id.txt_hour6);
        txtArrow1 = (TextView)view.findViewById(R.id.txt_arrow1);
        txtArrow2 = (TextView)view.findViewById(R.id.txt_arrow2);
        txtArrow3 = (TextView)view.findViewById(R.id.txt_arrow3);
        txtArrow4 = (TextView)view.findViewById(R.id.txt_arrow4);
        txtArrow5 = (TextView)view.findViewById(R.id.txt_arrow5);
        txtArrow6 = (TextView)view.findViewById(R.id.txt_arrow6);
        txtValue1 = (TextView)view.findViewById(R.id.txt_value1);
        txtValue2 = (TextView)view.findViewById(R.id.txt_value2);
        txtValue3 = (TextView)view.findViewById(R.id.txt_value3);
        txtValue4 = (TextView)view.findViewById(R.id.txt_value4);
        txtValue5 = (TextView)view.findViewById(R.id.txt_value5);
        txtValue6 = (TextView)view.findViewById(R.id.txt_value6);
        txtWeek1 = (TextView)view.findViewById(R.id.txt_week1);
        txtWeek2 = (TextView)view.findViewById(R.id.txt_week2);
        txtWeek3 = (TextView)view.findViewById(R.id.txt_week3);
        txtWeek4 = (TextView)view.findViewById(R.id.txt_week4);
        txtWeek5 = (TextView)view.findViewById(R.id.txt_week5);
        txtArrow11 = (TextView)view.findViewById(R.id.txt_arrow11);
        txtArrow12 = (TextView)view.findViewById(R.id.txt_arrow12);
        txtArrow13 = (TextView)view.findViewById(R.id.txt_arrow13);
        txtArrow14 = (TextView)view.findViewById(R.id.txt_arrow14);
        txtArrow15 = (TextView)view.findViewById(R.id.txt_arrow15);
        txtValue11 = (TextView)view.findViewById(R.id.txt_value11);
        txtValue12 = (TextView)view.findViewById(R.id.txt_value12);
        txtValue13 = (TextView)view.findViewById(R.id.txt_value13);
        txtValue14 = (TextView)view.findViewById(R.id.txt_value14);
        txtValue15 = (TextView)view.findViewById(R.id.txt_value15);

        txtCurTime = (TextView)view.findViewById(R.id.txt_cur_time);
        txtCurTime.setText(Tools.getCurTime());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
                    initUI();
                    if(TApplication.curSiteValue != null && siteValue5DaysList.size()==5 && siteValue6HourList.size()==6){
                        refreshCurUi();
                        refresh5DayUi();
                        refresh6HourUi();
                    }
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
                    initUI();
                    if(TApplication.curSiteValue != null && siteValue5DaysList.size()==5 && siteValue6HourList.size()==6){
                        refreshCurUi();
                        refresh5DayUi();
                        refresh6HourUi();
                    }
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
                    initUI();
                    if(TApplication.curSiteValue != null && siteValue5DaysList.size()==5 && siteValue6HourList.size()==6){
                        refreshCurUi();
                        refresh5DayUi();
                        refresh6HourUi();
                    }
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
                    initUI();
                    if(TApplication.curSiteValue != null && siteValue5DaysList.size()==5 && siteValue6HourList.size()==6){
                        refreshCurUi();
                        refresh5DayUi();
                        refresh6HourUi();
                    }
                }
                break;
        }
    }

    /**
     * 复位中间菜单图标和文字颜色
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

    /**
     * 初始化UI
     */
    private void initUI(){
        TextView[] txtHourArray = {txtHour1,txtHour2,txtHour3,txtHour4,txtHour5,txtHour6};
        TextView[] txtWeekArray = {txtWeek1,txtWeek2,txtWeek3,txtWeek4,txtWeek5};

        txtToday.setText(Tools.getWeekStr(Tools.getIntWeek())+"      今天");
        int hour = Tools.getHour();
        hour = Tools.getPre5Hour(hour);
        //int week = Tools.getIntWeek();
        int week = Tools.getIntWeek();
        for(int i=0; i<6; i++){
            txtHourArray[i].setText(String.valueOf(hour));
            hour = Tools.getNextHour(hour);
            if(i!=5){
                txtWeekArray[i].setText(Tools.getPreWeek(week));
            }
            week -= 1;
            if(week == 0){
                week = 7;
            }
        }

        String str = "";
        switch (menuMidMode) {
            case 1://空气温度
                str = "空气温度";
                break;
            case 2://空气湿度
                str = "空气湿度";
                break;
            case 3://土壤温度
                str = "土壤温度";
                break;
            case 4://光照
                str = "光照";
                break;
        }
        txtCheck.setText(str+"正常");
        txtZone.setText("适宜"+str);
        txtInfo.setText("当前"+str+"正常,请注意保持");
    }

    /**
     * 刷新5天数据
     */
    private void refresh5DayUi(){
        TextView[] txtArrow1Array = {txtArrow11,txtArrow12,txtArrow13,txtArrow14,txtArrow15};
        TextView[] txtValue1Array = {txtValue11,txtValue12,txtValue13,txtValue14,txtValue15};
        switch (menuMidMode){
            case 1://空气温度

                if(TApplication.curSiteValue.getTemperature() > siteValue5DaysList.get(0).getTemperature()){
                    txtArrow1Array[0].setText("↓");
                }else if(TApplication.curSiteValue.getTemperature() < siteValue5DaysList.get(0).getTemperature()){
                    txtArrow1Array[0].setText("↑");
                }else {
                    txtArrow1Array[0].setText("-");
                }

                for(int i=0; i<5; i++){
                    txtValue1Array[i].setText(decimalFormat.format(siteValue5DaysList.get(i).getTemperature()));
                    if(i != 4){
                        if(siteValue5DaysList.get(i).getTemperature() > siteValue5DaysList.get(i+1).getTemperature()){
                            txtArrow1Array[i+1].setText("↓");
                        }else if(siteValue5DaysList.get(i).getTemperature() < siteValue5DaysList.get(i+1).getTemperature()){
                            txtArrow1Array[i+1].setText("↑");
                        }else {
                            txtArrow1Array[i+1].setText("-");
                        }
                    }
                }

                break;
            case 2://空气湿度
                if(TApplication.curSiteValue.getHumidity() > siteValue5DaysList.get(0).getHumidity()){
                    txtArrow1Array[0].setText("↓");
                }else if(TApplication.curSiteValue.getHumidity() < siteValue5DaysList.get(0).getHumidity()){
                    txtArrow1Array[0].setText("↑");
                }else {
                    txtArrow1Array[0].setText("-");
                }

                for(int i=0; i<5; i++){
                    txtValue1Array[i].setText(decimalFormat.format(siteValue5DaysList.get(i).getHumidity()));
                    if(i != 4){
                        if(siteValue5DaysList.get(i).getHumidity() > siteValue5DaysList.get(i+1).getHumidity()){
                            txtArrow1Array[i+1].setText("↓");
                        }else if(siteValue5DaysList.get(i).getHumidity() < siteValue5DaysList.get(i+1).getHumidity()){
                            txtArrow1Array[i+1].setText("↑");
                        }else {
                            txtArrow1Array[i+1].setText("-");
                        }
                    }
                }
                break;
            case 3://土壤温度
                if(TApplication.curSiteValue.getSoilTemp() > siteValue5DaysList.get(0).getSoilTemp()){
                    txtArrow1Array[0].setText("↓");
                }else if(TApplication.curSiteValue.getSoilTemp() < siteValue5DaysList.get(0).getSoilTemp()){
                    txtArrow1Array[0].setText("↑");
                }else {
                    txtArrow1Array[0].setText("-");
                }

                for(int i=0; i<5; i++){
                    txtValue1Array[i].setText(decimalFormat.format(siteValue5DaysList.get(i).getHumidity()));
                    if(i != 4){
                        if(siteValue5DaysList.get(i).getSoilTemp() > siteValue5DaysList.get(i+1).getSoilTemp()){
                            txtArrow1Array[i+1].setText("↓");
                        }else if(siteValue5DaysList.get(i).getSoilTemp() < siteValue5DaysList.get(i+1).getSoilTemp()){
                            txtArrow1Array[i+1].setText("↑");
                        }else {
                            txtArrow1Array[i+1].setText("-");
                        }
                    }
                }
                break;
            case 4://光照
                if(TApplication.curSiteValue.getIllu() > siteValue5DaysList.get(0).getIllu()){
                    txtArrow1Array[0].setText("↓");
                }else if(TApplication.curSiteValue.getIllu() < siteValue5DaysList.get(0).getIllu()){
                    txtArrow1Array[0].setText("↑");
                }else {
                    txtArrow1Array[0].setText("-");
                }

                for(int i=0; i<5; i++){
                    txtValue1Array[i].setText(decimalFormat.format(siteValue5DaysList.get(i).getIllu()));
                    if(i != 4){
                        if(siteValue5DaysList.get(i).getIllu() > siteValue5DaysList.get(i+1).getIllu()){
                            txtArrow1Array[i+1].setText("↓");
                        }else if(siteValue5DaysList.get(i).getIllu() < siteValue5DaysList.get(i+1).getIllu()){
                            txtArrow1Array[i+1].setText("↑");
                        }else {
                            txtArrow1Array[i+1].setText("-");
                        }
                    }
                }
                break;
        }
    }

    /**
     * 未收到实时数据时刷新背景色为灰色
     */
    private void clearBackGround(){
        imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_error_color));
        lineBgColor.setBackgroundColor(getResources().getColor(R.color.gray));
        txtCurValue.setTextColor(getResources().getColor(R.color.gray_dark));
        txtCheck.setTextColor(getResources().getColor(R.color.gray_dark));
        txtCheck.setText("不是当前数据");
    }

    /**
     * 刷新6小时数据
     */
    private void refresh6HourUi(){
        TextView[] txtValueArray = {txtValue1,txtValue2,txtValue3,txtValue4,txtValue5,txtValue6};
        TextView[] txtArrowArray = {txtArrow1,txtArrow2,txtArrow3,txtArrow4,txtArrow5,txtArrow6};

        switch (menuMidMode) {
            case 1://空气温度
                if(TApplication.curSiteValue.getTemperature()>siteValue6HourList.get(0).getTemperature()){
                    txtArrow1.setText("↓");
                }else if(TApplication.curSiteValue.getTemperature()<siteValue6HourList.get(0).getTemperature()){
                    txtArrow1.setText("↑");
                }else {
                    txtArrow1.setText("-");
                }

                for(int i=0; i<6; i++){
                    txtValueArray[i].setText(decimalFormat.format(siteValue6HourList.get(i).getTemperature()));
                    if(i != 5){
                        if(siteValue6HourList.get(i).getTemperature()>siteValue6HourList.get(i+1).getTemperature()){
                            txtArrowArray[i+1].setText("↓");
                        }else if(siteValue6HourList.get(i).getTemperature()<siteValue6HourList.get(i+1).getTemperature()){
                            txtArrowArray[i+1].setText("↑");
                        }else {
                            txtArrow1.setText("-");
                        }
                    }
                }
                break;
            case 2://空气湿度

                if(TApplication.curSiteValue.getHumidity()>siteValue6HourList.get(0).getHumidity()){
                    txtArrow1.setText("↓");
                }else if(TApplication.curSiteValue.getHumidity()<siteValue6HourList.get(0).getHumidity()){
                    txtArrow1.setText("↑");
                }else {
                    txtArrow1.setText("-");
                }
                for(int i=0; i<6; i++){
                    txtValueArray[i].setText(decimalFormat.format(siteValue6HourList.get(i).getHumidity()));
                    if(i != 5){
                        if(siteValue6HourList.get(i).getHumidity()>siteValue6HourList.get(i+1).getHumidity()){
                            txtArrowArray[i+1].setText("↓");
                        }else if(siteValue6HourList.get(i).getHumidity()<siteValue6HourList.get(i+1).getHumidity()){
                            txtArrowArray[i+1].setText("↑");
                        }else {
                            txtArrow1.setText("-");
                        }
                    }
                }
                break;
            case 3://土壤温度
                if(TApplication.curSiteValue.getSoilTemp()>siteValue6HourList.get(0).getSoilTemp()){
                    txtArrow1.setText("↓");
                }else if(TApplication.curSiteValue.getSoilTemp()<siteValue6HourList.get(0).getSoilTemp()){
                    txtArrow1.setText("↑");
                }else {
                    txtArrow1.setText("-");
                }

                for(int i=0; i<txtValueArray.length; i++){
                    txtValueArray[i].setText(decimalFormat.format(siteValue6HourList.get(i).getSoilTemp()));
                    if(i != 5){
                        if(siteValue6HourList.get(i).getSoilTemp()>siteValue6HourList.get(i+1).getSoilTemp()){
                            txtArrowArray[i+1].setText("↓");
                        }else if(siteValue6HourList.get(i).getSoilTemp()<siteValue6HourList.get(i+1).getSoilTemp()){
                            txtArrowArray[i+1].setText("↑");
                        }else {
                            txtArrow1.setText("-");
                        }
                    }
                }
                break;
            case 4://光照
                if(TApplication.curSiteValue.getIllu()>siteValue6HourList.get(0).getIllu()){
                    txtArrow1.setText("↓");
                }else if(TApplication.curSiteValue.getIllu()<siteValue6HourList.get(0).getIllu()){
                    txtArrow1.setText("↑");
                }else {
                    txtArrow1.setText("-");
                }
                for(int i=0; i<6; i++){
                    txtValueArray[i].setText(decimalFormat.format(siteValue6HourList.get(i).getIllu()));
                    if(i != 5){
                        if(siteValue6HourList.get(i).getIllu()>siteValue6HourList.get(i+1).getIllu()){
                            txtArrowArray[i+1].setText("↓");
                        }else if(siteValue6HourList.get(i).getIllu()<siteValue6HourList.get(i+1).getIllu()){
                            txtArrowArray[i+1].setText("↑");
                        }else {
                            txtArrow1.setText("-");
                        }
                    }
                }
                break;
        }
    }

    /**
     * 刷新实时数据
     */
    private void refreshCurUi(){
        String start="",end="",value="",mid="",str="",str1="";
        mid="-";
        switch (menuMidMode) {
            case 1://空气温度
                start = "适宜空气温度";
                if(threshold != null){
                    mid = threshold.getDownTemp()+"-"+threshold.getUpTemp();
                }
                end = "℃";
                value = String.valueOf(decimalFormat.format(TApplication.curSiteValue.getTemperature()));
                if(TApplication.curSiteValue.getTemperature() > threshold.getUpTemp()){          //高温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_high_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.high));
                    txtCurValue.setTextColor(getResources().getColor(R.color.high));
                    txtCheck.setTextColor(getResources().getColor(R.color.high));
                    str = "空气温度偏高";
                    str1 = "，请采取措施降低温度";
                }else if(TApplication.curSiteValue.getTemperature() < threshold.getDownTemp()){    //低温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_low_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.low));
                    txtCurValue.setTextColor(getResources().getColor(R.color.low));
                    txtCheck.setTextColor(getResources().getColor(R.color.low));
                    str = "空气温度偏低";
                    str1 = "，请采取措施提高温度";
                }else {                                                         //正常
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_nomal_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.normal));
                    txtCurValue.setTextColor(getResources().getColor(R.color.normal));
                    txtCheck.setTextColor(getResources().getColor(R.color.normal));
                    str = "空气温度正常";
                    str1 = "，请采注意保持";
                }
                str1 = "棚内当前空气温度为"+TApplication.curSiteValue.getTemperature()+end+"，"+str+str1;
                break;
            case 2://空气湿度
                start = "适宜空气湿度";
                if(threshold != null){
                    mid = threshold.getDownHumidity()+"-"+threshold.getUpHumidity();
                }
                end = "%";
                value = String.valueOf(TApplication.curSiteValue.getHumidity());
                if(TApplication.curSiteValue.getHumidity() > threshold.getUpHumidity()){          //高温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_high_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.high));
                    txtCurValue.setTextColor(getResources().getColor(R.color.high));
                    txtCheck.setTextColor(getResources().getColor(R.color.high));
                    str = "空气湿度偏高";
                    str1 = "，请采取措施降低湿度";
                }else if(TApplication.curSiteValue.getHumidity() < threshold.getDownHumidity()){    //低温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_low_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.low));
                    txtCurValue.setTextColor(getResources().getColor(R.color.low));
                    txtCheck.setTextColor(getResources().getColor(R.color.low));
                    str = "空气湿度偏低";
                    str1 = "，请采取措施增加湿度";
                }else {                                                         //正常
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_nomal_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.normal));
                    txtCurValue.setTextColor(getResources().getColor(R.color.normal));
                    txtCheck.setTextColor(getResources().getColor(R.color.normal));
                    str = "空气湿度正常";
                    str1 = "，请采注意保持";
                }
                str1 = "棚内当前空气湿度为"+TApplication.curSiteValue.getHumidity()+end+"，"+str+str1;
                break;
            case 3://土壤温度
                start = "适宜土壤温度";
                if(threshold != null){
                    mid = threshold.getDownTemp15cm()+"-"+threshold.getUpTemp15cm();
                }
                end = "℃";
                //TApplication.curSiteValue.setSoilTemp(100);
                value = String.valueOf(decimalFormat.format(TApplication.curSiteValue.getSoilTemp()));
                if(TApplication.curSiteValue.getSoilTemp() > threshold.getUpTemp15cm()){          //高温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_high_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.high));
                    txtCurValue.setTextColor(getResources().getColor(R.color.high));
                    txtCheck.setTextColor(getResources().getColor(R.color.high));
                    str = "土壤湿度偏高";
                    str1 = "，请采取措施降低湿度";
                }else if(TApplication.curSiteValue.getSoilTemp() < threshold.getDownTemp15cm()){    //低温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_low_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.low));
                    txtCurValue.setTextColor(getResources().getColor(R.color.low));
                    txtCheck.setTextColor(getResources().getColor(R.color.low));
                    str = "土壤温度偏低";
                    str1 = "，请采取措施提高温度";
                }else {                                                         //正常
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_nomal_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.normal));
                    txtCurValue.setTextColor(getResources().getColor(R.color.normal));
                    txtCheck.setTextColor(getResources().getColor(R.color.normal));
                    str = "土壤温度正常";
                    str1 = "，请采注意保持";
                }
                str1 = "棚内当前土壤温度为"+TApplication.curSiteValue.getSoilTemp()+end+"，"+str+str1;
                break;
            case 4://光照
                start = "适宜光照";
                if(threshold != null){
                    mid = threshold.getDownSunlight()+"-"+threshold.getUpSunlight();
                }
                end = "Lx";
                value = String.valueOf(TApplication.curSiteValue.getIllu());
                if(TApplication.curSiteValue.getIllu() > threshold.getUpSunlight()){          //高温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_high_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.high));
                    txtCurValue.setTextColor(getResources().getColor(R.color.high));
                    txtCheck.setTextColor(getResources().getColor(R.color.high));
                    str = "光照太强";
                    str1 = "，请采取措施";
                }else if(TApplication.curSiteValue.getIllu() <threshold.getDownSunlight()){    //低温
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_low_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.low));
                    txtCurValue.setTextColor(getResources().getColor(R.color.low));
                    txtCheck.setTextColor(getResources().getColor(R.color.low));
                    str = "光照太弱";
                    str1 = "，请采取措施";
                }else {                                                         //正常
                    imgBgColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_nomal_color));
                    lineBgColor.setBackgroundColor(getResources().getColor(R.color.normal));
                    txtCurValue.setTextColor(getResources().getColor(R.color.normal));
                    txtCheck.setTextColor(getResources().getColor(R.color.normal));
                    str = "光照正常";
                    str1 = "，请采注意保持";
                }
                str1 = "棚内当前光照为"+TApplication.curSiteValue.getIllu()+end+"，"+str+str1;
                break;
        }

        txtCheck.setText(str);
        txtInfo.setText(str1);
        txtCurValue.setText(value+end);
        txtZone.setText(start+mid+end);
        txtCurTime.setText(TApplication.curSiteValue.getTimeInfo());//当前时间
        Log.i("RealTime","---------------refreshCurUi-------------");
        Log.i("RealTime","Temperature="+TApplication.curSiteValue.getTemperature()+",Humidity="+TApplication.curSiteValue.getHumidity()+",SoilTemp="+TApplication.curSiteValue.getSoilTemp()+",Illu="+TApplication.curSiteValue.getIllu());
        txtTempVal.setText(decimalFormat.format(TApplication.curSiteValue.getTemperature())+"℃");//当前空气温度
        txtHumidityVal.setText(decimalFormat.format(TApplication.curSiteValue.getHumidity())+"%");//当前空气湿度
        txtSoilVal.setText(decimalFormat.format(TApplication.curSiteValue.getSoilTemp())+"℃");//当前土壤温度
        txtIlluVal.setText(decimalFormat.format(TApplication.curSiteValue.getIllu())+"Lx");//当前光照
    }
}
