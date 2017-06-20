package com.snsoft.ctpf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snsoft.ctpf.R;
import com.snsoft.ctpf.TApplication;
import com.snsoft.ctpf.db.Shared;
import com.snsoft.ctpf.db.dao.ParamService;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ShortMsgFragment extends Fragment {
    private Context context;
    private ParamService param = new ParamService();


    public ShortMsgFragment(Context context) {
        this.context = context;
    }

    public ShortMsgFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short_msg, container, false);



        addView(view);

        return view;
    }

    private void addView(View view) {
        TextView txtPhone = (TextView)view.findViewById(R.id.txt_phone);
        TextView txtCx = (TextView)view.findViewById(R.id.txt_cx);
        TextView txtQx = (TextView)view.findViewById(R.id.txt_qx);
        TextView txtBzsm = (TextView)view.findViewById(R.id.txt_bzsm);
        TextView txtFormat = (TextView)view.findViewById(R.id.txt_format);
        TextView txtFormat2 = (TextView)view.findViewById(R.id.txt_format2);
        TextView txtFormat3 = (TextView)view.findViewById(R.id.txt_format3);
        TextView txtInfo = (TextView)view.findViewById(R.id.txt_info);
        TextView txtInfo2 = (TextView)view.findViewById(R.id.txt_info2);
        TextView txtInfo3 = (TextView)view.findViewById(R.id.txt_info3);

        String phone = param.getParameter("SMS");//从表sn_sys_parameters中根据paramname读取paramvalue.
        TApplication.sysParam.setPhone(phone);
        Shared.getInstance(context).setSysParam(TApplication.sysParam);
        txtPhone.setText("\t\t用户编辑指令，并发送到"+phone+"就能实现地块的施肥方案。");

        String text = txtCx.getText().toString();
        SpannableStringBuilder style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtCx.setText(style);

        text = txtQx.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtQx.setText(style);

        text = txtBzsm.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 7, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtBzsm.setText(style);

        text = txtFormat.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtFormat.setText(style);

        text = txtFormat2.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtFormat2.setText(style);

        text = txtFormat3.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtFormat3.setText(style);

        text = txtInfo.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtInfo.setText(style);

        text = txtInfo2.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtInfo2.setText(style);

        text = txtInfo3.getText().toString();
        style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_8)), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
        txtInfo3.setText(style);
    }
}
