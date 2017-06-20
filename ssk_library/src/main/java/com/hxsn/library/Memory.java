package com.hxsn.library;

import com.android.volley.RequestQueue;

/**
 *  常驻内存，在TApplication中初始化,在library使用，因此不能放在Application中。
 */
public class Memory {
    public static RequestQueue requestQueue;
}
