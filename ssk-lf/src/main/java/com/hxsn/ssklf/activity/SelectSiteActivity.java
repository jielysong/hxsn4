package com.hxsn.ssklf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxsn.library.db.Shared;
import com.hxsn.library.http.HttpRequest;
import com.hxsn.ssklf.R;
import com.hxsn.ssklf.TApplication;
import com.hxsn.ssklf.adapter.SiteListAdapter;
import com.hxsn.ssklf.base.BaseTitle;
import com.hxsn.ssklf.model.SiteInfo;
import com.hxsn.ssklf.utils.Const;
import com.hxsn.ssklf.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectSiteActivity extends Activity implements View.OnClickListener{

    //private TextView txtSelect1,txtSelect2,txtSelect3,txtSelect4,txtSelect5,txtSelect6,txtSelect7,txtSelect8,txtSelect9,txtSelect10;
    private List<SiteInfo> sites = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_site);
        addView();
        //addListener();

        BaseTitle.getInstance(this).setTitle("选择气象哨");

        //获取气象哨列表
        obtainSiteList();
    }


    /**
     * 网络获取实时数据，并刷新
     */
    private void obtainSiteList() {
        String url = Const.URL_GET_SITES;
        new HttpRequest(this) {
            @Override
            public void getResponse(String response) {
                if(JsonUtil.getStatus(response)){
                    sites = JsonUtil.getSiteInfoList(response);
                    if(sites.size() > 0){
                       // TApplication.siteInfoList = sites;
                        SiteListAdapter adapter = new SiteListAdapter(SelectSiteActivity.this, sites);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TApplication.curSiteInfo = sites.get(position);
                                Shared.setValue("siteName",sites.get(position).getName());
                                Shared.setValue("siteUUid",sites.get(position).getUuid());
                                Intent intent = new Intent();
                                intent.setClass(SelectSiteActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        // refreshUI();
                    }
                }
            }
        }.doMyGet(url);
    }

   /* private void refreshUI() {
        TextView[] txtSeelectArray = {txtSelect1,txtSelect2,txtSelect3,txtSelect4,txtSelect5,txtSelect6,txtSelect7,txtSelect8,txtSelect9,txtSelect10};
        for(int i=0; i<10; i++){
            txtSeelectArray[i].setText(sites.get(i).getName());
        }
    }*/

    private void addView() {
        listView = (ListView)findViewById(R.id.list);
       /* txtSelect1 = (TextView)findViewById(R.id.txt_select1);
        txtSelect2 = (TextView)findViewById(R.id.txt_select2);
        txtSelect3 = (TextView)findViewById(R.id.txt_select3);
        txtSelect4 = (TextView)findViewById(R.id.txt_select4);
        txtSelect5 = (TextView)findViewById(R.id.txt_select5);
        txtSelect6 = (TextView)findViewById(R.id.txt_select6);
        txtSelect7 = (TextView)findViewById(R.id.txt_select7);
        txtSelect8 = (TextView)findViewById(R.id.txt_select8);
        txtSelect9 = (TextView)findViewById(R.id.txt_select9);
        txtSelect10 = (TextView)findViewById(R.id.txt_select10);*/
        //txtSelect11 = (TextView)findViewById(R.id.txt_select11);

    }

   /* private void addListener() {
        txtSelect1.setOnClickListener(this);
        txtSelect2.setOnClickListener(this);
        txtSelect3.setOnClickListener(this);
        txtSelect4.setOnClickListener(this);
        txtSelect5.setOnClickListener(this);
        txtSelect6.setOnClickListener(this);
        txtSelect7.setOnClickListener(this);
        txtSelect8.setOnClickListener(this);
        txtSelect9.setOnClickListener(this);
        txtSelect10.setOnClickListener(this);


    }*/

    @Override

    public void onClick(View v) {

       /* Intent intent = new Intent();
        intent.setClass(this,HomeActivity.class);
        switch (v.getId()){
            case R.id.txt_select1:
                TApplication.uuidIndex = 0;

                break;
            case R.id.txt_select2:
                TApplication.uuidIndex = 1;

                break;
            case R.id.txt_select3:
                TApplication.uuidIndex = 2;

                break;
            case R.id.txt_select4:
                TApplication.uuidIndex = 3;

                break;
            case R.id.txt_select5:
                TApplication.uuidIndex = 4;

                break;
            case R.id.txt_select6:
                TApplication.uuidIndex = 5;

                break;
            case R.id.txt_select7:
                TApplication.uuidIndex = 6;

                break;
            case R.id.txt_select8:
                TApplication.uuidIndex = 7;

                break;
            case R.id.txt_select9:
                TApplication.uuidIndex = 8;

                break;
            case R.id.txt_select10:
                TApplication.uuidIndex = 9;

                break;
            case R.id.txt_select11:
                TApplication.uuidIndex = 10;

                break;
        }
        startActivity(intent);
        finish();*/
    }

}
