package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.hxsn.ssk.R;


public class TestMapActivity extends Activity implements OnGetDistricSearchResultListener {

    private DistrictSearch mDistrictSearch;//地区搜索
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map);

        txtView = (TextView)findViewById(R.id.txt);
        Button button = (Button)findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDistrictSearch = DistrictSearch.newInstance();
                mDistrictSearch.setOnDistrictSearchListener(TestMapActivity.this);
                mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName("文安县").districtName("文安县"));
            }
        });
    }

    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {

        LatLng latLng = districtResult.getCenterPt();
        int code = districtResult.getCityCode();
        String city = districtResult.getCityName();
        StringBuilder sb = new StringBuilder();

        sb.append("下面显示："+"\n");
        sb.append("中心点坐标="+latLng.latitude+","+latLng.longitude+"\n");
        sb.append("城市编码="+code+"\n");
        sb.append("城市名称="+city+"\n");

       /* int i = 0;
        for(List<LatLng> polylines: polylinesList){
            i++;
            sb.append("polylines+" + i + "\n");
            for(LatLng polyline: polylines){
                sb.append("polylines+" + i + ":");

                double latitude = Math.ceil(polyline.latitude);
                double longitude = Math.ceil(polyline.longitude);
                sb.append("latitude="+latitude+",longitude="+longitude+".   ");
            }
        }*/
        txtView.setText(sb.toString());

        //mBaiduMap.clear();
        if (districtResult == null) {
            return;
        }
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {


        }
    }
}
