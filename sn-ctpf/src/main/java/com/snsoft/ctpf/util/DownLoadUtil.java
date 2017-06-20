package com.snsoft.ctpf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class DownLoadUtil {

    private static final int TIMEOUT = 10 * 1000;//超时时长

    /**
     * 从服务器下载文件
     * @param srcUrl 服务器地址
     * @param destPath     下载目的地
     * @return 数据长度
     * @throws Exception
     */
    public static long downloadUpdateFile(String srcUrl, String destPath)
            throws Exception {
        int downloadCount = 0;
        InputStream inputStream;
        OutputStream outputStream;

        URL url = new URL(srcUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIMEOUT);
        //httpURLConnection.setReadTimeout(TIMEOUT);

        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("fail!");
        }
        inputStream = httpURLConnection.getInputStream();
        File file = new File(destPath);
        if(!file.exists()){
            file.createNewFile();
        }
        outputStream = new FileOutputStream(destPath, false);
        byte buffer[] = new byte[1024];
        int readsize = 0;
        while ((readsize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readsize);
            downloadCount += readsize;
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.close();

        return downloadCount;
    }


    /**
     * 文件copy
     * @param sourFile 源文件地址
     * @param desFile 目的文件地址
     * @return
     */
    public static int copyDatabaseFile(String sourFile, String desFile) {
        int num = 0;
        FileOutputStream fos;
        FileInputStream fis;
        try {
            fos = new FileOutputStream(desFile);
            fis = new FileInputStream(sourFile);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
                num += count;
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return num;
    }
}
