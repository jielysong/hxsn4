package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snsoft.ctpf.R;
import com.snsoft.ctpf.TApplication;
import com.snsoft.ctpf.beans.CardInfo;
import com.snsoft.ctpf.beans.FertilizerInfo;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.DbManager;
import com.snsoft.ctpf.db.dao.FertilizePlanService;
import com.snsoft.ctpf.db.dao.SoilInfoService;
import com.snsoft.ctpf.db.service.CtpfFacade;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 施肥建议卡
 */
public class FertilizerSuggestActivity extends Activity {


    private SoilInfo sinf;
    private SoilInfoService soilServcie = new SoilInfoService();

    private Button savejyk;
    private CardInfo cardInfo;

    //自定义布局的控件
    private TextView btn1;
    private TextView btn2;
    private EditText editText;

    private SQLiteDatabase database;

    private String mCropName = "";
    private String mYield = "";
    private String mHistory="";
    private String mDFOne = "";
    private String mZFOne = "";
    private String mDFTwo = "";
    private String mZFTwo = "";
    private String mFertilizers = "";
    private String mTopdressing = "";
    private String mMicro = "";
    private String mCountry = "";
    private String mTime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_suggest);
        cardInfo = TApplication.cardInfo;
        initdatas();
        findView();
    }


    private void initdatas(){
        CtpfFacade facade = new CtpfFacade();
        cardInfo.setCropnutrientinfo(facade.getCropNutrientInfo( cardInfo.getSoilinfo(), cardInfo.getCropinfo(), false));
        cardInfo.Calculation();
    }

    private void findView(){

        LayoutInflater inflater = LayoutInflater.from(this);
        TextView itm1 = (TextView) findViewById(R.id.text_item_zhzhzw);
        mCropName = cardInfo.getCropinfo().getCropname();
        itm1.setText(mCropName);
        TextView itm2 = (TextView) findViewById(R.id.text_item_mbcl);
        mYield = cardInfo.getCropinfo().getYield()+"公斤/亩";
        itm2.setText(mYield);
        //土壤成分
        LinearLayout soilComponents = (LinearLayout)findViewById(R.id.soil_components);
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


       // FertilizePlanService basefl0 = cardInfo.getBasefl()[0];
        FertilizePlanService[] fs = cardInfo.getBasefl();
        FertilizePlanService basefl0 = fs[0];
        Log.i("FertilizerSuggest","basef1.length="+cardInfo.getBasefl().length);
        while(basefl0.hasMore()){
            FertilizerInfo mo=(FertilizerInfo)basefl0.next();
            if (mo.getDosage()>0.1){
                View view = inflater.inflate(R.layout.view_item_difeijianyi, null);
                TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
                textview.setText(mo.getShortname()+mo.getFmtDosage()+"公斤/亩");
                layout1.addView(view);
                mDFOne = mDFOne + (mo.getShortname()+mo.getFmtDosage()+"公斤/亩"+";");
            }
        }

        FertilizePlanService topfl0 = cardInfo.getTopfl()[0];
        while(topfl0.hasMore()){
            FertilizerInfo mo=(FertilizerInfo)topfl0.next();
            if (mo.getDosage()>0.1){
                View view = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
                TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
                textview.setText(mo.getShortname()+mo.getFmtDosage()+"公斤/亩");
                layout2.addView(view);
                mZFOne = mZFOne + (mo.getShortname()+mo.getFmtDosage()+"公斤/亩") + ";";
            }
        }
        if ((layout1.getChildCount()+layout2.getChildCount()) == 0) {
            layout_one.setVisibility(View.GONE);
        }

        FertilizePlanService basefl1 = cardInfo.getBasefl()[1];
        while(basefl1.hasMore()){
            FertilizerInfo fertilizerInfo=(FertilizerInfo)basefl1.next();
            if (fertilizerInfo.getDosage()>0.1){
                View view = inflater.inflate(R.layout.view_item_difeijianyi, null);
                TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
                textview.setText(fertilizerInfo.getShortname()+fertilizerInfo.getFmtDosage()+"公斤/亩");
                layout3.addView(view);
                mDFTwo = mDFTwo + (fertilizerInfo.getShortname()+fertilizerInfo.getFmtDosage()+"公斤/亩") + ";";
            }
        }

        FertilizePlanService topfl1 = cardInfo.getTopfl()[1];
        while(topfl1.hasMore()){
            FertilizerInfo mo=(FertilizerInfo)topfl1.next();
            if (mo.getDosage()>0.1){
                View view = inflater.inflate(R.layout.view_item_zhuifeijianyi, null);
                TextView textview = (TextView) view.findViewById(R.id.text_item_fei);
                textview.setText(mo.getShortname()+mo.getFmtDosage()+"公斤/亩");
                layout4.addView(view);
                mZFTwo = mZFTwo + (mo.getShortname()+mo.getFmtDosage()+"公斤/亩") + ";";
            }
        }
        if ((layout3.getChildCount()+layout4.getChildCount()) == 0) {
            layout_two.setVisibility(View.GONE);
        }

        mFertilizers = cardInfo.getCropinfo().getFertilizers();
        if (mFertilizers.equals("") || mFertilizers == null) {
            layoutDfSuggest.setVisibility(View.GONE);
        } else {
            TextView tishidifei = (TextView) findViewById(R.id.text_tishi_difei);
            tishidifei.setText(mFertilizers);
        }

        mTopdressing = cardInfo.getCropinfo().getTopdressing();
        if (mTopdressing.equals("") || mTopdressing == null) {
            layoutZfSuggest.setVisibility(View.GONE);
        } else {
            TextView tishizhuifei = (TextView) findViewById(R.id.text_tishi_zhuifei);
            tishizhuifei.setText(mTopdressing);
        }

        mMicro = cardInfo.getCropinfo().getMicro();
        if (mMicro.equals("") || mMicro == null) {
            layoutWfSuggest.setVisibility(View.GONE);
        } else {
            TextView tishiweifei = (TextView) findViewById(R.id.text_tishi_weifei);
            tishiweifei.setText(mMicro);
        }

        if (mFertilizers.equals("") && mTopdressing.equals("") && mMicro.equals("")) {
            layoutDataMechod.setVisibility(View.GONE);
        }

        mCountry = cardInfo.getCountry();
        sinf = cardInfo.getSoilinfo();
        //解决小数点位数过多的问题
        DecimalFormat df = new DecimalFormat("#.00");
        //Log.v(TAG, sinf.getN() + "");
        sinf.setK(Double.parseDouble(df.format(sinf.getK())));
        sinf.setP(Double.parseDouble(df.format(sinf.getP())));
        sinf.setN(Double.parseDouble(df.format(sinf.getN())));
        sinf.setOrganic(Double.parseDouble(df.format(sinf.getOrganic())));


        //增加土壤成分信息
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

        findViewById(R.id.btn_back_sfjyk).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });

        savejyk= (Button) findViewById(R.id.btn_sfjyk);
        savejyk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                dialog();
                savejyk.setClickable(false);
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
    }

    private String getShareMethod(){
        StringBuffer sb = new StringBuffer();
        sb.append("测土配方施肥建议\n");
        sb.append( "种植作物："+mCropName+"\n");
        sb.append("目标产量："+mYield+"\n");
        sb.append("土壤养分测试数据：\n");
        sb.append("有机质：" + sinf.getOrganic() + "g/kg\n");
        sb.append("碱解氮：" + sinf.getN()+ "mg/kg\n");
        sb.append("有效磷：" + sinf.getP()+ "mg/kg\n");
        sb.append("速效钾：" + sinf.getK()+ "mg/kg\n");
        if(!mDFOne.equals("")&&!mZFOne.equals("")){
            sb.append("施肥方案一：\n");
        }

        List<String> listDFOne = String2Array(mDFOne);
        for (int i = 0; i < listDFOne.size(); i++) {
            sb.append("底肥："+listDFOne.get(i)+"\n");
        }
        List<String> listZFOne = String2Array(mZFOne);
        for (int i = 0; i < listZFOne.size(); i++) {
            sb.append("追肥："+listZFOne.get(i)+"\n");
        }
        if(!mDFTwo.equals("")&&!mZFTwo.equals("")){
            sb.append("施肥方案二：\n");
        }
        List<String> listDFTwo = String2Array(mDFTwo);
        for (int i = 0; i < listDFTwo.size(); i++) {
            sb.append("底肥："+listDFTwo.get(i)+"\n");
        }
        List<String> listZFTwo = String2Array(mZFTwo);
        for (int i = 0; i < listZFTwo.size(); i++) {
            sb.append("追肥："+listZFTwo.get(i)+"\n");
        }
        sb.append("施肥时期和方法提示："+"\n");
        if(!mFertilizers.equals("")){
            sb.append("底肥："+mFertilizers+"\n");
        }
        if(!mTopdressing.equals("")){
            sb.append("追肥："+mTopdressing+"\n");
        }
        if(!mMicro.equals("")){
            sb.append("微肥："+mMicro+"\n");
        }
        return sb.toString();
    }

    private List<String> String2Array (String str) {
        List<String> list = new ArrayList<String>();
        if (!str.equals("") && str != null) {
            str = str.substring(0, str.length()-1);
            String[] strarray=str.split(";");
            for (int i1 = 0; i1 < strarray.length; i1++){
                list.add(strarray[i1]);
            }
        }

        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        savejyk.setVisibility(View.VISIBLE);
    }

    private void dialog(){

        LayoutInflater inflater=getLayoutInflater();
        View layout=inflater.inflate(R.layout.activity_dialog, null);
        btn1=(TextView) layout.findViewById(R.id.dialog_button_ok);
        btn2=(TextView) layout.findViewById(R.id.dialog_button_cancel);
        editText=(EditText) layout.findViewById(R.id.dialog_edittext);
        editText.setText("目标产量"+mYield+"("+mCropName+")");

        final Dialog dialog =new Dialog(this,R.style.dialog) ;
        dialog.setContentView(layout);
        dialog.show();

        btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mHistory=editText.getText().toString();
                createTable();
                insert2();
                dialog.dismiss();
                Toast.makeText(FertilizerSuggestActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
            }

        });
        btn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        savejyk.setVisibility(View.GONE);
    }

    protected void createTable() {
        database = DbManager.getInstance().getHistoryDatabase();
        String createTable = "create table if not exists history(id integer primary key autoincrement,name text not null,mhistory text not null,yield text,dfone text,zfone text,dftwo text,zftwo text," +
                "moddf text,modzf text,modwf text,country text,time text, n double, p double, k double, o double)";
        database.execSQL(createTable);
    }

    private void insert2() {
        mTime = getDate();
        ContentValues values = new ContentValues();
        values.put("name", mCropName);
        values.put("mhistory", mHistory);
        values.put("yield", mYield);
        values.put("dfone", mDFOne);
        values.put("zfone", mZFOne);
        values.put("dftwo", mDFTwo);
        values.put("zftwo", mZFTwo);
        values.put("moddf", mFertilizers);
        values.put("modzf", mTopdressing);
        values.put("modwf", mMicro);
        values.put("country", mCountry);
        values.put("time", mTime);
        values.put("o", sinf.getOrganic());
        values.put("k", sinf.getK());
        values.put("p", sinf.getP());
        values.put("n", sinf.getN());
        database.insert("history", null, values);
        DbManager.getInstance().closeHistoryDatabase();
    }

    private String getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
