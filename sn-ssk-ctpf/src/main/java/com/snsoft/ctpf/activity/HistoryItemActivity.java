package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.ssk.R;
import com.snsoft.ctpf.beans.HistoryNote;
import com.snsoft.ctpf.beans.SoilInfo;

import java.util.List;

public class HistoryItemActivity extends Activity {

    private HistoryNote mHistoryNote;
    private SoilInfo sinf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item);


        Bundle bundle = getIntent().getExtras();
        mHistoryNote = (HistoryNote) bundle.getSerializable("history");
        sinf = new SoilInfo();
        sinf.setK(mHistoryNote.getK());
        sinf.setOrganic(mHistoryNote.getO());
        sinf.setN(mHistoryNote.getN());
        sinf.setP(mHistoryNote.getP());
        findView();
    }

    private void findView(){
        LayoutInflater inflater = LayoutInflater.from(this);
        TextView itm1 = (TextView) findViewById(R.id.text_item_zhzhzw);
        itm1.setText(mHistoryNote.getmName());
        TextView itm2 = (TextView) findViewById(R.id.text_item_mbcl);
        itm2.setText(mHistoryNote.getmYield());
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout_difei1_one);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout_zhuifei1_one);
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout_difei1_two);
        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout_zhuifei1_two);
        LinearLayout layout_one = (LinearLayout) findViewById(R.id.sffa_one_title);
        LinearLayout layout_two = (LinearLayout) findViewById(R.id.sffa_two_title);
        LinearLayout layoutDataMechod = (LinearLayout) findViewById(R.id.data_mechod_title);
        LinearLayout layoutDfSuggest = (LinearLayout) findViewById(R.id.linearlayout_df_suggest);
        LinearLayout layoutZfSuggest = (LinearLayout) findViewById(R.id.linearlayout_zf_suggest);
        LinearLayout layoutWfSuggest = (LinearLayout) findViewById(R.id.linearlayout_wf_suggest);
        RelativeLayout mToplayout = (RelativeLayout) findViewById(R.id.layout_yf_bar);
        mToplayout.setVisibility(View.GONE);

        for (int i = 0; i < mHistoryNote.getmDFOne().size(); i ++) {
            View view = inflater.inflate(R.layout.view_item_difeijianyi, null);
            TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
            textview.setText(mHistoryNote.getmDFOne().get(i));
            layout1.addView(view);
        }

        for (int i = 0; i < mHistoryNote.getmZFOne().size(); i ++) {
            View view = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
            TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
            textview.setText(mHistoryNote.getmZFOne().get(i));
            layout2.addView(view);
        }

        if ((layout1.getChildCount()+layout2.getChildCount()) == 0) {
            layout_one.setVisibility(View.GONE);
        }

        for (int i = 0; i < mHistoryNote.getmDFTwo().size(); i ++) {
            View view = inflater.inflate(R.layout.view_item_difeijianyi, null);
            TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
            textview.setText(mHistoryNote.getmDFTwo().get(i));
            layout3.addView(view);
        }

        for (int i = 0; i < mHistoryNote.getmZFTwo().size(); i ++) {
            View view = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
            TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
            textview.setText(mHistoryNote.getmZFTwo().get(i));
            layout4.addView(view);
        }

        if ((layout3.getChildCount()+layout4.getChildCount()) == 0) {
            layout_two.setVisibility(View.GONE);
        }

        String mFertilizers = mHistoryNote.getmFertilizers();
        if (mFertilizers.equals("") || mFertilizers == null) {
            layoutDfSuggest.setVisibility(View.GONE);
        } else {
            TextView tishidifei = (TextView) findViewById(R.id.text_tishi_difei);
            tishidifei.setText(mFertilizers);
        }

        String mTopdressing = mHistoryNote.getmTopdressing();
        if (mTopdressing.equals("") || mTopdressing == null) {
            layoutZfSuggest.setVisibility(View.GONE);
        } else {
            TextView tishizhuifei = (TextView) findViewById(R.id.text_tishi_zhuifei);
            tishizhuifei.setText(mTopdressing);
        }

        String mMicro = mHistoryNote.getmMicro();
        if (mMicro.equals("") || mMicro == null) {
            layoutWfSuggest.setVisibility(View.GONE);
        } else {
            TextView tishiweifei = (TextView) findViewById(R.id.text_tishi_weifei);
            tishiweifei.setText(mMicro);
        }

        if (mFertilizers.equals("") && mTopdressing.equals("") && mMicro.equals("")) {
            layoutDataMechod.setVisibility(View.GONE);
        }

        //增加土壤成分信息
        //土壤成分
        LinearLayout soilComponents = (LinearLayout)findViewById(R.id.soil_components);
        View cview = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
        TextView title = (TextView)cview.findViewById(R.id.text_item_title_jg03);
        TextView content = (TextView)cview.findViewById(R.id.text_item_fei);
        title.setText("有机质：");
        content.setText(sinf.getOrganic()+"g/kg");
        soilComponents.addView(cview);
        cview = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
        title = (TextView)cview.findViewById(R.id.text_item_title_jg03);
        content = (TextView)cview.findViewById(R.id.text_item_fei);
        title.setText("碱解氮：");
        content.setText(sinf.getN()+"mg/kg");
        soilComponents.addView(cview);
        cview = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
        title = (TextView)cview.findViewById(R.id.text_item_title_jg03);
        content = (TextView)cview.findViewById(R.id.text_item_fei);
        title.setText("有效磷：");
        content.setText(sinf.getP()+"mg/kg");
        soilComponents.addView(cview);
        cview = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
        title = (TextView)cview.findViewById(R.id.text_item_title_jg03);
        content = (TextView)cview.findViewById(R.id.text_item_fei);
        title.setText("速效钾：");
        content.setText(sinf.getK()+"mg/kg");
        soilComponents.addView(cview);

        findViewById(R.id.btn_back_sfjyk).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        final String shareStr = getShareMethod();
        findViewById(R.id.btn_share_jyk).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, shareStr);
                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });
        findViewById(R.id.btn_sfjyk).setVisibility(View.GONE);
    }
    private String getShareMethod(){
        StringBuffer sb = new StringBuffer();
        sb.append("测土配方施肥建议\n");
        sb.append( "种植作物："+mHistoryNote.getmName()+"\n");
        sb.append("目标产量："+mHistoryNote.getmYield()+"\n");
        sb.append("土壤养分测试数据：\n");
        sb.append("有机质：" + sinf.getOrganic() + "g/kg\n");
        sb.append("碱解氮：" + sinf.getN()+ "mg/kg\n");
        sb.append("有效磷：" + sinf.getP()+ "mg/kg\n");
        sb.append("速效钾：" + sinf.getK()+ "mg/kg\n");
        if(!(mHistoryNote.getmDFOne().size()==0)&&!(mHistoryNote.getmZFOne().size()==0)){
            sb.append("施肥方案一：\n");
        }

        List<String> listDFOne = mHistoryNote.getmDFOne();
        for (int i = 0; i < listDFOne.size(); i++) {
            sb.append("底肥："+listDFOne.get(i)+"\n");
        }
        List<String> listZFOne = mHistoryNote.getmZFOne();
        for (int i = 0; i < listZFOne.size(); i++) {
            sb.append("追肥："+listZFOne.get(i)+"\n");
        }
        if(!(mHistoryNote.getmDFTwo().size()==0)&&!(mHistoryNote.getmZFTwo().size()==0)){
            sb.append("施肥方案二：\n");
        }
        List<String> listDFTwo = mHistoryNote.getmDFTwo();
        for (int i = 0; i < listDFTwo.size(); i++) {
            sb.append("底肥："+listDFTwo.get(i)+"\n");
        }
        List<String> listZFTwo = mHistoryNote.getmZFTwo();
        for (int i = 0; i < listZFTwo.size(); i++) {
            sb.append("追肥："+listZFTwo.get(i)+"\n");
        }
        sb.append("施肥时期和方法提示："+"\n");
        if(!mHistoryNote.getmFertilizers().equals("")){
            sb.append("底肥："+mHistoryNote.getmFertilizers()+"\n");
        }
        if(!mHistoryNote.getmTopdressing().equals("")){
            sb.append("追肥："+mHistoryNote.getmTopdressing()+"\n");
        }
        if(!mHistoryNote.getmMicro().equals("")){
            sb.append("微肥："+mHistoryNote.getmMicro()+"\n");
        }
        return sb.toString();
    }
}
