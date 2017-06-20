package com.hxsn.zzd.utils;

import android.os.Environment;

import com.hxsn.zzd.TApplication;

import java.io.File;

/**
 * 常量
 */
public class Const {
    //version
    public static final int TEST_VERTION = 1;
    public static final int TEST_VERTION1 = 4;
    public static final int DEBUG_VERTION = 2;
    public static final int RELEASE_VERTION = 3;
    public static final int SQLITE_VERTION = 2;              //数据库版本
    public static final String SQLITE_DBNAME = "zzd";            //数据库名称

    // 得到sdcard的root
    public static final File SDCARD_ROOT = Environment.getExternalStorageDirectory();// .getAbsolutePath();
    public static final String PATH_IMAGES = SDCARD_ROOT + "/zzd/";
    public static final int MSG_SUCCESS = 11;
    public static final int MSG_FAILURE = 12;
    //图片地址
    public static final String URL_PICTURE_PATH = TApplication.URL_ZZD_STRING + "getPictureFile?fileEntryId=";
    public static String PATH_APK = SDCARD_ROOT + "/zzd/apkDownload/";
    public static String PATH_NONGSH_IMAGE_PATH = SDCARD_ROOT + "/zzd/nongsh/";
    public static String PATH_WEN_IMAGE = SDCARD_ROOT+"/zzd/wen/";
    /*****************
     * 后台服务器基址
     **********************************/
    //登录模块
    public static String URL_LOGIN = TApplication.URL_ZZD_STRING + "login.json?logname=";//jiwl&logpwd=123456
    public static String URL_EDIT_PSW = TApplication.URL_ZZD_STRING + "resetpwd.json?uid=";//修改密码
    public static String URL_MODIFY_MINE = TApplication.URL_ZZD_STRING + "updateUserinfo.json";//修改个人信息?uid=

    //升级模块
    public static String URL_UPDATE = TApplication.URL_ZZD_STRING + "zzd/appversion.json";

    //早知道页面
    public static String URL_ZZD_WEB = TApplication.URL_ZZD_STRING + "zzd/index.do?uid=";

    //农情站页面
    public static String URL_NQZHAN_WEB = TApplication.URL_CMS_STRING + "nsh.do?chid=40288c9a55ec67500155ec79ebcd0008&username=";
    //农事汇webView
    public static String URL_NSH_WEB = TApplication.URL_CMS_STRING + "nsh.do?chid=40288c9a55ec488c0155ec4d86720000&username=";
    //问专家webView
    public static String URL_NSH_WEN = TApplication.URL_CMS_STRING + "wzj.do?userid=";
    //关于
    public static String URL_ABOUT_WEB = TApplication.URL_CMS_STRING + "about.do";
    //提问
    public static String URL_ASK_QUEST = TApplication.URL_CMS_STRING + "addWen.json";
    //系统设置
    public static String URL_SYSTEM_SETTING = TApplication.URL_ZZD_STRING+"zzd/swzParams.do?uid=";
    //控制页面
    public static String URL_CONTROL = TApplication.URL_ZZD_STRING+"zzd/kongzhi.do";

    //告警页面
    public static String URL_WARN_WEB = TApplication.URL_ZZD_STRING + "zzd/bjyjxq.do?uid=";

    //获取用户未读警情信息数量
    public static String URL_WARN_READ = TApplication.URL_ZZD_STRING + "zzd/getUnreadMessageNums.json?uid=";

    //推送新闻资讯类页面
    public static String URL_NEWS_WEB = TApplication.URL_CMS_STRING + "xwzxLook.do?newsid=";

    //常量
    public static String CODE_CHANNEL_ID = "channelId";//设备通道ID，百度推送分配给设备的id号；
    public static String CODE_NSH_VERSION_KEY = "nshversion";//农事汇图标版本号，判断是否需要更新图标




}
