package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.activity.CropSelectActivity;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.service.DBSearch;
import com.snsoft.ctpf.util.AndroidUtil;

/**
 *
 */
@SuppressLint("ValidFragment")
public class Map1Fragment extends Fragment implements View.OnClickListener {

    private Context context;
    private LocationClient mLocClient;// 定位相关
    //private MyLocationListenner myListener = new MyLocationListenner();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private DistrictSearch mDistrictSearch;//地区搜索
    //private RelativeLayout rl1,rl2;
    private ImageView imgLocal,imgBack;
    private View view;


    public Map1Fragment(Context context) {
        this.context = context;
    }

    public Map1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(TApplication.context);
        view = inflater.inflate(R.layout.fragment_map1, container, false);

        addView(view);
        addListener();
        initCenterMap();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void addListener() {
        imgBack.setOnClickListener(this);
        imgLocal.setOnClickListener(this);
    }

    private void addView(final View view){

        imgLocal = (ImageView)view.findViewById(R.id.img_locate);
        imgBack = (ImageView)view.findViewById(R.id.img_back);
        mMapView = (MapView)view.findViewById(R.id.bd_mapview);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                search(latLng);
                mBaiduMap.clear();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                //Toast.makeText(context, "name=" + mapPoi.getName() + ",坐标" + mapPoi.getPosition().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * 根据坐标查询地址  不在区域内则显示不测土
     * @param  latLng :坐标
     */
    private void search(final LatLng latLng){
        GeoCoder mSearch = GeoCoder.newInstance();

        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    AndroidUtil.show(context,"没有检索到结果1");//没有检索到结果
                }

                //获取反向地理编码结果
                AndroidUtil.show(context,"获取地理编码结果1="+ result.describeContents()+""+result.getAddress());
                //获取地理编码结果
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    AndroidUtil.show(context,"没有检索到结果2");//没有检索到结果
                }
                //获取反向地理编码结果
                //AndroidUtil.show(context,"获取地理编码结果2="+ result.describeContents()+""+result.getAddress());
                String address = result.getAddress();
                if(address.contains(TApplication.city)){
                    InfoWindow mInfoWindow;
                    // 生成一个TextView用户在地图中显示InfoWindow
                    TextView location = new TextView(TApplication.context);
                    location.setBackgroundResource(R.drawable.popup);
                    location.setPadding(30, 20, 30, 50);
                    location.setTextColor(getResources().getColor(R.color.black_text_n));
                    location.setText("测土配方");
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SoilInfo soilInfo = DBSearch.getInstance().getSoilinfoByPosition(latLng.longitude, latLng.latitude);
                            TApplication.soilInfo = soilInfo;
                            if(soilInfo == null){
                                AndroidUtil.show(context,"数据为空");
                            }else{
                                Log.i("Map1Fragment", "soliInfo=" + soilInfo.toString());
                                Intent it = new Intent();
                                it.setClass(context, CropSelectActivity.class);
                                startActivity(it);
                            }
                        }
                    });

                    Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
                    p.y -= 47;
                    LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
                    mInfoWindow = new InfoWindow(location, llInfo, 0);
                    BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
                    MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
                    mBaiduMap.setMyLocationConfigeration(config);

                    // 图标
                    BitmapDescriptor mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
                    OverlayOptions overlayOptions = new MarkerOptions().position(latLng).icon(mIconMaker).zIndex(5);
                    mBaiduMap.addOverlay(overlayOptions);

                    // 显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }else {
                    AndroidUtil.showShort(context, "该区域不能测土");
                }
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
    }

    //根据城市名获取中心点初始化
    private void initCenterMap(){

        // 地图初始化
        mMapView = (MapView)view.findViewById(R.id.bd_mapview);
        mBaiduMap.setMyLocationEnabled(false);

        if(mLocClient != null){
            mLocClient.stop();
        }
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(new OnGetDistricSearchResultListener() {
            @Override
            public void onGetDistrictResult(DistrictResult districtResult) {//行政区域检索找到中心点
                mBaiduMap.clear();
                if (districtResult == null) {
                    return;
                }

                districtResult.getCityCode();
                LatLng latLng = districtResult.getCenterPt();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng);
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(latLng)
                        .zoom(12)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                if( mBaiduMap != null){
                    mBaiduMap.setMapStatus(mMapStatusUpdate);
                }
            }
        });

        if(TApplication.city == null || TApplication.city.length()==0){
            TApplication.city = "霸州市";
        }
        DistrictSearchOption option = new DistrictSearchOption();
        mDistrictSearch.searchDistrict(option.cityName(TApplication.city).districtName(TApplication.city));

    }

    //定位当前地址初始化
    private void initLocate() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 定位初始化
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(new BDLocationListener() {//定位SDK监听函数
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null || mMapView == null) {
                    return;
                }

                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())// 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
            }

        });
        LocationClientOption option = new LocationClientOption();

        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
    }

    @Override
    public void onClick(View v) {
        //Intent intent = new Intent();
        switch (v.getId()){
            case R.id.img_locate://定位
                isFirstLoc = true;
                mDistrictSearch.destroy();
                initLocate();
               // Intent it = new Intent();
               // it.setClass(context, CropSelectActivity.class);
               // startActivity(it);
                break;
            case R.id.img_back://归位
                isFirstLoc = false;
                initCenterMap();
                break;
        }
    }

    @Override
    public void onStop() {// 退出时销毁定位
        if(mLocClient != null){
            mLocClient.stop();
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        mMapView.onResume();
        super.onStart();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {// 关闭定位图层
        if(mLocClient != null){
            mLocClient.stop();
        }
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {

        @Override
        public void onGetPoiResult(PoiResult poiResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            poiDetailResult.getName();
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
}
