package com.snsoft.ctpf.util;

import android.os.Environment;

import java.io.File;

/**
 *  Created by jiely on 2016/5/20.
 *  常量
 */
public class Const {
    public static final int TEST_VERTION = 1;
    public static final int TEST_VERTION1 = 4;
    public static final int DEBUG_VERTION = 2;
    public static final int RELEASE_VERTION = 3;
    public static final int SQLITE_VERTION = 2;              //数据库版本
    public static final String SQLITE_DBNAME = "ctpf";            //数据库名称

    // 得到sdcard的root
    public static final File SDCARD_ROOT = Environment.getExternalStorageDirectory();// Environment.getExternalStorageDirectory()
    public static final String PATH_APP = SDCARD_ROOT + "/ctpf/";

    public static final String DB_LOCAL_NAME = "sn_ctpf_client.db"; // 保存的数据库文件名
    public static final String ASSETS_DB_NAME = "sn_ctpf_client.sqlite3"; // 资源文件中的数据库文件名
    public static final String APK_NAME = "sn-ctpf-phone-1.0-android.apk";
    public static final String DB_HISTORY_NAME = "sn_ctpf_client_history.db";//保存历史记录
    public static final String PATH_DB_DIR = SDCARD_ROOT + "/ctpf/sqlite/"; // 在手机里存放数据库的位置
    public static final String PATH_UPDATE_DIR = SDCARD_ROOT + "/SoilTesting/";

    public static final String PATH_DBFILE = PATH_DB_DIR + DB_LOCAL_NAME;             //数据库文件存放的全路径名
    public static final String PATH_APK_FILE = PATH_APP + APK_NAME;             //数据库文件存放的全路径名
    public static final String PATH_HISTORY_DBFILE = PATH_DB_DIR + DB_HISTORY_NAME;  //历史数据库文件存放的全路径名
    public static final String PATH_HISTORY_COPY_DBFILE = PATH_DB_DIR + "SoilTesting/" + DB_HISTORY_NAME;//历史数据库文件拷贝存放的全路径名

    public static final String KEY_DB_VERSION = "dataVersion";           //数据库版本命名空间，对应于服务器上的Version.xml的dataVersion
    public static final String KEY_LOCAL_DB_VERSION = "local_dataVersion";//本地数据库版本号，自定义的，存在与Shared
    public static final String KEY_LOCAL_APK_VERSION = "local_APKVersion";//本地数据库版本号，自定义的，存在与Shared
    public static final String KEY_APK_VERSION = "version";   //apk版本命名空间，对应于服务器上的Version.xml的version


}
