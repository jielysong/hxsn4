package com.hxsn.library.utils;

import android.content.Context;

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
     * 在应用中创建文件夹mkFile
     * param mkFile
     * return2015-1-15
     */
    public static File getFile(Context context, String mkFile) {
        File cacheDir = null;
        File saveFile;
        // 判断外存SD卡挂载状态，如果挂载正常，创建SD卡缓存文件夹

        saveFile = new File(cacheDir, mkFile);
//		LogUtil.i("saveFilepath", saveFile.getAbsolutePath());

        if (!saveFile.exists()) {
            cacheDir.mkdirs();
        }

        return saveFile;
    }


    /**
     * author：zhoujy
     * Date：2015-2-9
     * Title: createLocalPic
     * Description: 创建本地文件夹
     * param     文件夹路径
     */
    public static boolean createLocalFileDirectory(String path) {
        //创建图片本地文件夹
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

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
        //创建图片本地文件夹
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
     *
     * @param  httpUrl 图片的后台访问地址
     * @param  path     本地存放位置
     * @param  filename 文件名
     * @param  suffix   后缀名  .jpg  or .png
     * Description: 下载图片到本地
     */
    public static void saveLocalPicture(String httpUrl, String path, String filename,String suffix) {
        URL url;
        HttpURLConnection conn = null;
        InputStream inSream = null;
        FileOutputStream outStream = null;
        try {
            url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6 * 1000);  // 注意要设置超时，设置时间不要超过6秒，避免被android系统回收
            if (conn.getResponseCode() != 200)
                throw new RuntimeException("请求url失败");

            //把图片保存到项目的根目录
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(path);//Environment.getExternalStorageDirectory());
            //stringBuilder.append(Const.NAME_FILE_ADPIC);//"/downpicture/"
            stringBuilder.append(filename);
            stringBuilder.append(suffix);//".jpg"

            String localPath = stringBuilder.toString();
            File file = new File(localPath);

            inSream = conn.getInputStream();
            outStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inSream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }

            //readAsFile(inSream, new File(stringBuilder.toString()));
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * author：jiely
     * Date：2015年3月11日
     * Title: fileIsExists
     * Description: 判断文件是否存在
     */
    public static boolean isFileExists(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {

            return false;
        }
        return true;
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

    public static void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
