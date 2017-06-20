package com.hxsn.zzd.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.GridView;

import com.hxsn.library.beans.Nongsh;
import com.hxsn.zzd.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.  随时看
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class Nshui2Fragment extends Fragment {

    private GridView gridView;
    private WebView webView;
    private Context context;
    private List<String> urls;
    private List<Nongsh> nongshList;

    public Nshui2Fragment() {
    }

    public Nshui2Fragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ValidFragment")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_njhui2, container, false);

        webView = (WebView) view.findViewById(R.id.webView);

        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(context);
        webView.addJavascriptInterface(myJavaScriptInterface, "nongqingzhan");
        String url = "file:///android_asset/news.html";
        webView.loadUrl(url);

        return view;
    }

    @Override
    public void onStop() {
        webView.clearCache(true);
        super.onStop();
    }



}
