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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.activity.CropSelectActivity;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.service.DBSearch;
import com.snsoft.ctpf.db.service.IKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class City1Fragment extends Fragment {

    private Context context;

    private ListView listView;
    private Button btn_back;

    private SoilInfo sinfo;
    private List<String> nameslist = new ArrayList<String>();
    private ArrayList<HashMap<String, String>> names = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter adapter;

    private String cityname;
    private String county;
    private String town;
    private String village;
    private String position;
    private String farmer;

    private final String HEBINGQU = "合并区";
    private final String QU_NAME = "区";
    private final String COUNTY_NAME = "";
    private final int CITYS_LIST = 0x00010;
    private final int TOWNS_LIST = 0x00011;
    private final int VILLAGES_LIST = 0x00012;
    private final int POSITION_LIST = 0x00013;
    private final int FARMER_LIST = 0x00014;
    private final int COUNTYS_LIST = 0x00015;//县
    /** list状态 */
    private int list_state = TOWNS_LIST;
    /** list点击前的状态 */
    private int previous_state = CITYS_LIST;

    private boolean isCounty = false;

    public City1Fragment(Context context) {
        this.context = context;
    }

    public City1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city1, container, false);

        previous_state = CITYS_LIST;
        initdatas();
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = setAdapeter();
        listView.setAdapter(adapter);
    }

    private void initdatas(){
        cityname = DBSearch.getInstance().getCountyName();
        Log.i("", "_cityname=" + cityname);
        String countyType = DBSearch.getInstance().getCountyType(cityname);
        if(countyType.equals("1")){
            getCountyList(cityname);
            list_state = COUNTYS_LIST;
            isCounty = true;
        } else if(HEBINGQU.equals(cityname)){
            getCountyListOfArea(cityname);
            list_state = COUNTYS_LIST;
            isCounty = true;
        } else {
            nameslist = DBSearch.getInstance().getTowns(cityname);
            list_state = TOWNS_LIST;
            isCounty = false;
        }
    }
    //根据市获得县名称列表，并排序把区放到最后
    private void getCountyList(String cityname){
        nameslist = DBSearch.getInstance().getCountys(cityname);
        for (int i = 0; i < nameslist.size(); i++) {
            String str = nameslist.get(i);
            if(str.endsWith(QU_NAME)){
                nameslist.remove(i);
                nameslist.add(str);
            }
        }
    }
    //合并区获得区列表
    private void getCountyListOfArea(String cityname){
        nameslist = DBSearch.getInstance().getCountys(COUNTY_NAME);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < nameslist.size(); i++) {
            String str = nameslist.get(i);
            if(str.endsWith(QU_NAME)){
                list.add(str);
            }
        }
        nameslist.clear();
        nameslist = list;
    }

    private void findView(View view){

        listView = (ListView) view.findViewById(R.id.list_citys);

        listView.setOnItemClickListener(listener);
        btn_back = (Button) view.findViewById(R.id.btn_back_xzqy);
        btn_back.setVisibility(View.GONE);
        btn_back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                list_state = previous_state;
                switch (previous_state){
                    case COUNTYS_LIST:
                        System.out.println("county--->county");
                        previous_state = CITYS_LIST;

                        cityname = DBSearch.getInstance().getCountyName();
                        if(HEBINGQU.equals(cityname)){
                            getCountyListOfArea(cityname);
                        } else {
                            getCountyList(cityname);
                        }
                        //nameslist = DBSearch.getInstance().getCountys(cityname);

                        list_state = COUNTYS_LIST;
                        adapter = setAdapeter();
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(listener);
                        btn_back.setVisibility(View.GONE);
                        break;
                    case TOWNS_LIST:
                        System.out.println("towns--->town");
                        if(isCounty){
                            previous_state = COUNTYS_LIST;
                        } else {
                            previous_state = CITYS_LIST;
                            btn_back.setVisibility(View.GONE);
                        }
                        nameslist = DBSearch.getInstance().getTowns(cityname);
                        list_state = TOWNS_LIST;
                        adapter = setAdapeter();
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(listener);

                        break;
                    case VILLAGES_LIST:
                        System.out.println("villages--->village");
                        previous_state = TOWNS_LIST;
                        nameslist = DBSearch.getInstance().getVillages(cityname, town);
                        list_state = VILLAGES_LIST;
                        adapter = setAdapeter();
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(listener);
                        break;
                    case POSITION_LIST:
                        System.out.println("positions--->position");
                        previous_state = VILLAGES_LIST;
                        nameslist = DBSearch.getInstance().getPositions(cityname, town, village);
                        list_state = POSITION_LIST;
                        adapter = setAdapeter();
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(listener);
                        break;
                }
            }
        });
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterview, View view, int i,
                                long l){
            switch (list_state){
                case COUNTYS_LIST://
                    cityname = nameslist.get(i);
                    System.out.println("town");
                    nameslist = DBSearch.getInstance().getTowns(cityname);
                    list_state = TOWNS_LIST;
                    adapter = setAdapeter();
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(this);
                    btn_back.setVisibility(View.VISIBLE);
                    previous_state = COUNTYS_LIST;
                    break;
                case TOWNS_LIST:
                    town = nameslist.get(i);
                    System.out.println("village");
                    nameslist = DBSearch.getInstance().getVillages(cityname, town);
                    list_state = VILLAGES_LIST;
                    adapter = setAdapeter();
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(this);
                    btn_back.setVisibility(View.VISIBLE);
                    previous_state = TOWNS_LIST;
                    break;
                case VILLAGES_LIST:
                    village = nameslist.get(i);
                    System.out.println("position");
                    nameslist = DBSearch.getInstance().getPositions(cityname, town, village);
                    list_state = POSITION_LIST;
                    adapter = setAdapeter();
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(this);
                    btn_back.setVisibility(View.VISIBLE);
                    previous_state = VILLAGES_LIST;
                    break;
                case POSITION_LIST:
                    position = nameslist.get(i);
                    System.out.println("farmer");
                    nameslist = DBSearch.getInstance().getFarmers(cityname, town, village, position);
                    list_state = FARMER_LIST;
                    adapter = setAdapeter();
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(this);
                    btn_back.setVisibility(View.VISIBLE);
                    previous_state = POSITION_LIST;
                    break;
                case FARMER_LIST:
                    farmer = nameslist.get(i);
                    TApplication.soilInfo = DBSearch.getInstance().getYuansu(cityname, town, village, position, farmer);
                    TApplication.soilInfo.setCounty(cityname);
                    TApplication.soilInfo.setFarmer(farmer);
                    TApplication.soilInfo.setPosition(position);
                    TApplication.soilInfo.setTown(town);
                    TApplication.soilInfo.setVillage(village);
                    Intent it = new Intent();
                    it.setClass(context, CropSelectActivity.class);
                    startActivityForResult(it, IKey.CITY_REQUEST_CODE);
                    break;
            }
        }
    };

    private SimpleAdapter setAdapeter(){
        names.clear();
        String name="name";
        for(int i= 0;i<nameslist.size();i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(name, nameslist.get(i));
            names.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, names, R.layout.list_item_text_only,
                new String[]{name}, new int[]{R.id.text_list_item_only});
        return adapter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(IKey.CITY_REQUEST_CODE == requestCode && IKey.CITY_REQUEST_CODE == resultCode){
            Bundle bundle = data.getExtras();
            list_state = FARMER_LIST;
            previous_state = POSITION_LIST;
            nameslist = DBSearch.getInstance().getFarmers(bundle.getString("cityname"), bundle.getString("town"),
                    bundle.getString("village"), bundle.getString("position"));
            System.out.println("back!");

        }
    }
}
