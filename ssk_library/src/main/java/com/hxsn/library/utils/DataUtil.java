package com.hxsn.library.utils;

/**
 * desc: Created by jiely on 2015/9/16.
 */
public class DataUtil {

    /**
     * desc:0 1 2 3 4 5 6 ->   0 1 3 7 15 31
     * auther:jiely
     * create at 2015/11/6 15:13
     */
    public static int numToMode1(int num) {
        if (num < 2) {
            return num;
        }
        int mode = 1;
        for (int i = 0; i < num - 1; i++) {
            mode = (mode << 1) + 1;
        }
        return mode;
    }

    /**
     * desc:0 1 2 3 4 5 6 ->   1 2 4 8 16 32
     * auther:jiely
     * create at 2015/11/6 15:13
     */
    public static int numToMode2(int num) {
        if (num == 0) {
            return 1;
        }
        int mode = 1;
        for (int i = 0; i < num; i++) {
            mode = mode << 1;
        }
        return mode;
    }

    /**
     * desc: 2015/11/6 17:17 返回 2015/11/6
     * auther:jiely
     * create at 2015/11/6 17:17
     */
    public static String getAdate(String date) {
        String[] dates = date.split(" ");
        return dates[0];
    }


    /**
     * desc: 2015/11/6 17:17 返回 2015/11/6
     * auther:jiely
     * create at 2015/11/6 17:17
     */
    public static String getClock(String date) {
        String[] dates = date.split(" ");
        return dates[1];
    }

    /**
     * path="http://192.168.1.11:8080/laiba-app/icons/shop/imgs/1.jpg"
     * desc: 通过url获取图片文件名称，如1.jpg
     * auther:jiely
     * create at 2015/10/10 20:44
     */
    public static String getImageFileName(String path) {
        String fileName = "";
        char[] chs;
        chs = path.toCharArray();
        int i = path.length() - 1;
        for (; i > 0; i--) {
            if (chs[i] == '/') {
                break;
            }
        }
        fileName = path.substring(i);
        return fileName;
    }

    /**
     * desc:把字符串中的数字提取出来，顺序组成数字
     * auther:jiely
     * create at 2015/10/10 19:53
     */
    public static int getNumber(String str) {
        if (str == null) {
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 0x30 && str.charAt(i) <= 0x39) {
                stringBuilder.append(str.charAt(i));
            }
        }

        String strNumber = stringBuilder.toString();

        return Integer.parseInt(strNumber);
    }

    /**
     * desc:判断版本号是否是新的
     * auther:jiely
     * create at 2015/11/23 19:39
     */
    public static boolean isNewVersion(String newVersion, String oldVersion) {
        String[] olds = oldVersion.split("\\.");
        String[] news = newVersion.split("\\.");

        for (int i = 0; i < olds.length; i++) {
            Integer newv = Integer.parseInt(news[i]);
            Integer oldv = Integer.parseInt(olds[i]);
            if (newv > oldv) {
                return true;
            } else if (newv < oldv) {
                return false;
            } else {
                if (i == olds.length - 1) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * desc: 把double类型距离转换为字符串 如20.0->20
     * auther:jiely
     * create at 2015/11/23 19:25
     */
    public static String doubleToStr(double distance) {
        String dis;
        int number = (int) distance;

        if (distance < number + 0.001 && distance > number - 0.001) {
            dis = String.valueOf(number);
        } else {
            dis = String.valueOf(distance);
        }
        return dis;
    }

    /**
     * desc: 2015-11-6 17:17 返回 2015年11月6日
     * auther:jiely
     * create at 2015/11/6 17:17
     */
    public static String getBdate(String date) {
        date = getAdate(date);
        String[] dates = {};
        StringBuilder sb = new StringBuilder();
        if (date.contains("-")) {
            dates = date.split("-");
        }
        if (date.contains("/")) {
            dates = date.split("/");
        }
        sb.append(dates[0]).append("年").append(dates[1]).append("月").append(dates[2]).append("日");
        return sb.toString();
    }

    // 转化字符串为十六进制编码
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


}
