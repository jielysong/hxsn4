package com.hxsn.ssk.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxsn.ssk.R;


public class LoadingDialog {
    private static ImageView mImage;
    private static TextView mTipTextView;
    private static String mTipString = "加载中....";
    private static Dialog myDialog = null;

    private static boolean isClose = false;

    public static void showLoading(final Context ct) {
        isClose = false;
        myDialog = new Dialog(ct, R.style.loading_dialog);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.show();
        canClose();
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        myDialog.setCanceledOnTouchOutside(false);
        Window win = myDialog.getWindow();
        win.setContentView(R.layout.dialog_load_progress);

        mImage = (ImageView) win.findViewById(R.id.progress_dialog_icon);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(ct, R.anim.loading_animation);
        mImage.startAnimation(hyperspaceJumpAnimation);
        mTipTextView = (TextView) win.findViewById(R.id.progress_dialog_msg);
        mTipTextView.setText(mTipString);

        myDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                if (!isClose) {
                    showLoading(ct);
                }
            }
        });
    }


    public static void showLoading(final Context ct, int p) {
        isClose = false;
        myDialog = new Dialog(ct, R.style.loading_dialog);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.show();
        canClose();
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        myDialog.setCanceledOnTouchOutside(false);
        Window win = myDialog.getWindow();
        win.setContentView(R.layout.dialog_load_progress);

        mImage = (ImageView) win.findViewById(R.id.progress_dialog_icon);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(ct, R.anim.loading_animation);
        mImage.startAnimation(hyperspaceJumpAnimation);
        mTipTextView = (TextView) win.findViewById(R.id.progress_dialog_msg);
        mTipTextView.setText("提交中....");

        myDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                if (!isClose) {
                    showLoading(ct);
                }
            }
        });
    }

    public static void showLoading(final Context ct, String text) {
        isClose = false;
        myDialog = new Dialog(ct, R.style.loading_dialog);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.show();
        canClose();
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        myDialog.setCanceledOnTouchOutside(false);
        Window win = myDialog.getWindow();
        win.setContentView(R.layout.dialog_load_progress);

        mImage = (ImageView) win.findViewById(R.id.progress_dialog_icon);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(ct, R.anim.loading_animation);
        mImage.startAnimation(hyperspaceJumpAnimation);
        mTipTextView = (TextView) win.findViewById(R.id.progress_dialog_msg);
        mTipTextView.setText(text);

        myDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                if (!isClose) {
                    showLoading(ct);
                }
            }
        });
    }

    private static void canClose() {
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
////					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
        isClose = true;
//			}
//		}).start();
    }

    public static void dissmissLoading() {
        if (myDialog != null) {
            isClose = true;
            myDialog.dismiss();
            myDialog = null;
        }
    }

    public void setTipString(String tipStr) {
        mTipString = tipStr;
        if (mTipTextView != null) {
            mTipTextView.setText(mTipString);
        }
    }
}
