package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.db.Shared;
import com.snsoft.ctpf.db.dao.ParamService;
import com.snsoft.ctpf.util.AndroidUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SysConfigFragment extends Fragment{

    private Context context;
    private EditText edtUrl;
    private TextView txtPhone;
    private RadioGroup radiogroup1,radiogroup2;
    private Button mBtn;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private ParamService param;
    private List<String> list;
    private Spinner spinner;
    private boolean isJumpFer,isAddFer;
    private String areaStr;

    public SysConfigFragment(Context context) {
        this.context = context;
    }

    public SysConfigFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_config, container, false);
        addView(view);
        initdates();
        Listener();
        return view;
    }

    private void addView(View view) {
        param = new ParamService();
        spinner = (Spinner) view.findViewById(R.id.spinner1);
        edtUrl = (EditText) view.findViewById(R.id.et_shujuku_jjd);
        txtPhone = (TextView) view.findViewById(R.id.txt_phone);
        mBtn = (Button) view.findViewById(R.id.btn_config);
        radiogroup1=(RadioGroup) view.findViewById(R.id.radioGroup1);
        radiogroup2=(RadioGroup) view.findViewById(R.id.radioGroup2);
        radio1=(RadioButton) view.findViewById(R.id.radio1);
        radio2=(RadioButton) view.findViewById(R.id.radio2);
        radio3=(RadioButton) view.findViewById(R.id.radio3);
        radio4=(RadioButton) view.findViewById(R.id.radio4);
    }

    //用来初始化数据
    private void initdates() {
        list = param.getCountyname();//区县列表
        TApplication.sysParam = Shared.getInstance(context).getSysParam();
        String url = TApplication.sysParam.getUrl();
        if(url.length()>7){//"http://"
            edtUrl.setText(TApplication.sysParam.getUrl());
        }

        String phone = param.getParameter("SMS");//从表sn_sys_parameters中根据paramname读取paramvalue.
        TApplication.sysParam.setPhone(phone);
        Shared.getInstance(context).setSysParam(TApplication.sysParam);
        txtPhone.setText(phone);
        setRadioCheck();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(getPosition(), true);
    }

    //获取区县在list列表中的位置
    private int getPosition(){
        String city = TApplication.sysParam.getArea();
        if(city.equals("")){
            city = param.getParameter("CountyName");
            TApplication.sysParam.setArea(city);
        }
        return list.indexOf(city);
    }

    //那个radioButton设置为默认点击
    private void setRadioCheck(){
        boolean is = TApplication.sysParam.isJumpFer();

        Log.i("SysConfigFragment","isJumpFer="+is);

        if(is){
            radio1.setChecked(true);
            radio2.setChecked(false);
        }else{
            radio1.setChecked(false);
            radio2.setChecked(true);
        }
        is = TApplication.sysParam.isAddFer();
        Log.i("SysConfigFragment","isAddFer="+is);
        if(is){
            radio3.setChecked(true);
            radio4.setChecked(false);
        } else{
            radio3.setChecked(false);
            radio4.setChecked(true);
        }
    }

    //各个组件的监听事件
    private void Listener() {
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isJumpFer = TApplication.sysParam.isJumpFer();
                switch(checkedId){
                    case R.id.radio1:
                        if(!isJumpFer){
                            radio1.setChecked(true);
                            radio2.setChecked(false);
                            isJumpFer = true;

                        }
                        break;
                    case R.id.radio2:
                        if(isJumpFer){
                            radio1.setChecked(false);
                            radio2.setChecked(true);
                            isJumpFer = false;

                        }
                        break;
                    default:
                        break;
                }
            }

        });
        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isAddFer = TApplication.sysParam.isAddFer();
                switch(checkedId){
                    case R.id.radio3:
                        if(!isAddFer){
                            radio3.setChecked(true);
                            radio4.setChecked(false);
                            isAddFer = true;
                        }
                        break;
                    case R.id.radio4:
                        if(isAddFer){
                            radio3.setChecked(false);
                            radio4.setChecked(true);
                            isAddFer = false;
                        }
                        break;
                    default:
                        break;
                }
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaStr = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // 点击保存并重启
        mBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TApplication.sysParam.setJumpFer(isJumpFer);
                TApplication.sysParam.setAddFer(isAddFer);
                TApplication.sysParam.setArea(areaStr);

                String str = edtUrl.getText().toString().trim();
                if(str.length() == 0){
                    AndroidUtil.show(context,"请设置数据库地址");
                    return;
                }
                TApplication.sysParam.setUrl(str);
               // str = edtPhone.getText().toString().trim();
               // TApplication.sysParam.setPhone(str);
                Shared.getInstance(context).setSysParam(TApplication.sysParam);
                mBtn.setText("保存成功");
                //重启程序
                try {
                    Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    getActivity().finish();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
