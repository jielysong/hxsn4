package com.hxsn.ssk.activity;

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

import com.hxsn.ssk.R;
import com.hxsn.ssk.base.BaseTitle;

public class VersionInfoActivity extends Activity {

    private String content;
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
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            PersonViewHolder holder = (PersonViewHolder) viewHolder;
            final int NUM = 0;
            holder.position = i;
            String content="",title = "";
            Intent intent = getIntent();
            String version = intent.getStringExtra("version");
            switch (i){
                case 0:
                    title = "版本号-"+version;
                    content = "随时看在进入后台重新唤醒时容易导致程序出错关闭\n" +
                            "优化个人设置项以及子项\n" +
                            "按一次返回键提示退出，再按一次退出";
                    break;
                case NUM+1:
                    title = "版本号--ssk1.1.4";
                    content = "随时看页面，进入二级页面需显示返回按钮\n" +
                            "用RecyclerView改造版本信息页面";
                    break;
                case NUM+2:
                    title = "版本号--ssk1.1.3";
                    content = "去掉wifi自动打开或关闭的功能，因为外网版用wifi也能访问";
                    break;
                case NUM+3:
                    title = "版本号--ssk1.1.2";
                    content = "解决内网版不能升级问题\n" +
                            "APP使用自建库\n" +
                            "新闻标题栏居中\n" +
                            "解决提问题时没有添加图片显示“json格式错误”\n" +
                            "农情站消息推送标题为农情站\n" +
                            "关于我们使用web页\n"+
                            "收到推送的消息后可以自定义声音";
                    break;
                case NUM+4:
                    title = "版本号--ssk1.1.1";
                    content = "标题居中，农事汇里面栏目图标居中\n" +
                            "新增wifi根据不同版本自动打开或关闭功能\n" +
                            "解决农事汇图标不显示问题\n" +
                            "新增版本信息展示页面";
                    break;
                case NUM+5:
                    title = "版本号--ssk1.1.0";
                    content = "屏幕横屏出错问题\n" +
                            "提问题接口修改\n" +
                            "修改提问题上传图片压缩比\n" +
                            "修改农事汇图标格式为透明格式，并去掉背景图";
                    break;
                case NUM+6:
                    title = "版本号--ssk1.0.9";
                    content ="解决webView回退问题\n" +
                            "农事汇栏目显示bug\n" +
                            "新增问专家提问题功能";
                    break;
                case NUM+7:
                    title = "版本号--ssk1.0.8";
                    content =  "\"农家汇\"改为\"农事汇\"\n" +
                            "调整底部图标为透明色，并调整高度\n" +
                            "修改密码标题背景修改\n" +
                            "农事汇接口对接\n" +
                            "添加农情站页面\n" +
                            "问专家接口对接\n" +
                            "利用百度推送增加农情站和报警的推送功能\n" +
                            "解决个人设置修改乱码问题";
                    break;
                case NUM+8:
                    title = "版本号--ssk1.0.7";
                    content = "解决个人设置页面跳转到上个页面后之后再返回,页面没变的问题\n" +
                            "新增登录用户输入框保留上个登录用户显示功能\n" +
                            "关于我们";
                    break;
            }
            holder.txtTitle.setText(title);
            holder.txtInfo.setText(content);
        }

        @Override
        public int getItemCount() {
            return 8;
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
