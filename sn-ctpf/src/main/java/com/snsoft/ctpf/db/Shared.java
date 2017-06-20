package com.snsoft.ctpf.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.snsoft.ctpf.beans.SysParam;

/**
 * Created by jiely on 2016/6/28.
 */
public class Shared {

    private static Shared instance;
    private static SharedPreferences sharedPreferences;

    private Shared(Context context){
    }

    public static Shared getInstance(Context context){
        sharedPreferences =  context.getSharedPreferences("ssk_dic", Context.MODE_PRIVATE);
        if(instance == null){
            instance = new Shared(context);
        }
        return instance;
    }

    public  void setValue(String key,String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public  String getValue(String key) {
        return sharedPreferences.getString(key, "");
    }


    public void setSysParam(SysParam sysParam){
        sharedPreferences.edit().putString("area", sysParam.getArea()).apply();
        sharedPreferences.edit().putString("url", sysParam.getUrl()).apply();
        sharedPreferences.edit().putString("phone", sysParam.getPhone()).apply();
        sharedPreferences.edit().putBoolean("jumpFer", sysParam.isJumpFer()).apply();
        sharedPreferences.edit().putBoolean("addFer", sysParam.isAddFer()).apply();
    }

    public SysParam getSysParam(){
        SysParam sysParam = new SysParam();
        String str = sharedPreferences.getString("area","");
        sysParam.setArea(str);
        str = sharedPreferences.getString("url","");
        sysParam.setUrl(str);
        str = sharedPreferences.getString("phone","10086");
        sysParam.setPhone(str);
        boolean is = sharedPreferences.getBoolean("jumpFer",false);
        sysParam.setJumpFer(is);
        is = sharedPreferences.getBoolean("addFer",true);
        sysParam.setAddFer(is);
        return sysParam;
    }


    public  void setInt(String key,int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public  int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

}
