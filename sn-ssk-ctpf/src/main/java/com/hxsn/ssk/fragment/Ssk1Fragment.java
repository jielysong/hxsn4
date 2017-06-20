package com.hxsn.ssk.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.ssk.R;


/**
 * A simple {@link Fragment} subclass.  随时看
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class Ssk1Fragment extends Fragment implements View.OnClickListener {
    private Context context;
    private RelativeLayout layout1, layout2, layout3, layout4, layout5;
    private ImageView img1, img2, img3, img4, img5;
    private TextView txt1, txt2, txt3, txt4, txt5;

    public Ssk1Fragment() {
    }

    public Ssk1Fragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ssk1, container, false);
        addView(view);
        addListener();
        return view;
    }

    private void addListener() {
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        layout5.setOnClickListener(this);
    }

    private void addView(View view) {
        layout1 = (RelativeLayout) view.findViewById(R.id.layout1);
        img1 = (ImageView) view.findViewById(R.id.img1);
        txt1 = (TextView) view.findViewById(R.id.txt1);
        layout2 = (RelativeLayout) view.findViewById(R.id.layout2);
        img2 = (ImageView) view.findViewById(R.id.img2);
        txt2 = (TextView) view.findViewById(R.id.txt2);
        layout3 = (RelativeLayout) view.findViewById(R.id.layout3);
        img3 = (ImageView) view.findViewById(R.id.img3);
        txt3 = (TextView) view.findViewById(R.id.txt3);
        layout4 = (RelativeLayout) view.findViewById(R.id.layout4);
        img4 = (ImageView) view.findViewById(R.id.img4);
        txt4 = (TextView) view.findViewById(R.id.txt4);
        layout5 = (RelativeLayout) view.findViewById(R.id.layout5);
        img5 = (ImageView) view.findViewById(R.id.img5);
        txt5 = (TextView) view.findViewById(R.id.txt5);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        clearClickView();
        switch (v.getId()) {
            case R.id.layout1:
                setClickView(1);
                break;
            case R.id.layout2:
                setClickView(2);
                break;
            case R.id.layout3:
                setClickView(3);
                break;
            case R.id.layout4:
                setClickView(4);
                break;
            case R.id.layout5:
                setClickView(5);
                break;
        }
    }

    private void setClickView(int mode) {
        switch (mode) {
            case 1:
                img1.setBackgroundResource(R.drawable.detect_s);
                txt1.setTextColor(getResources().getColor(R.color.green));
                break;
            case 2:
                img1.setBackgroundResource(R.drawable.curve_s);
                txt1.setTextColor(getResources().getColor(R.color.green));
                break;
            case 3:
                img1.setBackgroundResource(R.drawable.worn_s);
                txt1.setTextColor(getResources().getColor(R.color.green));
                break;
            case 4:
                img1.setBackgroundResource(R.drawable.history_s);
                txt1.setTextColor(getResources().getColor(R.color.green));
                break;
            case 5:
                img1.setBackgroundResource(R.drawable.more_s);
                txt1.setTextColor(getResources().getColor(R.color.green));
                break;
        }
    }

    private void clearClickView() {
        img1.setBackgroundResource(R.drawable.detect_n);
        txt1.setTextColor(getResources().getColor(R.color.gray));
        img2.setBackgroundResource(R.drawable.curve_n);
        txt2.setTextColor(getResources().getColor(R.color.gray));
        img3.setBackgroundResource(R.drawable.worn_n);
        txt3.setTextColor(getResources().getColor(R.color.gray));
        img4.setBackgroundResource(R.drawable.history_n);
        txt4.setTextColor(getResources().getColor(R.color.gray));
        img5.setBackgroundResource(R.drawable.more_n);
        txt5.setTextColor(getResources().getColor(R.color.gray));
    }
}
