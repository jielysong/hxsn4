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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;import com.hxsn.ssk.R;
import com.snsoft.ctpf.activity.BaseMapActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class OfflineMapFragment extends Fragment implements MKOfflineMapListener,View.OnClickListener {

    private Context context;
    private MKOfflineMap mOffline = null;
    private Button btnDown,btnCityList,btnDownManage;

    private LinearLayout layoutCity;//城市列表显示
    private LinearLayout layoutManage;//下载管理的列表显示
    private EditText edtCity;
    private int cityId=0;
    /**
     * 已下载的离线地图信息列表
     */
    private ArrayList<MKOLUpdateElement> localMapList = null;
    private LocalMapAdapter lAdapter = null;

    public OfflineMapFragment(Context context) {
        this.context = context;
    }

    public OfflineMapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        initView(view);
        addListener();
        return view;
    }

    private void addListener() {
        btnDown.setOnClickListener(this);
        btnCityList.setOnClickListener(this);
        btnDownManage.setOnClickListener(this);
    }


    private void initView(View view) {
        edtCity = (EditText) view.findViewById(R.id.edt_city);
        btnCityList = (Button) view.findViewById(R.id.btn_city_list);
        btnDownManage = (Button) view.findViewById(R.id.btn_down_manage);
        layoutCity = (LinearLayout) view.findViewById(R.id.layout_city);
        layoutManage = (LinearLayout) view.findViewById(R.id.layout_down);
        btnDown = (Button)view.findViewById(R.id.download);

        layoutManage.setVisibility(View.GONE);
        layoutCity.setVisibility(View.VISIBLE);

        // 获取所有支持离线地图的城市
        ListView allCityList = (ListView)view. findViewById(R.id.allcitylist);
        ArrayList<String> allCities = new ArrayList<>();
        final ArrayList<MKOLSearchRecord> records2 = mOffline.getOfflineCityList();
        for (MKOLSearchRecord r : records2) {
            allCities.add(r.cityName + "(" + this.formatDataSize(r.size) + ")" );
        }
        ListAdapter aAdapter = new ArrayAdapter<>(context, R.layout.list_text, allCities);
        allCityList.setAdapter(aAdapter);
        allCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtCity.setText(records2.get(position).cityName);
                cityId = records2.get(position).cityID;
            }
        });

        // 获取已下过的离线地图信息
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }

        ListView localMapListView = (ListView) view.findViewById(R.id.localmaplist);
        lAdapter = new LocalMapAdapter();
        localMapListView.setAdapter(lAdapter);
    }



    /**
     * 更新状态显示
     */
    public void updateView() {
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }
        lAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        //int cityid = Integer.parseInt(edtCity.getText().toString());
        MKOLUpdateElement temp = mOffline.getUpdateInfo(cityId);
        if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
            mOffline.pause(cityId);
        }
        super.onPause();
    }

    public String formatDataSize(int size) {
        String ret;
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }


    @Override
    public void onDestroy() {
        mOffline.destroy();
        super.onDestroy();
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    //stateView.setText(String.format("%s : %d%%", update.cityName, update.ratio));
                    updateView();
                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_down_manage://下载管理
                layoutManage.setVisibility(View.VISIBLE);
                layoutCity.setVisibility(View.GONE);

                break;
            case R.id.download://开始下载
                layoutManage.setVisibility(View.VISIBLE);
                layoutCity.setVisibility(View.GONE);
                if(cityId == 0){
                    Toast.makeText(context, "未能获取城市ID", Toast.LENGTH_SHORT).show();
                }else {
                    mOffline.start(cityId);
                    Toast.makeText(context, "开始下载离线地图", Toast.LENGTH_SHORT).show();
                    updateView();
                }

                break;
            case R.id.btn_city_list://城市列表
                layoutManage.setVisibility(View.GONE);
                layoutCity.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 离线地图管理列表适配器
     */
    public class LocalMapAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return localMapList.size();
        }

        @Override
        public Object getItem(int index) {
            return localMapList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup arg2) {
            MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
            view = View.inflate(context, R.layout.offline_localmap_list, null);
            initViewItem(view, e);
            return view;
        }

        void initViewItem(View view, final MKOLUpdateElement element) {
            Button display = (Button) view.findViewById(R.id.display);
            Button remove = (Button) view.findViewById(R.id.remove);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView update = (TextView) view.findViewById(R.id.update);
            TextView ratio = (TextView) view.findViewById(R.id.ratio);
            ratio.setText(element.ratio + "%");
            title.setText(element.cityName);
            if (element.update) {
                update.setText("可更新");
            } else {
                update.setText("最新");
            }
            if (element.ratio != 100) {
                display.setEnabled(false);
            } else {
                display.setEnabled(true);
            }
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mOffline.remove(element.cityID);
                    updateView();
                }
            });
            display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("x", element.geoPt.longitude);
                    intent.putExtra("y", element.geoPt.latitude);
                    intent.setClass(context, BaseMapActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
