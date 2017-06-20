package com.hxsn.ssk.activity;

import android.app.Activity;
import android.os.Bundle;


/**
 * @description
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        //Memory.requestQueue.cancelAll(this);
        super.onStop();
    }
}
