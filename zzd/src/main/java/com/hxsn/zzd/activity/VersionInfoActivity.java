package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxsn.zzd.R;
import com.hxsn.zzd.base.BaseTitle;


public class VersionInfoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_info);

        BaseTitle.getInstance(this).setTitle("版本信息");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PersonAdapter adapter = new PersonAdapter();
        recyclerView.setAdapter(adapter);

    }

    public class PersonAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_version_info, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new PersonViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            PersonViewHolder holder = (PersonViewHolder) viewHolder;
            final int NUM = 1;
            holder.position = position;
            String content="",title = "";
            Intent intent = getIntent();
            String version = intent.getStringExtra("version");
            switch (position){
                case 0:
                    title = "版本号--zzd"+version;
                    content = "解决不能进入欢迎页的问题";
                    break;
                case 1:
                    title = "版本号--zzd1.0.4";
                    content = "优化个人设置项以及子项\n" +
                            "app版本适配到最低\n" +
                            "按一次返回键提示退出，再按一次退出\n"+
                            "修复登录时的BUG";
                    break;
                case NUM+1:
                    title = "版本号--zzd1.0.3";
                    content = "增加百度推送";
                    break;
                case  NUM+2:
                    title = "版本号--zzd1.0.2";
                    content = "早知道页面，进入二级页面需显示返回按钮";
                    break;

                case  NUM+3:
                    title = "版本号--zzd1.0.1";
                    content = "去掉wifi自动打开或关闭的功能，因为外网版用wifi也能访问";
                    break;
            }
            holder.txtTitle.setText(title);
            holder.txtInfo.setText(content);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        // public View rootView;
        public TextView txtTitle;
        public TextView txtInfo;
        public int position;

        public PersonViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtInfo = (TextView) itemView.findViewById(R.id.txt_info);
            //rootView = itemView.findViewById(R.id.recycler_view_test_item_person_view);
        }

    }

}
