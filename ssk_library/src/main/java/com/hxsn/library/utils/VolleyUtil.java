package com.hxsn.library.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jiely on 2016/4/18.
 */
public class VolleyUtil {
    private static RequestQueue requestQueue;

    public static void initialize(Context context) {
        if (requestQueue == null) {
            synchronized (VolleyUtil.class) {
                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        requestQueue.start();
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue");
        return requestQueue;
    }
}
