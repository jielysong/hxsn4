package com.snsoft.ctpf.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snsoft.ctpf.beans.CropInfo;
import com.snsoft.ctpf.db.DbManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 种植作物service方法
 * @author malq  Feb 13, 2012 3:48:09 PM
 *
 */
public class CropService{

	private SQLiteDatabase database;
	public CropService(){
		database = DbManager.getInstance().getLocalDatabase();
	}
    

	/**
	 * 获得本县所有作物列表
	 * @return
	 */
    public List<CropInfo> getCropInfoList(String countyid){
    	//必须得到本县作物
    	String sql="SELECT * FROM SN_CROP WHERE COUNTYID=? AND CROPSTATE='1'";
        sql = sql + " ORDER BY SORTVALUE ASC ";
		String[] fields=new String[1];
		fields[0]=countyid;
		Cursor cur=null;
		ArrayList<CropInfo> list=new ArrayList<CropInfo>();
	try {
			//SQLiteDatabase database = DbManager.getInstance().getLocalDatabase();
			cur =  database.rawQuery(sql,fields);
			while(cur.moveToNext()){
				CropInfo crop=new CropInfo();
				for (int i = 0; i <cur.getColumnCount(); i++) {
					String name =cur.getColumnName(i).trim();
                    if (name.equalsIgnoreCase("CROPID"))
                    	crop.setCropid(cur.getString(i));
                    else if (name.equalsIgnoreCase("CROPNAME"))
                    	crop.setCropname(cur.getString(i));
                    else if (name.equalsIgnoreCase("RECOMMENDEDVARIETIES"))
                    	crop.setRecommendedvarieties(cur.getString(i));
                    else if (name.equalsIgnoreCase("CROPSTATE"))
                    	crop.setCropstate(cur.getString(i));
                    else if (name.equalsIgnoreCase("LOWERLIMIT"))
                    	crop.setLowerlimit(cur.getInt(i));
                    else if (name.equalsIgnoreCase("TOPLIMIT"))
                    	crop.setToplimit(cur.getInt(i));	
                    else if (name.equalsIgnoreCase("DIVIDE"))
                    	crop.setDivide(cur.getInt(i));
                    else if (name.equalsIgnoreCase("OTHERFERTILIZERS"))
                    	crop.setOtherfertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("FERTILIZERS"))
                    	crop.setFertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("TOPDRESSING"))
                    	crop.setTopdressing(cur.getString(i));
                    else if (name.equalsIgnoreCase("MICRO"))
                    	crop.setMicro(cur.getString(i));
                    else if(name.equalsIgnoreCase("GOODSNAME"))
                    	crop.setGoodsname(cur.getString(i));
                    else if(name.equalsIgnoreCase("SORTVALUE")){
                    	crop.setSortvalue(cur.getString(i));
                    }else if(name.equalsIgnoreCase("SCHEME")){
                    	crop.setScheme(cur.getInt(i));
                    }
                    //--其他有用到的字段再写代码
				}
				list.add(crop);
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
    	return list;

    }
	
