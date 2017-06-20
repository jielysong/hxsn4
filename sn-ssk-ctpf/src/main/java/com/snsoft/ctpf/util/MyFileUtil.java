package com.snsoft.ctpf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * ************************************************************************************************************
 * author：jiely
 * Date：2015-2-4
 * Description:
 * Company：中交讯通
 *
 * @version 0.1
 *          *************************************************************************************************************
 */
public class MyFileUtil {

    /**
     * author：zhoujy
     * Date：2015-2-9
     * Title: createLocalPic
     * Description: 创建本地文件夹
     * param     文件夹路
     */
    public static boolean deleteFile(File file) {

        boolean isDelete = true;

        if (file.exists()) {                    //判断文件是否存在
            if (file.isFile()) {                    //判断是否是文件
                isDelete = file.delete();                       //delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) {              //否则如果它是一个目录
                File files[] = file.listFiles();               //声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {            //遍历目录下所有的文件
                    deleteFile(files[i]);             //把每个文件 用这个方法进行迭代
                }
            }
            isDelete = file.delete();
        } else {
            System.out.println("所删除的文件不存在！" + '\n');
        }

        return isDelete;
    }

    /**
     * author：jiely
     * Date：2015年4月24日
     * Title: deleteFile
     * Description: 删除指定文件夹下所有文件,不删除文件夹，如果文件夹不存在则创建文件夹
     */
    public static boolean deleteAllFile(String path) {
        boolean isOk = true;
        try {
            File file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }
            if (!file.isDirectory()) {
                return false;
            }

            String[] tempList = file.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++) {
                temp = new File(path + tempList[i]);
                if (temp.isFile()) {
                    isOk = temp.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return isOk;
    }

    /**
     * author：jiely
     * Date：2015年3月30日
     * Title: createLocalFile
     * Description: 创建本地文件
     * return:
     */
    public static boolean createLocalFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                String parent = file.getParent();
                File dir = new File(parent);
                if (!dir.exists()) {
                    return dir.mkdirs();
                }
                System.out.println(parent);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();//递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * author：jiely
     * Date：2015-3-3
     * Title: saveLocalPicture
     * Description: 下载图片到本地
     * param @param urlpath
     */
    public static void saveLocalPicture(String urlpath, String localPath) {
        URL url;
        HttpURLConnection conn = null;
        InputStream inSream = null;
        FileOutputStream outStream = null;
        try {
            url = new URL(urlpath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6 * 1000);  // 注意要设置超时，设置时间不要超过6秒，避免被android系统回收
            if (conn.getResponseCode() != 200)
                throw new RuntimeException("请求url失败");

            //把图片保存到项目的根目录
            File file = new File(localPath);

            inSream = conn.getInputStream();
            outStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inSream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inSream != null) {
                try {
                    inSream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * author：jiely
     * Date：2015年3月11日
     * Title: fileIsExists
     * Description: 判断文件夹是否为空
     * param @param dirPath  文件夹路径
     * return boolean  如果文件夹不存在则为空，返回true， 如果文件夹存在且为空也返回true，文件夹存在且不为空则返回false
     */
    public static boolean isFileDirNull(File file) {
        try {
            //File file = new File(dirPath);
            if (!file.exists()) {
                return true;
            } else {
                String[] files = file.list();
                return files.length == 0;
            }
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * author：jiely
     * Date：2015年4月23日
     * Title: getFileName
     * Description: 通过分割线"/"获取文件名
     * param @param url
     */
    public static String getFileName(String url) {

        String[] split = url.split("/");
        return split[split.length - 1];
    }

    public static void copyFile(String sourFile, String desFile) {
        FileOutputStream fos = null;
        FileInputStream fis;
        try {
            fos = new FileOutputStream(desFile);
            fis = new FileInputStream(sourFile);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * desc:递归获取目录文件个数
     * auther:jiely
     * create at 2015/10/10 21:29
     */
    public long getFileNum(File f) {//
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileNum(flist[i]);
                size--;
            }
        }
        return size;
    }


}
