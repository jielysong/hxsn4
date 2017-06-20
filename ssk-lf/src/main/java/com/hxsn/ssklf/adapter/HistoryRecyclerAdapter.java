package com.hxsn.ssklf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxsn.ssklf.R;
import com.hxsn.ssklf.model.SiteValue;

import java.text.DecimalFormat;
import java.util.List;

/**
 *  Created by jiely on 2016/12/13.
 *  报警预警列表
 */
public class HistoryRecyclerAdapter extends RecyclerView.Adapter{
    private List<SiteValue> siteValueList;
    private DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    public HistoryRecyclerAdapter(List<SiteValue> list){
        this.siteValueList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        HistoryViewHolder viewHolder = (HistoryViewHolder) holder;

        SiteValue siteValue = siteValueList.get(i);
        viewHolder.txtTimeInfo.setText(siteValue.getTimeInfo());
        viewHolder.txtTimeInfo.setTextSize(12);
        viewHolder.txtTemperature.setText(decimalFormat.format(siteValue.getTemperature())+"℃");
        viewHolder.txtTemperature.setTextSize(12);
        viewHolder.txtHumidity.setText(decimalFormat.format(siteValue.getHumidity())+"%");
        viewHolder.txtHumidity.setTextSize(12);
        viewHolder.txtSoilTemp.setText(decimalFormat.format(siteValue.getSoilTemp())+"℃");
        viewHolder.txtSoilTemp.setTextSize(12);
        viewHolder.txtIllu.setText(decimalFormat.format(siteValue.getIllu())+"Lx");
        viewHolder.txtIllu.setTextSize(12);
    }

    @Override
    public int getItemCount() {
        return siteValueList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTimeInfo;
        public TextView txtTemperature;
        public TextView txtHumidity;
        public TextView txtSoilTemp;
        public TextView txtIllu;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            txtTimeInfo = (TextView) itemView.findViewById(R.id.txt_time);
            txtTemperature = (TextView) itemView.findViewById(R.id.txt_temp);
            txtHumidity = (TextView) itemView.findViewById(R.id.txt_humidity);
            txtSoilTemp = (TextView) itemView.findViewById(R.id.txt_soil);
            txtIllu = (TextView) itemView.findViewById(R.id.txt_illu);
        }
    }
}
