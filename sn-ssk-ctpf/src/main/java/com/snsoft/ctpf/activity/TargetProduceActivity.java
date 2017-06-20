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
import com.snsoft.ctpf.beans.CropInfo;
import com.snsoft.ctpf.beans.FertilizerInfo;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.dao.ParamService;
import com.snsoft.ctpf.db.service.DBSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 选择目标产量
 */
public class TargetProduceActivity extends Activity {


    private String[] dfss= new String[]{"尿素","硫酸钾","磷酸二铵"};
    private String[] zfss= new String[]{"尿素","硫酸钾"};

    //private CardInfo cardInfo;
    private List cllist = new ArrayList<Integer>();
    private ParamService param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_produce);
        param=new ParamService();

        if(TApplication.cardInfo != null){
            initdatas();
            findView();
        }
    }


    @Override
    protected void onResume(){
        super.onResume();

    }

    private void initdatas(){
        SoilInfo soilInfo = TApplication.cardInfo.getSoilinfo();
        CropInfo cropInfo = TApplication.cardInfo.getCropinfo();
        cllist = DBSearch.getInstance().getTargetYield(soilInfo, cropInfo);
    }

    private void findView(){
        ListView listView = (ListView) findViewById(R.id.list_clxz);
        String item01="chanl";
        ArrayList<HashMap<String, String>> names = new ArrayList<HashMap<String,String>>();
        for(int i= 0;i<cllist.size();i++){
            HashMap<String, String> map = new HashMap<>();
            String item = ""+cllist.get(i);
            map.put(item01, item + "公斤");
            names.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, names, R.layout.list_item_text_only,
                new String[]{item01}, new int[]{R.id.text_list_item_only});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView adapterview, View view, int i, long l){
                String chl = ""+cllist.get(i);
                int yield = Integer.parseInt(chl);
                TApplication.cardInfo.getCropinfo().setYield(yield);

                Intent intent = new Intent();

                boolean isJump = TApplication.sysParam.isJumpFer();
                if(isJump){//跳出肥料选择
                    TApplication.cardInfo.setBasef(dfss);
                    TApplication.cardInfo.setTopf(zfss);
                    List<FertilizerInfo> dflist =  DBSearch.getInstance()
                            .getFertilizerInfoList("1", "0", TApplication.cardInfo.getCountryid(),
                                    TApplication.cardInfo.getCropinfo().getCropid());
                    ArrayList<FertilizerInfo> zflist = (ArrayList<FertilizerInfo>) DBSearch.getInstance()
                            .getFertilizerInfoList("2", "0", TApplication.cardInfo.getCountryid(),
                                    TApplication.cardInfo.getCropinfo().getCropid());
                    ArrayList<String> dfstr = new ArrayList<String>();
                    ArrayList<String> zfstr = new ArrayList<String>();
                    for(FertilizerInfo fi : dflist){
                        if(fi.getRecommend()==1){
                            dfstr.add(fi.getFertilizerid());
                        }
                    }
                    for(FertilizerInfo fi : zflist){
                        if(fi.getRecommend()==1){
                            zfstr.add(fi.getFertilizerid());
                        }
                    }
                    String[] df = new String[dfstr.size()];
                    String[] zf = new String[zfstr.size()];
                    dfstr.toArray(df);
                    zfstr.toArray(zf);
                    Log.i("TargetProduceActivity","df.length="+df.length);
                    Log.i("TargetProduceActivity","df"+df);
                    TApplication.cardInfo.setBasef(df);
                    TApplication.cardInfo.setTopf(zf);
                    //it.putExtras(b);
                    intent.setClass(TargetProduceActivity.this, FertilizerSuggestActivity.class);
                }else{
                    intent.setClass(TargetProduceActivity.this, FertilizerSelectActivity.class);
                }

                startActivity(intent);

            }

        });
        findViewById(R.id.btn_back_mbcl).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });
    }
}
