package com.hxsn.ssklf.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 我的工具类
 *  Created by jiely on 2016/12/15.
 */
public class Tools {


    /**
     * 获取当前时间 月日时分
     * @return
     */
    public static String getCurTime(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return month+"月"+day+"日  "+hour+":"+minute;
    }

    /**
     * 获取当前是几点
     * @return
     */
    public static int getHour(){
       // Time time = new Time("GMT+8");
     //   time.setToNow();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     *
     * @param time 输入采集时间
     * @param t 时间差阈值
     * @return 是否算是实时采集
     */
    public static boolean isRealTime(String time, int t){
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        SimpleDateFormat sdf =   new SimpleDateFormat("MM月dd日 HH:mm");
        //int year = c.get(Calendar.YEAR);
        try {
            Date date = sdf.parse(time);
            /*date.setYear(year);

            date.getTime();
            c.getTimeInMillis();

            Log.i("Tools","----------------------------------------------year="+year);
            Log.i("Tools","get-time="+  date.getTime());
            Log.i("Tools","real-time="+  c.getTimeInMillis());

            Date dateThis = new Date(date.getTime());
            Date dateReal = new Date(c.getTimeInMillis());
            Log.i("Tools","----------------------------------------------");
            Log.i("Tools","dateThis="+  dateThis.toString());
            Log.i("Tools","dateReal="+  dateReal.toString());*/

            int oldHour = date.getHours();
            int oldMinute = date.getMinutes();
            int oldDay = date.getDate();

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int day = c.get(Calendar.DATE);

            int subHour = hour-oldHour;
            int sub = 0;
            if(oldDay != day){//日期都不相等了，就不用再判断了，直接断定不是实时的
                if(subHour != -23){
                    return false;
                }else{
                    sub = 60+minute-oldMinute;
                }
            }else {
                if(subHour > 0){
                    int subM = minute - oldMinute;
                    if(subM > 0){
                        sub = subHour*60+subM;
                    }else {
                        sub = (subHour-1)*60-60+subM;
                    }
                }else if(subHour == 0){//同一个小时内认为是实时的
                    return true;
                }else {//当前时间比采集时间小，唯一可能就是不是实时的
                    return false;
                }
            }

            if(sub < t){
                return true;
            }else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isRealTime1(String time, String realTime){

        SimpleDateFormat sdf =   new SimpleDateFormat("MM月dd日 HH:mm");

        try {
            Date date = sdf.parse(time);
            int oldHour = date.getHours();
            int oldMinite = date.getMinutes();
            int oldDay = date.getDay();


            Date realDate = sdf.parse(realTime);
            int hour = realDate.getHours();
            int minute = realDate.getMinutes();
            int day = realDate.getDay();

            int subHour = hour-oldHour;
            int sub = 0;
            if(oldDay != day){//日期都不相等了，就不用再判断了，直接断定不是实时的
                if(subHour != -23){
                    return false;
                }else{
                    sub = 60+minute-oldMinite;
                }
            }else {
                if(subHour > 0){
                    int subM = minute - oldMinite;
                    if(subM > 0){
                        sub = subHour*60+subM;
                    }else {
                        sub = (subHour-1)*60-60+subM;
                    }
                }else {//当前时间比采集时间小，唯一可能就是不是实时的
                    return false;
                }
            }

            if(sub < 60){
                return true;
            }else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取当前是几点的下一小时
     * @param hour
     * @return
     */
    public static int getNextHour(int hour){

        int nextHour = hour+1;
        if(nextHour == 24){
            nextHour = 0;
        }
        return nextHour;
    }

    /**
     * 获取当前是几点的前五小时
     * @param hour
     * @return
     */
    public static int getPre5Hour(int hour){

        int preHour = hour-6;
        switch (preHour){
            case -5:
                preHour = 19;
                break;
            case -4:
                preHour = 20;
                break;
            case -3:
                preHour = 21;
                break;
            case -2:
                preHour = 22;
                break;
            case -1:
                preHour = 23;
                break;
        }

        return preHour;
    }

    /**
     * 根据周几的整形获得字符型星期几
     * @return 周几
     */
    public static String getWeekStr(int week){
        String mWay = "";
        //int way = getIntWeek();
        switch (week){
            case 1:
                mWay ="日";
                break;
            case 2:
                mWay ="一";
                break;
            case 3:
                mWay ="二";
                break;
            case 4:
                mWay ="三";
                break;
            case 5:
                mWay ="四";
                break;
            case 6:
                mWay ="五";
                break;
            case 7:
                mWay ="六";
                break;
        }

        return "星期"+mWay;
    }

    /**
     * 获取当前是星期几
     * @return
     */
    public static int getIntWeek(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前星期几的上一天是星期几
     * @param week
     * @return
     */
    public static String getPreWeek(int week){
        String mWay = "";
        if(week == 0){
            week = 7;
        }
        int preWeek = week-1;
        if(preWeek == 0){
            preWeek = 7;
        }
        switch (preWeek){
            case 1:
                mWay ="日";
                break;
            case 2:
                mWay ="一";
                break;
            case 3:
                mWay ="二";
                break;
            case 4:
                mWay ="三";
                break;
            case 5:
                mWay ="四";
                break;
            case 6:
                mWay ="五";
                break;
            case 7:
                mWay ="六";
                break;
        }

        return "星期"+mWay;
    }
    /**
     * Nan转换为0
     * @param nan
     * @return
     */
    public static double nanToZero1(double nan){
        if(Double.isNaN(nan)){
            return 0;
        }else {
            return nan;
        }
    }


    /**
     * 获取某一个数在刻度中的位置
     * @param array
     * @param value
     * @return
     */
    public static int getLabelIndex(List<Integer> array, double value){
        int index = 0;
        for(Integer s: array){
            if(value < s){
                break;
            }
            index++;
        }
        return index;
    }
}
