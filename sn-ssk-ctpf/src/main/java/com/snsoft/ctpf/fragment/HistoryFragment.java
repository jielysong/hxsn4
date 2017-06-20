package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hxsn.ssk.R;
import com.snsoft.ctpf.activity.HistoryItemActivity;
import com.snsoft.ctpf.beans.HistoryNote;
import com.snsoft.ctpf.db.DbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HistoryFragment extends Fragment {

    private Context context;

    private ListView mListView;
    private TextView emptyTv;
    private Button back;
    private List<HistoryNote> mHistoryInfo;
    private SQLiteDatabase database;

    public HistoryFragment(Context context) {
        this.context = context;
    }

    public HistoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mHistoryInfo = getHistoryInformation();
        findView(view);
        return view;
    }

    private void findView(View view){
        mListView = (ListView)view.findViewById(R.id.listView_history);
        emptyTv = (TextView) view.findViewById(R.id.empty_textview);
        back = (Button) view.findViewById(R.id.back_more);

        if(mHistoryInfo.size()==0){
            emptyTv.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
        }
        mListView.setAdapter(new ListAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(context, HistoryItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("history", mHistoryInfo.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }


    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mHistoryInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return mHistoryInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView =inflater.inflate(R.layout.listview_history_item, null);
            }
            TextView mCropInfo = (TextView) convertView.findViewById(R.id.crop_title);
            mCropInfo.setText("施肥建议卡");
            TextView mYield = (TextView) convertView.findViewById(R.id.yield_info);
            mYield.setText(mHistoryInfo.get(position).getMhistory());
            TextView mCountry = (TextView) convertView.findViewById(R.id.city_info);
            mCountry.setText("[ "+mHistoryInfo.get(position).getmCountry()+" ]");
            TextView mTime = (TextView) convertView.findViewById(R.id.date_info);
            mTime.setText("保存时间："+mHistoryInfo.get(position).getmTime());
            Button delBtn = (Button) convertView.findViewById(R.id.delete_button);
            delBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    delHistoryInfo(mHistoryInfo.get(position).getId());
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

    }

    private void delHistoryInfo(int id) {
        database = DbManager.getInstance().getHistoryDatabase();
        String sql = "delete from history where id=?";
        Object[] args = { id };
        database.execSQL(sql, args);
        DbManager.getInstance().closeHistoryDatabase();
        mHistoryInfo = getHistoryInformation();
    }

    private List<HistoryNote> getHistoryInformation() {

        String sql="select * from history";
        Cursor cur=null;
        ArrayList<HistoryNote> list=new ArrayList<HistoryNote>();
        try {
            database = DbManager.getInstance().getHistoryDatabase();
            cur =  database.rawQuery(sql,null);
            while(cur.moveToNext()){
                HistoryNote mHistory=new HistoryNote();
                for (int i = 0; i <cur.getColumnCount(); i++) {
                    String name =cur.getColumnName(i).trim();
                    if (name.equalsIgnoreCase("NAME"))
                        mHistory.setmName(cur.getString(i));
                    else if (name.equalsIgnoreCase("MHISTORY"))
                        mHistory.setMhistory(cur.getString(i));
                    else if (name.equalsIgnoreCase("YIELD"))
                        mHistory.setmYield(cur.getString(i));
                    else if (name.equalsIgnoreCase("DFONE"))
                        mHistory.setmDFOne(String2Array(cur.getString(i)));
                    else if (name.equalsIgnoreCase("ZFONE"))
                        mHistory.setmZFOne(String2Array(cur.getString(i)));
                    else if (name.equalsIgnoreCase("DFTWO"))
                        mHistory.setmDFTwo(String2Array(cur.getString(i)));
                    else if (name.equalsIgnoreCase("ZFTWO"))
                        mHistory.setmZFTwo(String2Array(cur.getString(i)));
                    else if (name.equalsIgnoreCase("MODDF"))
                        mHistory.setmFertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("MODZF"))
                        mHistory.setmTopdressing(cur.getString(i));
                    else if (name.equalsIgnoreCase("MODWF"))
                        mHistory.setmMicro(cur.getString(i));
                    else if (name.equalsIgnoreCase("ID"))
                        mHistory.setId(cur.getInt(i));
                    else if (name.equalsIgnoreCase("COUNTRY"))
                        mHistory.setmCountry(cur.getString(i));
                    else if (name.equalsIgnoreCase("TIME"))
                        mHistory.setmTime(cur.getString(i));
                    else if(name.equalsIgnoreCase("o"))
                        mHistory.setO(cur.getDouble(i));
                    else if(name.equalsIgnoreCase("p"))
                        mHistory.setP(cur.getDouble(i));
                    else if(name.equalsIgnoreCase("k"))
                        mHistory.setK(cur.getDouble(i));
                    else if(name.equalsIgnoreCase("n"))
                        mHistory.setN(cur.getDouble(i));
                }
                list.add(mHistory);

            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (database != null && database.isOpen()) {
                    DbManager.getInstance().closeDatabase();
                }
                if (cur!=null){
                    cur.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    private List<String> String2Array (String str) {
        List<String> list = new ArrayList<String>();
        if (!str.equals("") && str != null) {
            str = str.substring(0, str.length()-1);
            String[] strarray=str.split(";");
            for (int i1 = 0; i1 < strarray.length; i1++){
                list.add(strarray[i1]);
            }
        }

        return list;
    }

}
