package com.hxsn.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtil {


    /**
     * author：zhoujy
     * Date：2015-2-10
     * Title: getLoacalBitmap
     * Description: 加载本地图片
     * param @param path
     */
    public static Bitmap getLocalBitmap(String path) {
        FileInputStream fis = null;
        try {
            if (MyFileUtil.isFileExists(path)) {
                fis = new FileInputStream(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inPurgeable = true;
                options.inInputShareable = true;
                return BitmapFactory.decodeStream(fis, null, options);
            } else {
                return null;
            }

        } catch (Exception e) {
            ExceptionUtil.handle(e);
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    ExceptionUtil.handle(e);
                }
            }
        }
    }

    /**
     * author：jiely
     * Date：2015年4月18日
     * Title: getBitmapFromUrl
     * Description: 从网络访问图片，返回bitmap对象
     * param @param urlPath
     */
    public static Bitmap getHttpBitmap(String urlPath) {
        Bitmap bitmap;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            if (urlPath == null || urlPath.length() == 0) {
                return null;
            } else {
                Log.i("bitmapUtil", "urlPath=" + urlPath);
            }
            URL u = new URL(urlPath);
            conn = (HttpURLConnection) u.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * desc: 把bitmap存入文件中，文件夹为path，文件名为filename
     * auther:jiely
     * create at 2015/12/23 16:50
     */
    public static void saveBitmapToFile(Bitmap bm, String fileName, String path) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File myCaptureFile = new File(path, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
