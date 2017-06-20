package com.hxsn.ssk.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxsn.ssk.R;

import java.io.File;

/**
 * @desc: Created by jiely on 2015/12/29.
 */
public class BasePicAdd {
    private TextView txtPhotograph, txtPicture, txtCancel;
    private LinearLayout layout;
    private Activity activity;
    private Context context;

    public BasePicAdd(Activity activity){
        this.activity = activity;
        layout = (LinearLayout) activity.findViewById(R.id.line_dialog);
        txtPicture = (TextView) activity.findViewById(R.id.txt_picture);
        txtPhotograph = (TextView) activity.findViewById(R.id.txt_photograph);
        txtCancel = (TextView) activity.findViewById(R.id.txt_cancel);
    }

    public void setVisible(){
        layout.setVisibility(View.VISIBLE);
    }

    public boolean onClick(View v){
        Intent intent;
        try {
            switch (v.getId()) {
                case R.id.txt_picture://上传照片
                    layout.setVisibility(View.INVISIBLE);
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(intent, 1);
                    layout.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.txt_photograph://拍照上传
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "jolin.jpg")));
                    activity.startActivityForResult(intent, 2);
                    layout.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.txt_cancel:
                    layout.setVisibility(View.INVISIBLE);
                    return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public void onActivityResult(int requestCode, Intent data) {

        switch (requestCode) {
            case 1:// 如果是直接从相册获取
                startPhotoZoom(data.getData());
                break;
            case 2:// 如果是调用相机拍照时
                File temp = new File(Environment.getExternalStorageDirectory() + "/jolin.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
        }

    }

    /**
     * desc:
     * auther:jiely
     * create at 2015/12/23 16:35
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, 3);
    }
}
