package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxsn.ssk.R;


/**
 * A simple {@link Fragment} subclass.
 * 肥料技术
 */
@SuppressLint("ValidFragment")
public class FertilizerTechnologyFragment extends Fragment{
    private Context context;

    public FertilizerTechnologyFragment() {

    }

    public FertilizerTechnologyFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fer_technology, container, false);

        WebView webView = (WebView)view.findViewById(R.id.webView);

        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://192.168.12.174:8899/wy/pg/wynews.jsp");

        return view;
    }




}
