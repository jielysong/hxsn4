package com.hxsn.ssklf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxsn.ssklf.R;
import com.hxsn.ssklf.model.SiteInfo;

import java.util.List;

/**
 *  Created by jiely on 2016/12/13.
 *  报警预警列表
 */
public class SiteListAdapter extends BaseAdapter {

    private List<SiteInfo> siteList;
    private LayoutInflater inflater;

    public SiteListAdapter(Context context, List<SiteInfo> list){
        this.siteList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return siteList.size();
    }

    @Override
    public Object getItem(int position) {
        return siteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SiteInfo siteInfo = (SiteInfo) this.getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.item_site, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_site_name);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(siteInfo.getName());

        return convertView;
    }

    public  class ViewHolder{
        public TextView txtName;
    }
}
