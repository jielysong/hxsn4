package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snsoft.ctpf.R;
import com.snsoft.ctpf.TApplication;
import com.snsoft.ctpf.beans.CardInfo;
import com.snsoft.ctpf.beans.FertilizerInfo;
import com.snsoft.ctpf.db.service.DBSearch;

import java.util.ArrayList;

/**
 * 施肥选择
 */
public class FertilizerSelectActivity extends Activity {

    //private Intent fromIntent;

    private CardInfo cardInfo;
    private ArrayList<FertilizerInfo> dflist = new ArrayList<FertilizerInfo>();
    private ArrayList<FertilizerInfo> zflist = new ArrayList<FertilizerInfo>();

    private ArrayList<CheckBox> dfcBoxs = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> zfcBoxs = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertllize_select);
        initdatas();
        findView();
    }

    private void initdatas(){
        dflist = (ArrayList<FertilizerInfo>) DBSearch.getInstance().getFertilizerInfoList("1", "0",
                TApplication.cardInfo.getCountryid(),TApplication.cardInfo.getCropinfo().getCropid());

        zflist = (ArrayList<FertilizerInfo>) DBSearch.getInstance().getFertilizerInfoList("2", "0",
                TApplication.cardInfo.getCountryid(), TApplication.cardInfo.getCropinfo().getCropid());

    }

    private void findView(){
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout01 = (LinearLayout) findViewById(R.id.layout_group_difei);
        for(int i=0;i<dflist.size();i++){
            FertilizerInfo fertilizerInfo = dflist.get(i);
            View viewchek = inflater.inflate(R.layout.item_chkbx_sf, null);
            TextView tv_chek = (TextView) viewchek.findViewById(R.id.text_chkbx_title);
            CheckBox chkbox = (CheckBox) viewchek.findViewById(R.id.chkbx_01);
            int recommend = fertilizerInfo.getRecommend();
            if(1== recommend){
                chkbox.setChecked(true);
            }
            dfcBoxs.add(chkbox);
            tv_chek.setText(fertilizerInfo.getShortname());
            layout01.addView(viewchek);
        }
        LinearLayout layout02 = (LinearLayout) findViewById(R.id.layout_group_zuifei);
        for(int i=0;i<zflist.size();i++){
            FertilizerInfo fertilizerInfo = zflist.get(i);
            View viewchek = inflater.inflate(R.layout.item_chkbx_sf, null);
            TextView tv_chek = (TextView) viewchek.findViewById(R.id.text_chkbx_title);
            CheckBox chkbox = (CheckBox) viewchek.findViewById(R.id.chkbx_01);
            int recommend = fertilizerInfo.getRecommend();
            if(1== recommend){
                chkbox.setChecked(true);
            }
            zfcBoxs.add(chkbox);
            tv_chek.setText(fertilizerInfo.getShortname());
            layout02.addView(viewchek);
        }
        findViewById(R.id.btn_sfyl).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                setUserChecked();

                Intent it = new Intent();
                it.setClass(FertilizerSelectActivity.this, FertilizerSuggestActivity.class);
                startActivity(it);
            }
        });

        findViewById(R.id.btn_back_sfyl).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });
    }

    private void setUserChecked(){
        ArrayList<String> dfstr = new ArrayList<String>();
        ArrayList<String> zfstr = new ArrayList<String>();
        for(int i=0;i<dfcBoxs.size();i++){
            CheckBox box = dfcBoxs.get(i);
            if(box.isChecked()){
                FertilizerInfo fi =dflist.get(i);
                dfstr.add(fi.getFertilizerid());
            }
        }
        for(int i=0;i<zfcBoxs.size();i++){
            CheckBox box = zfcBoxs.get(i);
            if(box.isChecked()){
                FertilizerInfo fi =zflist.get(i);
                zfstr.add(fi.getFertilizerid());
            }
        }
        String[] dfss= new String[1];
        String[] zfss= new String[1];
        if(0<dfstr.size()){
            dfss= new String[dfstr.size()];
        }
        if(0<zfstr.size()){
            zfss= new String[zfstr.size()];
        }
        for(int i=0;i<dfstr.size();i++){
            dfss[i]= dfstr.get(i);
        }
        for(int i=0;i<zfstr.size();i++){
            zfss[i]= zfstr.get(i);
        }
        TApplication.cardInfo.setBasef(dfss);
        TApplication.cardInfo.setTopf(zfss);
    }
}
