package com.hxsn.ssklf.activity;

import android.app.Activity;
import android.os.Bundle;

import com.hxsn.ssklf.R;
import com.hxsn.ssklf.base.BaseTitle;

/**
 * 帮助页面
 */
public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        BaseTitle.getInstance(this).setTitle("帮助");
    }




}
