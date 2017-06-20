package com.hxsn.ssklf.utils;

import android.os.Environment;

import com.hxsn.ssklf.TApplication;

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
    public static final String PATH_APK = SDCARD_ROOT + "/ssk/apkDownload/";
    public static final String PATH_NONGSH_IMAGE_PATH = SDCARD_ROOT + "/ssk/nongsh/";
    public static final String PATH_WEN_IMAGE = SDCARD_ROOT+"/ssk/wen/";
    /*****************
     * 后台服务器基址
     **********************************/

    //实时气象哨数据接口
    public static final String URL_REAL_TIME = TApplication.URL_STRING + "cur_site_value.json?uuId=";
    //前6小时每小时气象哨各指标平均值数据接口
    public static final String URL_SIX_HOUR = TApplication.URL_STRING + "six_site_val_list.json?uuId=";
    //前5天各气象哨日平均值数据接口
    public static final String URL_FIVE_DAYS = TApplication.URL_STRING + "fiveday_site_val_list.json?uuId=";
    //今天昨天前天气象哨分时数据接口
    public static final String URL_PRE_THREE = TApplication.URL_STRING + "get_siteval_list.json?uuId=";
    //获取报警预警列表
    public static final String URL_WARNING = TApplication.URL_STRING + "site_warnning_list.json?uuId=";
    //获取气象站列表
    public static final String URL_GET_SITES = TApplication.URL_STRING + "getSites.json";
    //升级模块
    public static final String URL_UPDATE = TApplication.URL_STRING + "appversion.json";
    //历史数据接口
    public static final String URL_HISTORY = TApplication.URL_STRING + "get_history.json?uuId=";



    //农情站页面
    public static String URL_NQZHAN_WEB = "http://115.28.140.121:8280/zzdcms/app/" + "nsh.do?chid=40288c9a55ec67500155ec79ebcd0008";
    //随时看页面
    public static final String URL_SSK_WEB = TApplication.URL_STRING + "sskindex.do?uid=";
    //告警页面
    public static final String URL_WARN_WEB = TApplication.URL_STRING + "bjyjxq.do?uid=";
    //推送新闻资讯类页面
    public static final String URL_NEWS_WEB = TApplication.URL_STRING + "nqzdetails.do?uid=";

    //常量
    public static final String CODE_CHANNEL_ID = "channelId";//设备通道ID，百度推送分配给设备的id号；
    public static final String CODE_NSH_VERSION_KEY = "nshversion";//农事汇图标版本号，判断是否需要更新图标

    //随时看 CMS url地址
    public static final String URL_NONGSH_LIST = TApplication.URL_NONGSH_STRING+"/app/channelList.json";//农事汇图标列表地址
    public static final String URL_HEAD_NONGSH_IMAGE = TApplication.URL_NONGSH_STRING;//农事汇图标地址头
    public static final String URL_NONGSH_INFO = TApplication.URL_NONGSH_STRING+"/app/xwzx.do?chid=";



    public static final int[] SETTINGS = {20,30,30,80,16,25,50,200};
    public static final int[] COLORS = {0x3A9C36,0xEB6100,0x2217F4};

    public static final String[] UUIDARRAY = {"2c908a905905bb180159067cee7b000a","2c908a905905bb180159067d7972000f","2c908a905905bb180159067de19b0015",
            "2c908a905905bb180159067e27e6001a","2c908a905905bb180159067f213f0023","2c908a905905bb180159067f646b0028","2c908a905905bb18015906804a6d002f",
            "2c908a905905bb1801590680f4c50039","2c908a905905bb1801590680bb7c0034","2c908a905905bb18015906812ea3003e"};
}
