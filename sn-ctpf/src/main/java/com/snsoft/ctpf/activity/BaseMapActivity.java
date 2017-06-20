package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.snsoft.ctpf.R;
import com.snsoft.ctpf.TApplication;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.service.DBSearch;
import com.snsoft.ctpf.util.AndroidUtil;

public class BaseMapActivity extends Activity {

    private static final String LTAG = BaseMapActivity.class.getSimpleName();
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // 介绍如何使用个性化地图，需在MapView 构造前设置个性化地图文件路径
        // 注: 设置个性化地图，将会影响所有地图实例。
        // MapView.setCustomMapStylePath("个性化地图config绝对路径");
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra("x") && intent.hasExtra("y")) {
            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();
            LatLng latLng = new LatLng(b.getDouble("y"), b.getDouble("x"));
            mMapView = new MapView(this, new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(latLng).build()));


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

        } else {
            mMapView = new MapView(this, new BaiduMapOptions());
        }
        setContentView(mMapView);
        mBaiduMap = mMapView.getMap();

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
                    AndroidUtil.show(BaseMapActivity.this,"没有检索到结果1");//没有检索到结果
                }

                //获取反向地理编码结果
                AndroidUtil.show(BaseMapActivity.this,"获取地理编码结果1="+ result.describeContents()+""+result.getAddress());
                //获取地理编码结果
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    AndroidUtil.show(BaseMapActivity.this,"没有检索到结果2");//没有检索到结果
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
                                AndroidUtil.show(BaseMapActivity.this,"数据为空");
                            }else{
                                Log.i("Map1Fragment", "soliInfo=" + soilInfo.toString());
                                Intent it = new Intent();
                                it.setClass(BaseMapActivity.this, CropSelectActivity.class);
                                startActivity(it);
                            }
                        }
                    });

                    Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
                    p.y -= 47;
                    LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
                    mInfoWindow = new InfoWindow(location, llInfo, 0);
                    BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
                    MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
                    mBaiduMap.setMyLocationConfigeration(config);

                    // 图标
                    BitmapDescriptor mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
                    OverlayOptions overlayOptions = new MarkerOptions().position(latLng).icon(mIconMaker).zIndex(5);
                    mBaiduMap.addOverlay(overlayOptions);

                    // 显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }else {
                    AndroidUtil.showShort(BaseMapActivity.this, "该区域不能测土");
                }
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }
}
