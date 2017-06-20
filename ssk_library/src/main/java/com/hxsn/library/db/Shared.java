package com.hxsn.library.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.hxsn.library.beans.User;

/**
 *  Created by jiely on 2016/6/28.
 */
public class Shared {

    private static SharedPreferences shared;

    public static void init(Context context,String sharedName) {
        shared = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        Log.i("Shared","shared="+shared);
    }

    public  static void setValue(String key,String value) {
        shared.edit().putString(key, value).apply();
    }

    public  static String getValue(String key) {
        return shared.getString(key, "");
    }


    public static void setVersion(String version) {
        shared.edit().putString("version", version).apply();
    }
    public static String getVersion() {
        return shared.getString("version", "");
    }

    public static User getUser(){
        User user = new User();
        String value = shared.getString("userID", "");         user.setUserId(value);
        value = shared.getString("userName", "");user.setUserName(value);
        value = shared.getString("password", "");       user.setPassword(value);
        value = shared.getString("control_psw", "");       user.setControlPwd(value);
        value = shared.getString("realName", "");       user.setRealName(value);
        value = shared.getString("nickName", "");       user.setNickName(value);
        value = shared.getString("phone", "");          user.setPhone(value);
        value = shared.getString("address", "");        user.setAddress(value);
        value = shared.getString("email", "");          user.setEmail(value);
        value = shared.getString("code", "");           user.setCode(value);
        value = shared.getString("sex", "");            user.setSex(value);
        value = shared.getString("headUrl", "");        user.setHeadUrl(value);
        value = shared.getString("status", "");        user.setStatus(value);

        return user;
    }

    public static void setUser(User user){
        if(user != null){
            String str = user.getUserId();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("userID", str).apply();
            }

            str = user.getUserName();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("userName", str).apply();
            }

            str = user.getPassword();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("password", str).apply();
            }

            str = user.getControlPwd();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("control_psw", str).apply();
            }

            str = user.getRealName();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("realName", str).apply();
            }

            str = user.getNickName();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("nickName", str).apply();
            }

            str = user.getPhone();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("phone", str).apply();
            }

            str = user.getAddress();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("address", str).apply();
            }

            str = user.getEmail();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("email", str).apply();
            }

            str = user.getCode();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("code", str).apply();
            }

            str = user.getSex();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("sex", str).apply();
            }

            str = user.getHeadUrl();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("headUrl", str).apply();
            }

            str = user.getStatus();
            if(!TextUtils.isEmpty(str)){
                shared.edit().putString("status", str).apply();
            }
        }
    }

    public static void clearUser(){
            shared.edit().putString("userID", "").apply();
            shared.edit().putString("userName", "").apply();
            shared.edit().putString("password", "").apply();
            shared.edit().putString("realName", "").apply();
            shared.edit().putString("nickName", "").apply();
            shared.edit().putString("phone", "").apply();
            shared.edit().putString("address", "").apply();
            shared.edit().putString("email", "").apply();
            shared.edit().putString("code", "").apply();
            shared.edit().putString("sex", "").apply();
            shared.edit().putString("headUrl", "").apply();
            shared.edit().putString("welcome", "").apply();

    }

    public static void setIsFirstRun(boolean isFirstRun) {
        shared.edit().putBoolean("isFirstRun", isFirstRun).apply();
    }
    public static boolean getIsFirstRun() {
        return shared.getBoolean("isFirstRun", false);
    }



    public static void setMessageAlert(String flag) {
        shared.edit().putString("messageAlert", flag).apply();
    }
    public static String getMessageAlert() {
        return shared.getString("messageAlert", "1");
    }

    public static void setInt(String key, int count){
        shared.edit().putInt(key, count).apply();
    }

    public static int getInt(String key, int i){
        return shared.getInt(key, 0);
    }

}
