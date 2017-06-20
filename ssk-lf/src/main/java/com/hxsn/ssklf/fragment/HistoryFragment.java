package com.hxsn.ssklf.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxsn.library.http.HttpRequest;
import com.hxsn.ssklf.R;
import com.hxsn.ssklf.TApplication;
import com.hxsn.ssklf.adapter.HistoryRecyclerAdapter;
import com.hxsn.ssklf.model.SiteValue;
import com.hxsn.ssklf.ui.DividerItemDecoration;
import com.hxsn.ssklf.utils.Const;
import com.hxsn.ssklf.utils.JsonUtil;

import java.util.List;

/**
 * 查看历史数据页面
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class HistoryFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private  List<SiteValue> siteList;

    private RecyclerView recyclerView;
    private TextView txtToday,txtYesterday,txtPreYesterday;
    private int mode = 1;


    public HistoryFragment() {
    }

    public HistoryFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.in_history, container, false);
        addView(view);

        //获取历史数据
        obtainHistoryData(0);


        return view;
    }

    private void obtainHistoryData(int dateType) {
        String url = Const.URL_HISTORY+TApplication.curSiteInfo.getUuid()+"&date="+dateType;
        //String url = Const.URL_HISTORY+TApplication.siteInfoList.get(TApplication.uuidIndex).getUuid()+"&date="+dateType;
        new HttpRequest(context) {
            @Override
            public void getResponse(String response) {
                if(JsonUtil.getStatus(response)){
                    siteList = JsonUtil.getSiteList(response);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(layoutManager);
                    //添加分割线
                    recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));
                    HistoryRecyclerAdapter adapter = new HistoryRecyclerAdapter(siteList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }.doMyGet(url);
    }

    private void addView( View view){
        //设置表格标题的背景颜色
        ViewGroup tableTitle = (ViewGroup) view.findViewById(R.id.table_title);
        tableTitle.setBackgroundColor(getResources().getColor(R.color.gray_light_s));
        //tableListView = (ListView) view.findViewById(R.id.list);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //txtToday,txtYesterday,txtPreYesterday
        txtToday = (TextView)view.findViewById(R.id.txt_today);
        txtYesterday = (TextView)view.findViewById(R.id.txt_yesterday);
        txtPreYesterday = (TextView)view.findViewById(R.id.txt_pre_yesterday);
        txtToday.setOnClickListener(this);
        txtYesterday.setOnClickListener(this);
        txtPreYesterday.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.txt_today://今天的历史数据
                if(mode != 1){
                    clearTxtView();
                    txtToday.setTextColor(getResources().getColor(R.color.white));
                    txtToday.setBackground(getResources().getDrawable(R.drawable.bg_text_sky));
                    mode = 1;
                    //获取历史数据
                    obtainHistoryData(0);
                }
                break;
            case R.id.txt_yesterday://昨天的历史数据
                if(mode != 2){
                    clearTxtView();
                    txtYesterday.setTextColor(getResources().getColor(R.color.white));
                    txtYesterday.setBackground(getResources().getDrawable(R.drawable.bg_text_sky));
                    mode = 2;
                    //获取历史数据
                    obtainHistoryData(1);
                }
                break;
            case R.id.txt_pre_yesterday://前天的历史数据
                if(mode != 3){
                    clearTxtView();
                    txtPreYesterday.setTextColor(getResources().getColor(R.color.white));
                    txtPreYesterday.setBackground(getResources().getDrawable(R.drawable.bg_text_sky));
                    mode = 3;
                    //获取历史数据
                    obtainHistoryData(2);
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void clearTxtView(){
        txtToday.setTextColor(getResources().getColor(R.color.black_text_n));
        txtToday.setBackground(getResources().getDrawable(R.drawable.bg_text_gray));
        txtYesterday.setTextColor(getResources().getColor(R.color.black_text_n));
        txtYesterday.setBackground(getResources().getDrawable(R.drawable.bg_text_gray));
        txtPreYesterday.setTextColor(getResources().getColor(R.color.black_text_n));
        txtPreYesterday.setBackground(getResources().getDrawable(R.drawable.bg_text_gray));
    }
}
