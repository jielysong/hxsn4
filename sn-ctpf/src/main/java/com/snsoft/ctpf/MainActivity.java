package com.snsoft.ctpf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.snsoft.ctpf.activity.WelcomeActivity;
import com.snsoft.ctpf.db.DbManager;
import com.snsoft.ctpf.db.Shared;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_welcome);

        Intent intent = new Intent();


        String value  = Shared.getInstance(this).getValue("welcome");//TApplication.getValue("welcome");
        if (value.length() == 0) {//首次安装打开

            DbManager.getInstance().initdb(this); //初始化数据库

            Shared.getInstance(this).setValue("welcome", "welcome");
           /// intent.setClass(this, WelcomeActivity.class);
            //MyFileUtil.deleteAllFile(Const.PATH_DB_DIR);
        } else {
           // intent.setClass(this, HomeActivity.class);
        }
        intent.setClass(this, WelcomeActivity.class);
        startActivity(intent);
        finish();

    }

}
