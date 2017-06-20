package com.snsoft.ctpf.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.snsoft.ctpf.db.DbManager;
import com.snsoft.ctpf.util.TLog;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 种植作物service方法
 * @author malq  Feb 13, 2012 3:48:09 PM
 *
 */
public class ParamService{

	private SQLiteDatabase database;
	public ParamService(){
		database = DbManager.getInstance().getLocalDatabase();
	}
	/**
	 * 获取配置值
	 * @param paramname
	 * @return
	 */
	
	/**
	 * 查找参数（保存在手机内的）
	 * @param paramname
	 * @return
	 */
	public String getParameter(String paramname) {
		//必须得到本县作物
    	String sql = "select paramvalue from sn_sys_parameters where paramname='"+paramname.replace("'", "''")+"'";
		TLog.i("sql="+sql);
		String[] fields=null;
		Cursor cur=null;
		try {
			SQLiteDatabase database = DbManager.getInstance().getLocalDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
				String val=cur.getString(0);

				return val;
	        }
		}
        catch (Exception ex) {
            //throw ex;
        	ex.printStackTrace();
        }
        finally {
            try {
            	if (cur!=null){
            		cur.close();
            	}
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    	return null;
	}
	
	
	/**
	 * 根据城市名称获得城市编码
	 * @param countyname
	 * @return
	 */
	public String getCountyidByname(String countyname){
		//必须得到本县作物
    	String sql = "select countyid from sn_countyinfo where countyname='"+countyname.replace("'", "''")+"'";
		String[] fields=null;
		Cursor cur=null;
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
				String val=cur.getString(0);

				return val;
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
	
	/**
	 * 根据城市名称 返回城市类型
	 * @param countyname
	 * @return
	 */
	public String getCountyTypeByname(String countyname){
		String sql = "select countytype from sn_countyinfo where countyname='"+countyname.replace("'", "''")+"'";
		String[] fields=null;
		Cursor cur=null;
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
				String val=cur.getString(0);

				return val;
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
	
	/**
	 * 是否县级类型
	 * @param countyname
	 * @return
	 */
	public boolean isCounty(String countyname){
    	//获得当前城市代码
    	String ctype=getCountyTypeByname(countyname);
    	if ("0".equals(ctype)){
    		return true;
    	}else{
    		List<String> list = getCountys();
    		if (list!=null && list.size()>1){
    			//城市类型为1,县城市数量大于1,则是地级市
    			return false;
    		}
    	}
    	return true;
	}
	
	/**
	 * 获取县信息
	 * @return
	 */
	public List<String> getCountys(){
		List<String> list = new ArrayList<String>();
		String sql = "select countyname from sn_countyinfo order by countyname asc";
		String[] fields=null;
		Cursor cur=null;
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			while(cur.moveToNext()){
				list.add(cur.getString(0));
	        }

			//return list;
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
		final Collator collator = Collator.getInstance(java.util.Locale.CHINA);
		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return collator.compare(o1, o2);
			}
		});
		
		return list;
	}
	
	/**
	 * 根据城市编号返回城市名称 
	 * @param countyid
	 * @return
	 */
	public String getCountyNameByid(String countyid){

		String sql = "select countyname from sn_countyinfo where countyid='"+countyid.replace("'", "''")+"'";
		String[] fields=null;
		Cursor cur=null;
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
				String val=cur.getString(0);
				//database.close();
				return val;
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
	
	/**
	 * 获取县的名称
	 * @param paramid
	 * @ return
	 */
	
	public String getShujuku(String paramid){
		//String sql = "select paramvalue from SN_SYS_PARAMETERS where paramid=9739107042924A8F9CB21083E59B2CA7";
		try{
			//SQLiteDatabase db = DBManager.getInstance().getDatabase();
			if(database.isOpen()){
				String[] columns = {"paramvalue"};	// 需要的列
				String selection = "paramid = ?";	// 选择条件, 给null查询所有
				String[] selectionArgs = {paramid + ""};	// 选择条件的参数, 会把选择条件中的? 替换成数据中的值
				String groupBy = null;	// 分组语句  group by name
				String having = null;	// 过滤语句
				String orderBy = null;	// 排序
				
				Cursor cursor = database.query("SN_SYS_PARAMETERS", columns, selection, selectionArgs, groupBy, having, orderBy);
				
				if(cursor != null && cursor.moveToFirst()) {		// cursor对象不为null, 并且可以移动到第一行
					String paramvalue=cursor.getString(0);
					

					return paramvalue;
				}

			}
		} catch (Exception ex) {
	        //ex.printStackTrace();
			Log.i("","________ParamService="+ex);
	    } finally {
			if (database != null && database.isOpen()) {
				DbManager.getInstance().closeDatabase();
			}
		}
		
		return "";
	}
	    
	
	/**
	 * 获取县的名称
	 * @return 县区列表
	 */
	public List<String> getCountyname(){
		try{
			//SQLiteDatabase db = DBManager.getInstance().getDatabase();
			if(database.isOpen()){
				String[] columns = {"countyname"};	// 需要的列
				String selection = "isshow=1";	// 选择条件, 给null查询所有
				String[] selectionArgs = null;	// 选择条件的参数, 会把选择条件中的? 替换成数据中的值
				String groupBy = null;	// 分组语句  group by name
				String having = null;	// 过滤语句
				String orderBy = null;	// 排序
				
				Cursor cursor = database.query("sn_countyinfo", columns, selection, selectionArgs, groupBy, having, orderBy);
			    String countyname;
			    if(cursor != null && cursor.getCount() > 0) {
					List<String> list = new ArrayList<String>();
					while(cursor.moveToNext()) {	// 向下移一位, 知道最后一位, 不可以往下移动了, 停止.
						countyname = cursor.getString(0);
						list.add(countyname);
					}
					return list;
				}
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			Log.i("","________ParamService="+ex);
	    } finally {
			if (database != null && database.isOpen()) {
				DbManager.getInstance().closeDatabase();
			}
		}
		return null;
	}
}
