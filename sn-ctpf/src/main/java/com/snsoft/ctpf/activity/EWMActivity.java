package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.snsoft.ctpf.R;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.service.DBSearch;
import com.snsoft.ctpf.db.service.IKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EWMActivity extends Activity {
	private EditText et_code;
	private TextView errMis;
	
	private List<String> codelist = new ArrayList<String>();
	private ArrayList<HashMap<String, String>> codes = new ArrayList<HashMap<String,String>>();
	private SoilInfo sinfo;
	
	private Button btn;
	private ListView listView;
	private SimpleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_erweima);
		
		findView();
	}
	
	@Override  
	public boolean dispatchTouchEvent(MotionEvent ev) {  
	    if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
	        View v = getCurrentFocus();  
	        if (isShouldHideInput(v, ev)) {  
	  
	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	            if (imm != null) {  
	                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
	            }  
	        }  
	        return super.dispatchTouchEvent(ev);  
	    }  
	    // 必不可少，否则所有的组件都不会有TouchEvent了  
	    if (getWindow().superDispatchTouchEvent(ev)) {  
	        return true;  
	    }  
	    return onTouchEvent(ev);  
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
	
	
	@Override
	protected void onResume() {
		super.onResume();
		//errMis.setVisibility(View.GONE);
		//et_code.setText("");
	}
	
	private void findView(){
		
		listView = (ListView) findViewById(R.id.list_dkbh);
		
		et_code = (EditText) findViewById(R.id.et_erweima);
		errMis =(TextView) findViewById(R.id.text_alert_erweima);
		findViewById(R.id.btn_shaomiao).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				startActivityForResult(new Intent(EWMActivity.this,CameraActivity.class),1);
			}
		});
		btn = (Button) findViewById(R.id.btn_chaxun_ewm);
		
		btn.setOnClickListener(new OnClickListener(){
			
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
	
	private OnItemClickListener listener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapterview, View view, int i,
				long l){
			String code = codelist.get(i);
			sinfo = DBSearch.getInstance().getSoilinfoByDkbh(code);
			Intent it = new Intent();
			Bundle b = new Bundle();
			b.putInt(IKey.CHAXUN_BUNDLE_KEY, IKey.ERWEIMA_DATA);
			b.putParcelable(IKey.SINFO_GET, sinfo);
			it.putExtras(b);
			
			it.setClass(EWMActivity.this, CropSelectActivity.class);
			startActivity(it);	
		}
	};

	private void getSoilInfo(){
		String code = et_code.getText().toString().trim();
		sinfo = DBSearch.getInstance().getSoilinfoByDkbh(code);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1 && resultCode == 1){
			Log.i("EWMActivity", "把扫描结果设置到查询框中");
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
		SimpleAdapter adapter = new SimpleAdapter(this, codes, R.layout.list_item_text_only,
				new String[]{name}, new int[]{R.id.text_list_item_only});
		return adapter;
	}
}
