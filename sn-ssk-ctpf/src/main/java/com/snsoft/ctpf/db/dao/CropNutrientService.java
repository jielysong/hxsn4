package com.snsoft.ctpf.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snsoft.ctpf.beans.CropNutrientInfo;
import com.snsoft.ctpf.db.DbManager;

import java.util.ArrayList;


/**
 * 作物需肥service方法
 * @author malq  Feb 13, 2012 3:48:09 PM
 *
 */
public class CropNutrientService{
	private SQLiteDatabase database;
	public CropNutrientService(){
		database = DbManager.getInstance().getLocalDatabase();
	}
    

	/**
	 * 作物需肥信息
	 * 2012年8月9日增加了对需肥量底限的设置
	 * @return
	 */
    public CropNutrientInfo getCropNutrientInfo(String cropid, int yield){
    	String sql;
		sql = "SELECT * FROM SN_CROP_FERTILIZER_REQUIREMENT WHERE CROPID=? and yieldfloor<=? order by yieldfloor desc";
		String[] fields=new String[2];
		fields[0]=cropid;
		fields[1]=String.valueOf(yield);
		CropNutrientInfo cni=new CropNutrientInfo();
		Cursor cur=null;
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			ArrayList list=new ArrayList();
	        if(cur.moveToNext()){
	        	for (int i = 0; i <cur.getColumnCount(); i++) {
	                String name = cur.getColumnName(i).trim();
	                    if (name.equalsIgnoreCase("N"))
	                    	cni.setN(cur.getDouble(i)*yield/100);
	                    else if (name.equalsIgnoreCase("P"))
	                    	cni.setP(cur.getDouble(i)*yield/100);
	                    else if (name.equalsIgnoreCase("K"))
	                    	cni.setK(cur.getDouble(i)*yield/100);
	                    else if (name.equalsIgnoreCase("NBASE"))
	                    	cni.setNbase(cur.getInt(i));
	                    else if (name.equalsIgnoreCase("PBASE"))
	                    	cni.setPbase(cur.getInt(i));
	                    else if (name.equalsIgnoreCase("KBASE"))
	                    	cni.setKbase(cur.getInt(i));
	                    //--其他有用到的字段再写代码
	            }
	    	}
        }
        catch (Exception ex) {
            //throw ex;
        	ex.printStackTrace();
        }
        finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
            	if (cur!=null){
            		cur.close();
            	}
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cni;
    }

}
