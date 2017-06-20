package com.hxsn.ssk.activity;

import android.app.Activity;
import android.os.Bundle;

import com.hxsn.library.Memory;


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
