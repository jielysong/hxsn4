package com.hxsn.ssk.biz;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;

import com.hxsn.library.utils.BitmapUtil;
import com.hxsn.library.utils.ExceptionUtil;
import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.hxsn.ssk.utils.Const;
import com.hxsn.ssk.utils.DebugUtil;


/**
 * @desc: Created by jiely on 2015/9/16.
 */
public class ImageBiz extends AsyncTask<Object, Void, Integer> {
    private Bitmap bitmap = null;
    private View imageView = null;
    private boolean isLocal;

    public ImageBiz(boolean isLocal) {
        this.isLocal = isLocal;
    }

    @Override
    protected Integer doInBackground(Object... params) {
        long imgId = (Long) params[0];
        imageView = (View) params[1];
        try {
            if (imgId == 0) {
                bitmap = null;
            } else {
                String urlString = Const.URL_PICTURE_PATH + imgId;
                DebugUtil.d("ImageBiz", "url=" + urlString);
                if (isLocal) {
                    bitmap = BitmapUtil.getLocalBitmap(urlString);
                } else {
                    bitmap = BitmapUtil.getHttpBitmap(urlString);
                }

            }
            return 200;
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
        return 500;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Integer result) {
        if (result == 200) {
            try {
                if (bitmap != null) {
                    Drawable drawable = new BitmapDrawable(TApplication.resources, bitmap);
                    imageView.setBackground(drawable);
                } else {
                    imageView.setBackgroundResource(R.mipmap.icon_logo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            imageView.setBackgroundResource(R.mipmap.icon_logo);
        }
    }
}
