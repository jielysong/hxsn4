package com.hxsn.ssklf.base;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxsn.ssklf.R;

/**
 * desc: 界面标题栏
 * Created by jiely on 2015/8/31.
 */
public class BaseTitle {
    private TextView txtMiddle;

    public BaseTitle(final Activity activity) {

        ImageView imgLeft = (ImageView) activity.findViewById(R.id.in_title)
                .findViewById(R.id.img_left);
        txtMiddle = (TextView) activity.findViewById(R.id.in_title)
                .findViewById(R.id.txt_middle);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    public BaseTitle(final Activity activity, View view) {
        ImageView imgLeft = (ImageView) view.findViewById(R.id.in_title)
                .findViewById(R.id.img_left);
        txtMiddle = (TextView) view.findViewById(R.id.in_title)
                .findViewById(R.id.txt_middle);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    public static BaseTitle getInstance(Activity activity) {
        return new BaseTitle(activity);
    }

    public static BaseTitle getInstance(Activity activity, View view) {
        return new BaseTitle(activity, view);
    }

    public void setTitle(String middle) {
        txtMiddle.setText(middle);
    }

}
