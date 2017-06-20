package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.db.Shared;
import com.snsoft.ctpf.util.Config;
import com.snsoft.ctpf.util.Const;
import com.snsoft.ctpf.util.VersionUtil;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MoreFragment extends Fragment {

    private Context context;
    public MoreFragment() {

    }

    public MoreFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more, container, false);

        MyAdapter adapter = new MyAdapter(context);
        ListView listView = (ListView) view.findViewById(R.id.list);
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                transaction.replace(R.id.framelayout, TApplication.fragmentList.get(Config.moreList.get(position)));
                transaction.commit();
            }
        });
        Log.i("MoreFragment","Config.moreList.size()="+Config.moreList.size());
        listView.setAdapter(adapter);

        return view;
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            super();
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return Config.moreList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_more, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
                viewHolder.txtNew = (TextView) convertView.findViewById(R.id.txt_new);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.txtName.setText(Config.moreNames[position]);

            viewHolder.txtNew.setVisibility(View.INVISIBLE);
            Fragment fragment =  TApplication.fragmentList.get(Config.moreList.get(position));
            String fragmentName = fragment.getClass().getSimpleName();
            String url = TApplication.sysParam.getUrl();

            //判断是否有数据更新
            if(fragmentName.equals("UpdateDataFragment")){
                if(url.length() == 0){
                    viewHolder.txtNew.setVisibility(View.INVISIBLE);
                    return convertView;
                }
                Integer newVersion = VersionUtil.getVersionForUrl(url, Const.KEY_DB_VERSION);
                int oldVersion = Shared.getInstance(context).getInt(Const.KEY_LOCAL_DB_VERSION);
                if(newVersion != null){
                    Log.i("MoreFragment","fragmentName = "+fragmentName);

                    Log.i("MoreFragment","newDbVersion="+newVersion+",oldDbVersion="+oldVersion);
                    if(newVersion > oldVersion ){
                        viewHolder.txtNew.setVisibility(View.VISIBLE);
                    }else {
                        viewHolder.txtNew.setVisibility(View.INVISIBLE);
                    }
                }
            }

            //判断是否有系统更新
            if(fragmentName.equals("UpdateSysFragment")){
                Integer newVersion = VersionUtil.getVersionForUrl(url, Const.KEY_APK_VERSION);
                int oldVersion = Shared.getInstance(context).getInt(Const.KEY_LOCAL_APK_VERSION);
                if(newVersion != null){
                    Log.i("MoreFragment","newAPKVersion="+newVersion+",oldAPKVersion="+oldVersion);
                    if(newVersion > oldVersion ){
                        viewHolder.txtNew.setVisibility(View.VISIBLE);
                    }else {
                        viewHolder.txtNew.setVisibility(View.INVISIBLE);
                    }
                }
            }

            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtName,txtNew;
    }

}
