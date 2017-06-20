package com.hxsn.ssk.fragment;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 *  Created by Administrator on 16-4-11.
 */
public class JavaScriptInterface {
    Context mContext;

    public JavaScriptInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void javaFun() {
        Toast.makeText(mContext, "hello.js", Toast.LENGTH_SHORT).show();
    }

}
