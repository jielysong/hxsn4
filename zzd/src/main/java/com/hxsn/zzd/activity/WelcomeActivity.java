package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hxsn.zzd.R;
import com.hxsn.zzd.base.BaseBanner;


public class WelcomeActivity extends Activity {

    // @InjectView(R.id.viewPager) ViewPager viewPager;
    // @InjectView(R.id.round_group) ViewGroup roundGroup;
    private BaseBanner banner;

    private int[] ids = {R.drawable.index01, R.drawable.index02, R.drawable.index03,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome1);
        final Intent intent = new Intent();
        TextView txtIn = (TextView) findViewById(R.id.txt_in);
        txtIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView txtIn1 = (TextView) findViewById(R.id.txt_text1);
        txtIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.setClass(WelcomeActivity.this, TestActivity.class);
                intent.setClass(WelcomeActivity.this, LoginTestActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//"com.videogo.EXTRA_WEBVIEW_ACTION"
                //intent.putExtra(IntentConsts.EXTRA_WEBVIEW_ACTION, EzvizWebViewActivity.WEBVIEW_ACTION_CLOUDPAGE);
                intent.setFlags(268435456);
                intent.putExtra("Param", -1);
                intent.putExtra("com.videogo.EXTRA_WEBVIEW_ACTION", 0);
                startActivity(intent);

                startActivity(intent);
            }
        });

        TextView txtIn2 = (TextView) findViewById(R.id.txt_text2);
        txtIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(WelcomeActivity.this, LoginTestActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//"com.videogo.EXTRA_WEBVIEW_ACTION"
                //intent.putExtra(IntentConsts.EXTRA_WEBVIEW_ACTION, EzvizWebViewActivity.WEBVIEW_ACTION_CLOUDPAGE);
                intent.setFlags(268435456);
                intent.putExtra("Param", -1);
                intent.putExtra("com.videogo.EXTRA_WEBVIEW_ACTION", 0);
                startActivity(intent);
            }
        });
    }

   /* private void addBanner() {
        banner = new BaseBanner(this,this,viewPager,roundGroup);
        banner.init();
        banner.start();
    }*/

    @Override
    protected void onStop() {
        // banner.stop();
        super.onStop();
    }
}
