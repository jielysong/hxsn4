package com.hxsn.library.utils;


import android.text.TextUtils;

import com.hxsn.library.beans.AppVersion;
import com.hxsn.library.beans.Nongsh;
import com.hxsn.library.beans.NotifyInfo;
import com.hxsn.library.beans.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @date Created by jiely on 2016/4/19.
 * * "title":"农情站",
"description":"有新鲜水蜜桃出售，价格优惠",
"notification_builder_id":0,
"notification_basic_style":7,
"open_type":2,	//自定义打开方式
"custom_content":{
"type":1,	//消息类型：1表示农情站资讯信息
"id":"40288ca1548020210154802269e30001"	//资讯ID
 */
public class JsonUtil {

    private static final String[] userList = {"userid", "username", "realname", "nickname", "email", "phone", "address"};
    private static final String[] appVersionList = {"createDate", "description", "version", "url","nshversion"};
    private static final String[] notifyInfoList = {"id","type","open_type","description","title","notification_builder_id","notification_basic_style"};
    /**
     * desc:user解析
     * auther:jiely
     * create at 2015/10/10 19:52
     */
    public static User getUser(String jsonString) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            String userId = jsonObject.optString(userList[0]);
            user.setUserId(userId);
            String userName = jsonObject.optString(userList[1]);
            user.setUserName(userName);
            String realName = jsonObject.optString(userList[2]);
            user.setRealName(realName);
            String nickName = jsonObject.optString(userList[3]);
            user.setNickName(nickName);
            String email = jsonObject.optString(userList[4]);
            user.setEmail(email);
            String phone = jsonObject.optString(userList[5]);
            user.setPhone(phone);
            String address = jsonObject.optString(userList[6]);
            user.setAddress(address);
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
        return user;
    }

    /**
     * desc:通知消息解析
     * auther:jiely
     * create at 2016/5/6 10:52
     */
    public static NotifyInfo getNotifyInfo(String jsonString) {
        NotifyInfo notifyInfo = new NotifyInfo();
        if(TextUtils.isEmpty(jsonString)){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            String id = jsonObject.optString(notifyInfoList[0]);
            notifyInfo.setId(id);
            int type = jsonObject.optInt(notifyInfoList[1]);
            notifyInfo.setType(type);

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
        return notifyInfo;
    }

    /**
     * desc:从后台获取APP版本信息
     * auther:jiely
     * create at 2015/10/10 19:52
     */
    public static AppVersion getAppVersion(String jsonString) {
        AppVersion appVersion = new AppVersion();
        try {
            // JSONObject jsonObject = new JSONObject(jsonString);
            //  JSONObject jsonversion = jsonObject.optJSONObject(appVersionList[0]);
            JSONObject jsonversion = new JSONObject(jsonString);
            String createDate = jsonversion.optString(appVersionList[0]);
            appVersion.setCreateDateString(createDate);
            String description = jsonversion.optString(appVersionList[1]);
            appVersion.setDesc(description);
            String strVersion = jsonversion.optString(appVersionList[2]);
            appVersion.setVersion(strVersion);
            String url = jsonversion.optString(appVersionList[3]);
            appVersion.setUrl(url);
            int nshversion = jsonversion.optInt(appVersionList[4]);
            appVersion.setNshversion(nshversion);
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
        return appVersion;
    }

    private static final String[] nongshes = {"id", "name", "image"};

    public static List<Nongsh> getNongshList(String jsonString){
        List<Nongsh> nongshList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.optJSONArray("channellist");

            for(int i=0; i<jsonArray.length();i++){
                jsonObject = jsonArray.optJSONObject(i);
                String id = jsonObject.optString(nongshes[0]);
                String name = jsonObject.optString(nongshes[1]);
                String image = jsonObject.optString(nongshes[2]);
                Nongsh nongsh = new Nongsh(id,name,image);
                nongshList.add(nongsh);
            }
            return nongshList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
