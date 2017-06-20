package com.snsoft.ctpf.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.snsoft.ctpf.R;
import com.snsoft.ctpf.TApplication;
import com.snsoft.ctpf.activity.CropSelectActivity;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.service.DBSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * 养分
 */
@SuppressLint("ValidFragment")
public class YangFenFragment extends Fragment {

    private EditText et_yjz ,et_jjd ,et_yxl ,et_sxj;
    private RadioGroup rg_soilTexture, rg_landform;
    private RadioButton rb_soilType_qr,rb_soilType_mr,rb_soilType_zr;
    private RadioButton rb_landform_py,rb_landform_ql,rb_landform_zs;
    private Spinner sp_county;
    private LinearLayout countyLl;
    //private SoilInfo sinfo;
    private String mSoilType;//土壤质地
    private String mGeomorType;//地貌类型
    private List<String> nameslist;
    private String cityName;
    private Spinner sp_tuzhi;
    private Spinner sp_dimao;

    private Context context;

    public YangFenFragment(Context context) {
        this.context = context;
    }

    public YangFenFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yang_fen, container, false);

        if(TApplication.soilInfo == null){
            TApplication.soilInfo = new SoilInfo();
        }
        cityName = TApplication.city;
        findView(view);
        initData();

        return view;
    }

    private void findView(View view){
        et_yjz = (EditText)view.findViewById(R.id.et_yf_yjz);
        et_jjd = (EditText) view.findViewById(R.id.et_yf_jjd);
        et_yxl = (EditText) view.findViewById(R.id.et_yf_yxl);
        et_sxj = (EditText) view.findViewById(R.id.et_yf_sxj);
        rg_soilTexture = (RadioGroup) view.findViewById(R.id.rg_yf_trzd);
        rb_soilType_qr = (RadioButton) view.findViewById(R.id.rb_qr_trzd);
        rb_soilType_mr = (RadioButton) view.findViewById(R.id.rb_mr_trzd);
        rb_soilType_zr = (RadioButton) view.findViewById(R.id.rb_zr_trzd);
        rg_landform = (RadioGroup) view.findViewById(R.id.rg_dm_dmlx);
        rb_landform_py = (RadioButton) view.findViewById(R.id.rb_py_dmlx);
        rb_landform_ql = (RadioButton) view.findViewById(R.id.rb_ql_dmlx);
        rb_landform_zs = (RadioButton) view.findViewById(R.id.rb_zs_dmlx);
        sp_county = (Spinner) view.findViewById(R.id.sp_county);
        countyLl = (LinearLayout) view.findViewById(R.id.county_linearlayout);

        sp_dimao = (Spinner)view.findViewById(R.id.dm);
        sp_tuzhi = (Spinner)view.findViewById(R.id.tz);

        mSoilType = rb_soilType_qr.getText().toString();

        rg_soilTexture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == rb_soilType_qr.getId()){
                    mSoilType = rb_soilType_qr.getText().toString();
                } else if(checkedId == rb_soilType_mr.getId()){
                    mSoilType = rb_soilType_mr.getText().toString();
                } else {
                    mSoilType = rb_soilType_zr.getText().toString();
                }
            }
        });

        mGeomorType = rb_landform_py.getText().toString();

        rg_landform.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == rb_landform_py.getId()){
                    mGeomorType = rb_landform_py.getText().toString();
                } else if(checkedId == rb_landform_ql.getId()){
                    mGeomorType = rb_landform_ql.getText().toString();
                } else {
                    mGeomorType = rb_landform_zs.getText().toString();
                }
            }
        });

        sp_county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                cityName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        view.findViewById(R.id.btn_yfhl).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if(setSoil()){
                    Intent it = new Intent();
                    TApplication.soilInfo.setCounty(cityName);
                    it.setClass(context, CropSelectActivity.class);
                    startActivity(it);
                }

            }
        });
    }

    private void initData(){
        nameslist = new ArrayList<>();
        String cityname = DBSearch.getInstance().getCountyName();
        String countyType = DBSearch.getInstance().getCountyType(cityname);
        final ArrayAdapter<CharSequence> tuzhiArrayAdapter = ArrayAdapter.createFromResource(context, R.array.tz, android.R.layout.simple_spinner_item);
        tuzhiArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tuzhi.setAdapter(tuzhiArrayAdapter);
        final ArrayAdapter<CharSequence> dimaoArrayAdapter = ArrayAdapter.createFromResource(context, R.array.dm, android.R.layout.simple_spinner_item);
        dimaoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dimao.setAdapter(dimaoArrayAdapter);
        sp_tuzhi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                mSoilType= tuzhiArrayAdapter.getItem(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        sp_dimao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                mGeomorType = dimaoArrayAdapter.getItem(arg2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        if(countyType.equals("1")){
            nameslist = DBSearch.getInstance().getCountys(cityname);
            countyLl.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nameslist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_county.setAdapter(adapter);
        } else {
            countyLl.setVisibility(View.GONE);
        }
    }

    private boolean setSoil(){
        String yjz = et_yjz.getText().toString().trim();
        if(!"".equals(yjz)){
            Double double1 = Double.parseDouble(yjz);
            if (double1<0.4 || double1 >100){
                Toast.makeText(context, "有机质值不在范围内", Toast.LENGTH_LONG).show();
                return false;
            }else{
                TApplication.soilInfo.setOrganic(double1);
            }
        }else{
            Toast.makeText(context, "请正确输入有机质", Toast.LENGTH_LONG).show();
            return false;
        }

        String jjd = et_jjd.getText().toString().trim();
        if(!"".equals(jjd)){
            Double double1 = Double.parseDouble(jjd);
            if (double1<10 || double1 >240){
                Toast.makeText(context, "水解性氮值不在范围内", Toast.LENGTH_LONG).show();
                return false;
            }else{
                TApplication.soilInfo.setN(double1);
            }
        }else{
            Toast.makeText(context, "请正确输入水解性氮", Toast.LENGTH_LONG).show();
            return false;
        }

        String yxl = et_yxl.getText().toString().trim();
        if(!"".equals(yxl)){
            Double double1 = Double.parseDouble(yxl);
            if (double1<0.1 || double1 >120){
                Toast.makeText(context, "有效磷值不在范围内", Toast.LENGTH_LONG).show();
                return false;
            }else{
                TApplication.soilInfo.setP(double1);
            }
        }else{
            Toast.makeText(context, "请正确输入有效磷", Toast.LENGTH_LONG).show();
            return false;
        }

        String sxj = et_sxj.getText().toString().trim();
        if(!"".equals(sxj)){
            Double double1 = Double.parseDouble(sxj);
            if (double1<15 || double1 >1000){
                Toast.makeText(context, "速效钾值不在范围内", Toast.LENGTH_LONG).show();
                return false;
            }else{
                TApplication.soilInfo.setK(double1);
            }
        }else{
            Toast.makeText(context, "请正确输入速效钾", Toast.LENGTH_LONG).show();
            return false;
        }

        TApplication.soilInfo.setSoiltype(mSoilType);
        TApplication.soilInfo.setLandform(mGeomorType);

        return true;
    }
}
