package com.hxsn.ssk.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by jiely on 2016/4/26.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected LayoutInflater inflater;
    protected ArrayList<T> myList = new ArrayList<T>();
    protected Context context;

    public MyBaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ArrayList<T> getAdapterData1() {
        return myList;
    }

    public void appendData(ArrayList<T> data, boolean isClearOld) {
        if (data == null)
            return;
        if (isClearOld)
            myList.clear();
        myList.addAll(data);
    }

    public void update() {
        this.notifyDataSetChanged();
    }

    public void clear() {
        myList.clear();
    }

    @Override
    public int getCount() {
        if (myList == null)
            return 20;
        return myList.size();
    }

    @Override
    public T getItem(int position) {
        if (myList == null)
            return null;
        if (position > myList.size() - 1)
            return null;
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getMyView(position, convertView, parent);
    }

    public abstract View getMyView(int position, View convertView, ViewGroup parent);
}
