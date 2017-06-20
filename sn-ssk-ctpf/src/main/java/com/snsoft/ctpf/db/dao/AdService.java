package com.snsoft.ctpf.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snsoft.ctpf.db.DbManager;

/**
 * 广告service方法
 * @author malq  Feb 13, 2012 3:48:09 PM
 *
 */
public class AdService{
	private static SQLiteDatabase database;
	static {
		database = DbManager.getInstance().getLocalDatabase();
	}
	public AdService(){
		database = DbManager.getInstance().getLocalDatabase();
	}
	/**
	 * 获取广告的方法，传入区域编码，得到对应区域的广告
	 * @param countyid
	 * @return
	 */
	public String getAd(String countyid) {
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select advertisement from sn_advertisement where countyid='"+countyid+"'";
		Cursor cur =  database.rawQuery(sql, null);
		try {
			String val = null;
	        if(cur.getCount()>0){
	        	if(cur.moveToNext()){
	        		val = cur.getString(cur.getColumnIndex("advertisement"));
	        	}
	        }

			return val;
		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
                if (cur != null)
                	cur.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
		return null;
	}

    
}
