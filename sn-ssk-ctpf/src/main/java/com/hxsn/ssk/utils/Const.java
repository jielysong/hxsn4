package com.hxsn.ssk.utils;

import android.os.Environment;

import com.hxsn.ssk.TApplication;

import java.io.File;

/**
 * Created by jiely on 2016/6/28.
 */
public class Const {
    //version
    public static final int TEST_VERTION = 1;
    public static final int TEST_VERTION1 = 4;
    public static final int DEBUG_VERTION = 2;
    public static final int RELEASE_VERTION = 3;


    // 得到sdcard的root
    public static final File SDCARD_ROOT = Environment.getExternalStorageDirectory();// .getAbsolutePath();
    public static final String PATH_IMAGES = SDCARD_ROOT + "/ssk/";
    public static final int MSG_SUCCESS = 11;
    public static final int MSG_FAILURE = 12;
    //图片地址
    public static final String URL_PICTURE_PATH = TApplication.URL_STRING + "getPictureFile?fileEntryId=";
    public static String PATH_APK = SDCARD_ROOT + "/ssk/apkDownload/";
    public static String PATH_NONGSH_IMAGE_PATH = SDCARD_ROOT + "/ssk/nongsh/";
    public static String PATH_WEN_IMAGE = SDCARD_ROOT+"/ssk/wen/";
    /*****************
     * 后台服务器基址
     **********************************/
    //登录模块
    public static String URL_LOGIN = TApplication.URL_STRING + "login.json?logname=";//jiwl&logpwd=123456
    public static String URL_EDIT_PSW = TApplication.URL_STRING + "resetpwd.json?uid=";//修改密码
    public static String URL_MODIFY_MINE = TApplication.URL_STRING + "updateUserinfo.json";//修改个人信息?uid=


    //升级模块
    public static String URL_UPDATE = TApplication.URL_STRING + "app/appversion";
    //农情站页面
    public static String URL_NQZHAN_WEB = TApplication.URL_STRING + "nqzindex.do?uid=";
    //随时看页面
    public static String URL_SSK_WEB = TApplication.URL_STRING + "sskindex.do?uid=";
    //告警页面
    public static String URL_WARN_WEB = TApplication.URL_STRING + "bjyjxq.do?uid=";
    //推送新闻资讯类页面
    public static String URL_NEWS_WEB = TApplication.URL_STRING + "nqzdetails.do?uid=";

    //常量
    public static String CODE_CHANNEL_ID = "channelId";//设备通道ID，百度推送分配给设备的id号；
    public static String CODE_NSH_VERSION_KEY = "nshversion";//农事汇图标版本号，判断是否需要更新图标

    //随时看 CMS url地址
    public static String URL_NONGSH_LIST = TApplication.URL_NONGSH_STRING+"/app/channelList.json";//农事汇图标列表地址
    public static String URL_HEAD_NONGSH_IMAGE = TApplication.URL_NONGSH_STRING;//农事汇图标地址头
    public static String URL_NONGSH_INFO = TApplication.URL_NONGSH_STRING+"/app/xwzx.do?chid=";
    public static String URL_WNN_LIST = TApplication.URL_NONGSH_STRING+"/app/wzj.do?userid=";
    public static String URL_ABOUT_WEB = TApplication.URL_NONGSH_STRING + "/app/about.do";
    //提问
    public static String URL_ASK_QUEST = TApplication.URL_NONGSH_STRING + "/app/addWen.json";

    public static String URL_SYSTEM_SETTING = TApplication.URL_STRING+"zzd/swzParams.do?uid=";
}
