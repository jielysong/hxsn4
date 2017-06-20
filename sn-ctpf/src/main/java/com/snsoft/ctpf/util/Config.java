package com.snsoft.ctpf.util;

import android.util.Log;
import android.widget.Toast;

import com.snsoft.ctpf.R;
import com.snsoft.ctpf.TApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *  Created by jiely on 2016/5/20.
 */
public class Config {

    private static final String ERROR = "程序配置错误";


    //用来配制主页面底部的类，并且主页面只能为5个
    public static String[] mainclass=new String[]{
            "com.snsoft.ctpf.CTYFActivity",
            "com.snsoft.ctpf.CTMapTwoActivity",
            "com.snsoft.ctpf.EWMActivity",
            "com.snsoft.ctpf.more.CTSMSActivity"
    };

    //所有可用于配置的类
    private final static String[] activities= {
            "com.snsoft.ctpf.activity.CTYFActivity", "com.snsoft.ctpf.activity.CTMapActivity",
            "com.snsoft.ctpf.activity.EWMActivity", "com.snsoft.ctpf.activity.CTMapTwoActivity",
            "com.snsoft.ctpf.activity.CityActivity", "com.snsoft.ctpf.activity.CTHistoryActivity",
            "com.snsoft.ctpf.activity.OfflineMapActivity", "com.snsoft.ctpf.activity.CTConfigureActivity",
            "com.snsoft.ctpf.activity.CTFarmerSelectActivity", "com.snsoft.ctpf.activity.CTCityTwoActivity",
            "com.snsoft.ctpf.activity.CTSMSActivity", "com.snsoft.ctpf.activity.UpdataDataActivity",
            "com.snsoft.ctpf.activity.CTCheckVersionActivity",
    };//养分，地图，二维码，地图2，

    //用于配置底部菜单文字显示
    public static String[] configTabStr = new String[]{
            "养分","地图","二维码","短信","更多"
    };

    //更多页面类对应的菜单名称
    private final static String[] MORE_MENU_NAME= {
            "养分","地图", "二维码", "地图",
            "地区","历史记录", "离线地图","系统配置",
            "用户", "地区", "短信", "数据更新",
            "系统更新","肥料技术"
    };

    //bottom1:养分   bottom2:二维码     bottom3:历史    bottom4:短信    bottom5:更多
    //bottom6:地图   bottom7:用户         bottom8:离线   bottom9:区域      bottom10:更多
    //对应的按钮资源
    private final static int[] IMAGE_RESOURCE = {
            R.drawable.bottom1, R.drawable.bottom6, R.drawable.bottom2,R.drawable.bottom6,
            R.drawable.bottom9, R.drawable.bottom3, R.drawable.bottom8, R.drawable.bottom10,
            R.drawable.bottom7, R.drawable.bottom9, R.drawable.bottom4, R.drawable.bottom10,
            R.drawable.bottom10,
    };

    //bottom1:养分   bottom2:二维码     bottom3:历史    bottom4:短信    bottom5:更多
    //bottom6:地图   bottom7:用户         bottom8:离线   bottom9:区域      bottom10:更多
    public static int[] cinfigint=new int[]{ R.drawable.bottom1,R.drawable.bottom6,R.drawable.bottom2,R.drawable.bottom4,R.drawable.bottom10};

    //用来配制更多的页面的类，可以添加，但必须注意包名的路径不同，但必须写下边的文字信息，要不会报空指针异常
    public static  String [] moreActivities = new String[]{};
    //用来配制更多里的文字信息，但必须写上边的类信息，要不会报空指针异常
    public static String[] moreNames = new String[]{};

    public static int[] baseIndexs = new int[4];   //主界面的基础界面
    public static List<Integer> moreList = new ArrayList<>();   //更多页面的内容


    /**
     *
     * @return 初始化完成与否
     */
    public static boolean init(){
        TLog.i("数据初始化");
        Properties properties = new Properties();

        int index = 0;
        try {
            properties.load(TApplication.getInstance().getAssets().open("config.properties"));
            String base = properties.getProperty("base");//读取基础页面配置
            String more = properties.getProperty("more");//读取更多页面配置
            int maxIndex = Integer.valueOf(properties.getProperty("maxindex"));
            moreList.clear();
            //配置前台页面
            if(base==null){
                Toast.makeText(TApplication.context, ERROR, Toast.LENGTH_LONG).show();
                return false;
            }

            String[] bases = base.split(",");

            if(bases.length!=4){
                Toast.makeText(TApplication.context, ERROR, Toast.LENGTH_LONG).show();
                return false;
            }
            for(int i=0; i<4; i++){
                index = Integer.parseInt(bases[i]);
                baseIndexs[i] = index;
                if(index>maxIndex || index<0){
                    Toast.makeText(TApplication.context, ERROR, Toast.LENGTH_LONG).show();
                    return false;
                }
                configTabStr[i] = MORE_MENU_NAME[index];        //底部按钮的五个名称
                cinfigint[i] = IMAGE_RESOURCE[index];           //底部图标
            }

            if(more!=null && more.length() != 0){
                List<String> moreNameList = new ArrayList<String>();
                String[] moreActivityIndex = more.split(",");//更多Activity的下标
                Log.i("Config","more="+moreNameList);
                moreNames = new String[moreActivityIndex.length];
                int i = 0;
                for(String str : moreActivityIndex){
                    Log.i("Config","str="+str);
                    index = Integer.parseInt(str);
                    if(index>maxIndex || index<0){
                        showError();
                        System.exit(1);
                    }
                    moreList.add(index);
                    moreNameList.add(MORE_MENU_NAME[index]);
                    moreNames[i] = MORE_MENU_NAME[index];
                    i++;
                }
            }
            TLog.i("moreNames="+ Arrays.toString(moreNames));
            TLog.i("moreList="+ moreList.toString());
        } catch (IOException e) {
            return false;
        }
        TLog.i("数据初始化完成");
        return true;
    }

    private static void showError(){
        Toast.makeText(TApplication.context, ERROR, Toast.LENGTH_LONG).show();
    }
}
