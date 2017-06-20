package com.snsoft.ctpf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.snsoft.ctpf.R;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        //欢迎页持续2S
        Handler handler = new Handler();
        handler.postDelayed(new Loading(), 2000);
    }


    /**
     * 2s后进入主界面
     */
    class Loading implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(getApplication(),HomeActivity.class));
            WelcomeActivity.this.finish();
        }

    }

}
