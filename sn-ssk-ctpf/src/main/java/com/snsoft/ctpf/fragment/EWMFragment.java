package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.activity.CameraActivity;
import com.snsoft.ctpf.activity.CropSelectActivity;
import com.snsoft.ctpf.activity.CtpfHomeActivity;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.service.DBSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 二维码
 */
@SuppressLint("ValidFragment")
public class EWMFragment extends Fragment {

    private Context context;
    private EditText et_code;
    private TextView errMis;

    private List<String> codelist = new ArrayList<String>();
    private ArrayList<HashMap<String, String>> codes = new ArrayList<HashMap<String,String>>();
    private SoilInfo sinfo;

    private Button btn;
    private ListView listView;
    private SimpleAdapter adapter;
    private View view;

    public EWMFragment(Context context) {
        this.context = context;
    }

    public EWMFragment() {
    }

    /**
     * Fragment中，注册
     * 接收MainActivity的Touch回调的对象
     * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理
     */
    private CtpfHomeActivity.MyTouchListener mTouchListener = new CtpfHomeActivity.MyTouchListener() {
        @Override
        public void onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                if (isShouldHideInput(view, event)) {

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }

                // 必不可少，否则所有的组件都不会有TouchEvent了
                //if (getActivity().getWindow().superDispatchTouchEvent(event)) {
                    return ;
                //}

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ewm, container, false);

        findView(view);
        //在该Fragment的构造函数中注册mTouchListener的回调
        ((CtpfHomeActivity)this.getActivity()).registerMyTouchListener(mTouchListener);

        return view;
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }



    private void findView(View view){

        listView = (ListView) view.findViewById(R.id.list_dkbh);
        et_code = (EditText) view.findViewById(R.id.et_erweima);
        errMis =(TextView) view.findViewById(R.id.text_alert_erweima);
        view.findViewById(R.id.btn_shaomiao).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                startActivityForResult(new Intent(context,CameraActivity.class),1);
            }
        });
        btn = (Button) view.findViewById(R.id.btn_chaxun_ewm);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //点击查询时自动隐藏键盘
                InputMethodManager imm = (InputMethodManager) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                if ( imm.isActive() ) {
                    imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
                }
                String code = et_code.getText().toString().trim();
                if(code.equals("")){
                    listView.setVisibility(View.GONE);
                    errMis.setVisibility(View.VISIBLE);
                }else{
                    getDKList(code);
                    if(codelist.size()>0){
                        listView.setVisibility(View.VISIBLE);
                        errMis.setVisibility(View.GONE);
                        adapter = setAdapeter();
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(listener);
                    }else{
                        listView.setVisibility(View.GONE);
                        errMis.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    private void getDKList(String code){
        codelist = DBSearch.getInstance().getDKList(code);
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterview, View view, int i,
                                long l){
            String code = codelist.get(i);
            TApplication.soilInfo =  DBSearch.getInstance().getSoilinfoByDkbh(code);
            Intent it = new Intent();
            it.setClass(context, CropSelectActivity.class);
            startActivity(it);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == 1){
            Log.v("EWMFragment", "把扫描结果设置到查询框中");
            et_code.setText(data.getStringExtra("data"));
            String code = et_code.getText().toString().trim();
            if(code.equals("")){
                listView.setVisibility(View.GONE);
                errMis.setVisibility(View.VISIBLE);
            }else{
                getDKList(code);
                if(codelist.size()>0){
                    listView.setVisibility(View.VISIBLE);
                    errMis.setVisibility(View.GONE);
                    adapter = setAdapeter();
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(listener);
                }else{
                    listView.setVisibility(View.GONE);
                    errMis.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    private SimpleAdapter setAdapeter(){
        codes.clear();
        String name="name";
        for(int i= 0;i<codelist.size();i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(name, codelist.get(i));
            codes.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, codes, R.layout.list_item_text_only,
                new String[]{name}, new int[]{R.id.text_list_item_only});
        return adapter;
    }
}
