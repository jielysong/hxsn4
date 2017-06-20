package com.snsoft.ctpf.util;

import com.hxsn.ssk.TApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiely on 2016/5/23.
 */
public class CTPFUtil {

    private final static String ADD_AREA = "additional";
    private static List<String> AREA_LIST = new ArrayList<String>();

    /**
     * 读取扩展区域  城市的各个区
     */
    public static void getAdditionalArea(){
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = TApplication.getInstance().getAssets().open(ADD_AREA);
            reader = new BufferedReader(new InputStreamReader(is));
            String areaStr = reader.readLine();
            if(areaStr==null){
                return;
            }
            for(String area: areaStr.split(",")){
                AREA_LIST.add(area);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(reader!=null){
                    reader.close();
                }
                if(is!=null){
                    is.close();
                }
            }catch(IOException ex){
                TLog.error(ex.getMessage());
            }

        }
    }

    /**
     * 判断当前地域是否在扩展区域内
     * @param areaStr
     * @return
     */
    public static boolean inHere(String areaStr){
        for(String s : AREA_LIST){
            if(areaStr.contains(s)){
                return true;
            }
        }
        return false;
    }
}
