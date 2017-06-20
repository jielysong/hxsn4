package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.beans.CardInfo;
import com.snsoft.ctpf.beans.CropInfo;
import com.snsoft.ctpf.db.service.DBSearch;
import com.snsoft.ctpf.util.AndroidUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 农作物选择
 */
public class CropSelectActivity extends Activity {
    private String cityname;

    private List<CropInfo> clist = new ArrayList<>();
    private CardInfo cardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_select);
        cardInfo = new CardInfo();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("CropSelectActivity","onResume()");
        initdatas();
        findView();
    }

    private void initdatas(){
        if(TApplication.soilInfo != null){
            cityname = TApplication.soilInfo.getCounty();
            Log.i("CropSelectActivity","cityname="+cityname);
            String cityid = DBSearch.getInstance().getCountyidByname(cityname);
            cardInfo.setCountryname(cityname);
            cardInfo.setCountryid(cityid);
            cardInfo.setSoilinfo(TApplication.soilInfo);
            clist = DBSearch.getInstance().getCropInfoList(cityid);
        }else{
            AndroidUtil.show(this,"未获取");
        }

    }

    private void findView(){
        ListView listView = (ListView) findViewById(R.id.list_zwxz);
        String name="name";
        ArrayList<HashMap<String, String>> names = new ArrayList<>();

        for(int i= 0;i<clist.size();i++){
            HashMap<String, String> map = new HashMap<>();
            map.put(name, clist.get(i).getCropname());
            names.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, names, R.layout.list_item_text_only,
                new String[]{name}, new int[]{R.id.text_list_item_only});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapterview, View view, int i, long l){
                CropInfo cropInfo = clist.get(i);
                cardInfo.setCropinfo(cropInfo);
                TApplication.cardInfo = cardInfo;
                Intent it = new Intent();
                it.setClass(CropSelectActivity.this, TargetProduceActivity.class);
                startActivity(it);
            }
        });

        findViewById(R.id.btn_back_zwxz).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });
    }
}