	/**
	 * 获得本县、本乡所有作物列表
	 * @return
	 */
    public List<CropInfo> getCropInfoList(String countyid,String xiangming){
    	//必须得到本县作物
    	String sql;
    	sql="SELECT * FROM SN_CROP WHERE COUNTYID=? AND CROPSTATE='1'";
        sql=sql+ " and cropid in(select cropid from sn_town_crops where townname=?)";
        if(xiangming==null || xiangming.length()<=1){
        	//不按乡查询
        	sql="SELECT * FROM SN_CROP WHERE COUNTYID=? AND CROPSTATE='1'";
        }
        sql = sql + " ORDER BY SORTVALUE ASC ";
//		String[] fields=new String[1];
//		fields[0]=countyid;
		String[] fields;
		if(xiangming==null || xiangming.length()<=1){
			fields=new String[1];
			fields[0]=countyid;
		} else {
			fields=new String[2];
			fields[0]=countyid;
			fields[1]=xiangming;
		}
		
		Cursor cur=null;
		ArrayList<CropInfo> list=new ArrayList<CropInfo>();
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			while(cur.moveToNext()){
				CropInfo crop=new CropInfo();
				for (int i = 0; i <cur.getColumnCount(); i++) {
					String name =cur.getColumnName(i).trim();
                    if (name.equalsIgnoreCase("CROPID"))
                    	crop.setCropid(cur.getString(i));
                    else if (name.equalsIgnoreCase("CROPNAME"))
                    	crop.setCropname(cur.getString(i));
                    else if (name.equalsIgnoreCase("RECOMMENDEDVARIETIES"))
                    	crop.setRecommendedvarieties(cur.getString(i));
                    else if (name.equalsIgnoreCase("CROPSTATE"))
                    	crop.setCropstate(cur.getString(i));
                    else if (name.equalsIgnoreCase("LOWERLIMIT"))
                    	crop.setLowerlimit(cur.getInt(i));
                    else if (name.equalsIgnoreCase("TOPLIMIT"))
                    	crop.setToplimit(cur.getInt(i));	
                    else if (name.equalsIgnoreCase("DIVIDE"))
                    	crop.setDivide(cur.getInt(i));
                    else if (name.equalsIgnoreCase("OTHERFERTILIZERS"))
                    	crop.setOtherfertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("FERTILIZERS"))
                    	crop.setFertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("TOPDRESSING"))
                    	crop.setTopdressing(cur.getString(i));
                    else if (name.equalsIgnoreCase("MICRO"))
                    	crop.setMicro(cur.getString(i));
                    else if(name.equalsIgnoreCase("GOODSNAME"))
                    	crop.setGoodsname(cur.getString(i));
                    else if(name.equalsIgnoreCase("SORTVALUE")){
                    	crop.setSortvalue(cur.getString(i));
                    }else if(name.equalsIgnoreCase("SCHEME")){
                    	crop.setScheme(cur.getInt(i));
                    }
                    //--其他有用到的字段再写代码
				}
				list.add(crop);
	        	
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
    	return list;
    }
    
    /**
     * 返回根据作物ID返回作物信息
     * @return
     * @throws
     */
    public CropInfo getCropInfo(String cropid){
    	//必须得到本县作物
    	String sql="SELECT * FROM SN_CROP WHERE CROPID=?";
		String[] fields=new String[1];
		fields[0]=cropid;
		Cursor cur=null;
		CropInfo crop=new CropInfo();
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
				for (int i = 0; i <cur.getColumnCount(); i++) {
					String name =cur.getColumnName(i).trim();
                    if (name.equalsIgnoreCase("CROPID"))
                    	crop.setCropid(cur.getString(i));
                    else if (name.equalsIgnoreCase("CROPNAME"))
                    	crop.setCropname(cur.getString(i));
                    else if (name.equalsIgnoreCase("RECOMMENDEDVARIETIES"))
                    	crop.setRecommendedvarieties(cur.getString(i));
                    else if (name.equalsIgnoreCase("CROPSTATE"))
                    	crop.setCropstate(cur.getString(i));
                    else if (name.equalsIgnoreCase("LOWERLIMIT"))
                    	crop.setLowerlimit(cur.getInt(i));
                    else if (name.equalsIgnoreCase("TOPLIMIT"))
                    	crop.setToplimit(cur.getInt(i));	
                    else if (name.equalsIgnoreCase("DIVIDE"))
                    	crop.setDivide(cur.getInt(i));
                    else if (name.equalsIgnoreCase("OTHERFERTILIZERS"))
                    	crop.setOtherfertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("FERTILIZERS"))
                    	crop.setFertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("TOPDRESSING"))
                    	crop.setTopdressing(cur.getString(i));
                    else if (name.equalsIgnoreCase("MICRO"))
                    	crop.setMicro(cur.getString(i));
                    else if(name.equalsIgnoreCase("GOODSNAME"))
                    	crop.setGoodsname(cur.getString(i));
                    else if(name.equalsIgnoreCase("SORTVALUE")){
                    	crop.setSortvalue(cur.getString(i));
                    }else if(name.equalsIgnoreCase("SCHEME")){
                    	crop.setScheme(cur.getInt(i));
                    }
                    //--其他有用到的字段再写代码
				}
				return crop;
	        	
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
    	return null;


    }

}
