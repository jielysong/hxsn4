package com.hxsn.ssklf.utils;

import android.util.Log;


import com.hxsn.library.utils.ExceptionUtil;
import com.hxsn.ssklf.model.SiteInfo;
import com.hxsn.ssklf.model.SiteValue;
import com.hxsn.ssklf.model.Threshold;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by jiely on 2016/10/10.
 */
public class JsonUtil {

    /**
     * 	"upTemp":15,
     "downTemp":15,
     "upHumidity":45,
     "downHumidity":45,
     "upTemp15cm":14,
     "downTemp15cm":14,
     "upSunlight":600,
     "downSunlight":600,
     */
    private static final String[] thresHoldArray = {"upTemp","downTemp","upHumidity","downHumidity","upTemp15cm","downTemp15cm","upSunlight","downSunlight"};

    /**
     * param  jsonString
     * return String    返回类型
     * author：jiely
     * Date：2015-2-5
     * Title: getToken
     * Description: 从后台获取Token
     */
    public static boolean getStatus(String jsonString) {
        try {
            Log.i("JsonUtil","jsonString="+jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return false;
            }
            return jsonObject.optBoolean("status");
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return false;
    }

    public static String getDescription(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return null;
            }
            String desc = jsonObject.optString("description");
            return desc;
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return null;
    }

    //weather windDirection windVelocity airTemp surfaceTemp humidity visible temp10cm temp20cm temp40cm
    /**
     * json解析 获取参数阈值
     * @param  jsonString json串
     * @return 阈值
     */
    public static Threshold getThreshold(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.optJSONObject("threshold");
            Threshold threshold = new Threshold();
            int hold = jsonObject.optInt(thresHoldArray[0]);
            threshold.setUpTemp(hold);
            hold = jsonObject.optInt(thresHoldArray[1]);
            threshold.setDownTemp(hold);
            hold = jsonObject.optInt(thresHoldArray[2]);
            threshold.setUpHumidity(hold);
            hold = jsonObject.optInt(thresHoldArray[3]);
            threshold.setDownHumidity(hold);
            hold = jsonObject.optInt(thresHoldArray[4]);
            threshold.setUpTemp15cm(hold);
            hold = jsonObject.optInt(thresHoldArray[5]);
            threshold.setDownTemp15cm(hold);
            hold = jsonObject.optInt(thresHoldArray[6]);
            threshold.setUpSunlight(hold);
            hold = jsonObject.optInt(thresHoldArray[7]);
            threshold.setDownSunlight(hold);
            return threshold;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //weather windDirection windVelocity airTemp surfaceTemp humidity visible temp10cm temp20cm temp40cm
    /**
     * json解析 获取气象站数据
     * @param  jsonString json串
     * @return 气象站数据
     */
    public static SiteValue getSiteValue(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.optJSONObject("siteVal");
            SiteValue siteValue = paseSiteValue(jsonObject);
            return siteValue;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json解析 获取气象站数据列表
     * @param  jsonString json串
     * @return 列表
     */
    public static List<SiteValue> getSiteList(String jsonString){
        List<SiteValue> siteList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.optJSONArray("siteValList");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                SiteValue siteValue = paseSiteValue(jsonObject);
                siteList.add(siteValue);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return siteList;
    }

    private static SiteValue paseSiteValue(JSONObject jsonObject){
        SiteValue siteValue = new SiteValue();
        siteValue.setTimeInfo(jsonObject.optString("timeInfo"));
        Double airTemp = jsonObject.optDouble("airTemp");
        siteValue.setTemperature(airTemp);
        Double humidity = jsonObject.optDouble("humidity");
        siteValue.setHumidity(humidity);
        airTemp = jsonObject.optDouble("temp15cm");
        siteValue.setSoilTemp(airTemp);
        humidity = jsonObject.optDouble("sunlight");
        siteValue.setIllu(humidity);
        return siteValue;
    }

    /**
     * json解析 获取气象站列表
     * @param  jsonString json串
     * @return 气象站列表
     */
    public static List<SiteInfo> getSiteInfoList(String jsonString){
        List<SiteInfo> siteInfoList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.optJSONArray("siteList");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                SiteInfo siteValue = new SiteInfo();
                String str = jsonObject.optString("name");
                siteValue.setName(str);
                str = jsonObject.optString("uuid");
                siteValue.setUuid(str);
                siteInfoList.add(siteValue);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return siteInfoList;
    }

}
