package com.hxsn.ssk.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hxsn.library.beans.Topic;
import com.hxsn.ssk.R;
import com.hxsn.ssk.base.MyBaseAdapter;


public class WenActivity extends Activity {

    private ListView listView;
    private ThisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen);

        listView = (ListView) findViewById(R.id.list);
        adapter = new ThisAdapter(this);
        listView.setAdapter(adapter);
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtMoney, txtValid, txtName;
        Button btnUse;
    }

    class ThisAdapter extends MyBaseAdapter<Topic> {
        ViewHolder holder;

        public ThisAdapter(Context context) {
            super(context);
        }

        @Override
        public View getMyView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_topic, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }
    }

}
