package com.snsoft.ctpf.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.activity.CropSelectActivity;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.service.DBSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UserFragment extends Fragment {
    private Context context;
    private EditText mFarmerEt;
    private Button mFarmerSearchBtn;
    private ListView mFarmerList;
    private ArrayList<HashMap<String, String>> mFarmerName;
    private List<SoilInfo> mSoilInfoList;

    public UserFragment(Context context) {
        this.context = context;
    }

    public UserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initData();
        initView(view);
        return view;
    }


    private void initData(){
        mFarmerName = new ArrayList<HashMap<String,String>>();
        //cityName = DataUtil.getInstance().getCityName();
    }

    private void initView(View view){
        mFarmerEt = (EditText)view.findViewById(R.id.et_farmer_name);
        mFarmerSearchBtn = (Button) view.findViewById(R.id.btn_farmer_search);
        mFarmerList = (ListView) view.findViewById(R.id.listview_farmer_search_result);
        mFarmerList.setOnItemClickListener(listener);
        mFarmerSearchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击查询时自动隐藏键盘
                InputMethodManager imm = (InputMethodManager) v.getContext( ).getSystemService( Context. INPUT_METHOD_SERVICE );
                if ( imm.isActive() ) {
                    imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
                }

                searchFarmers();
            }
        });
    }

    private void searchFarmers(){
        String mStr = mFarmerEt.getText().toString().trim();
        if(mStr == null || "".equals(mStr)){
            Toast.makeText(context, "请输入用户名称", Toast.LENGTH_SHORT).show();
            return;
        }
        String name="name";
        mFarmerName.clear();
        mSoilInfoList = DBSearch.getInstance().getSoilInfoListByFarmer(mStr, 50, null);
        for (int i = 0; i < mSoilInfoList.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            String value = mSoilInfoList.get(i).getFarmer()+" ("+ mSoilInfoList.get(i).getCounty() + "--" +
                    mSoilInfoList.get(i).getTown()+"--"+mSoilInfoList.get(i).getVillage()+") ";
            map.put(name, value);
            mFarmerName.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, mFarmerName, R.layout.list_item_text_only,
                new String[]{name}, new int[]{R.id.text_list_item_only});
        mFarmerList.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Intent it = new Intent();
            TApplication.soilInfo = mSoilInfoList.get(arg2);
            it.setClass(context, CropSelectActivity.class);
            startActivity(it);
        }
    };

}
