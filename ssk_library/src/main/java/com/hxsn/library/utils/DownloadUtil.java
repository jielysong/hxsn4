package com.hxsn.library.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * http://blog.csdn.net/lmj623565791/article/details/41874561
 *
 * @author zhy
 */
public class DownloadUtil {

    private static final int TIMEOUT = 10 * 1000;
    //private static final String PATH_IMAGES = Const.SDCARD_ROOT + "/ssk/nsh/";

    /**
     * desc:根据url下载图片到/guangba/shopBannerImage/shopname/下
     *
     * @param url:图片的网络访问地址
     * @param path:图片存储的本地地址 "/ssk/nsh/"
     *                       auther:jiely
     *                       create at 2015/10/10 20:36
     */
    public static void downloadImageToLocal(String url, String path) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            String fileName = DataUtil.getImageFileName(url);

            String filePath = path + fileName;
            MyFileUtil.createLocalFile(filePath);
            //1.缓存bitmap至LruCache
            //mLruCache.put(filePath, bitmap);
            fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (is != null) {
                    is.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * desc:下载文件到本地，url为网络地址 absolutePath为文件地址
     * auther:jiely
     * create at 2015/10/10 20:35
     */
    public static long downloadFileToLocal(String down_url, String absolutePath, Handler handler) throws Exception {
        int down_step = 5;// 提示step
        int totalSize;// 文件总大小
        int downloadCount = 0;// 已经下载好的大小
        int updateCount = 0;// 已经上传的文件大小
        InputStream inputStream;
        OutputStream outputStream;
        URL url = new URL(down_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
        // 获取下载文件的size
        totalSize = httpURLConnection.getContentLength();
        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("fail!");
        }
        inputStream = httpURLConnection.getInputStream();


        File file = new File(absolutePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        if (file.isDirectory()) {
            return 0;
        }

        outputStream = new FileOutputStream(absolutePath, false);// 文件存在则覆盖掉
        byte buffer[] = new byte[1024];
        int readsize = 0;
        while ((readsize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readsize);
            downloadCount += readsize;// 时时获取下载到的大小
            /**
             * 每次增张5%
             */
            if (updateCount == 0 || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                updateCount += down_step;
            }

            if (handler != null) {
                Integer progress = downloadCount * 100 / totalSize;
                //TApplication.keyValueMap.put(Const.DOWNLOAD_PROGRESS, progress);
                Message message = handler.obtainMessage();
                message.what = 11;//Const.MSG_DOWNLOAD_PROGRESS;
                handler.sendMessage(message);
            }

        }

        if (handler != null) {
            Message message = handler.obtainMessage();
            message.what = 12;//Const.MSG_DOWNLOAD_OK;
            handler.sendMessage(message);
        }


        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.close();

        return downloadCount;
    }

    /**
     * 一次从多个url中下载图片到指定目录下
     * @param urls :url地址集合  http://192.168.12.94:8980/sskcms/resource/app/nshimg/xwzx.jpg
     * @param path ：文件地址
     * @param handler: 回调
     */
    public static void downloadImagesToLocal(List<String> urls,String path,Handler handler) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        FileOutputStream fos =null;
        try{
            for(int i=0; i<urls.size();i++){
                URL u = new URL(urls.get(i));
                conn = (HttpURLConnection)u.openConnection();
                is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

                //2.缓存bitmap至/data/data/packageName/cache/文件夹中
                String fileName = getFileName(urls.get(i));

                String filePath = path+fileName+".png";
                MyFileUtil.createLocalFile(filePath);
                //1.缓存bitmap至LruCache
                //mLruCache.put(filePath, bitmap);
                fos = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }

            Message msg = handler.obtainMessage();
            msg.what = Const.MSG_SUCCESS;
            handler.sendMessage(msg);

        }catch(Exception e){
            e.printStackTrace();

        }  finally {
            try {
                if(is!=null) {
                    is.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(conn!=null) {
                conn.disconnect();
            }
            try {
                if(fos!=null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据url获取文件名
     * @param url http://192.168.12.94:8980/sskcms/resource/app/nshimg/xwzx.jpg
     * @return  xzwx
     */
    public static String getFileName(String url){
        StringBuilder sb = new StringBuilder();
        for(int i=url.length()-5; i>0; i--){
            char c = url.charAt(i);

            if(c == '/'){
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
