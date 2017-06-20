package com.snsoft.ctpf.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snsoft.ctpf.beans.FertilizerInfo;
import com.snsoft.ctpf.db.DbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 肥料信息service方法
 * @author malq  Feb 13, 2012 3:48:09 PM
 *
 */
public class FertilizerService{

	private SQLiteDatabase database;
	public FertilizerService(){
		database = DbManager.getInstance().getLocalDatabase();
	}

	/**
	 * 获得肥料列表
	 * @param countyid　县别代码
	 * @param Fertilizertype　肥料类型0 不限　1底肥　2追肥
	 * @param Dedicated　是否专用肥　0 非专用肥　1 专用肥
	 * @return
	 */
    public List getFertilizerInfoList(String countyid,String Fertilizertype,String Dedicated){
    	
    	String sql="";
        if ("0".equals(Fertilizertype)){
        	//所有肥料
        	sql="SELECT * FROM sn_fertilizer WHERE COUNTYID=? and DEDICATED="+Dedicated+
        	" order by RECOMMEND desc";
        }else{
        	//0代码不限的肥料，加上某类底肥或者不限的肥料
        	sql="SELECT * FROM sn_fertilizer WHERE COUNTYID=? and DEDICATED="+Dedicated +
        			" and (Fertilizertype="+Fertilizertype+" or Fertilizertype=0) " +
        					" order by RECOMMEND desc";
        }
		String[] fields=new String[1];
		fields[0]=countyid;        
        return getFertilizerInfoListBySql(sql,fields);
    }
	
	/**
	 * 获得肥料列表
	 * @param countyid　县别代码
	 * @param Fertilizertype　肥料类型0 不限　1底肥　2追肥
	 * @param Dedicated　是否专用肥　0 非专用肥　1 专用肥
	 * @param cropid　专用作物id
	 * @return
	 */
    public List getFertilizerInfoList(String countyid,String Fertilizertype,String Dedicated,String cropid){
    	
    	String sql="";
    	sql="SELECT * FROM sn_fertilizer WHERE COUNTYID=? and DEDICATED="+Dedicated;
        if("1".equals(Dedicated) && cropid!=null){
    		//专用肥时，增加专用作物筛选
    		sql=sql+" and cropid='"+cropid+"'";
    	}
        
        //下面要对作物对应的禁用肥料进行限制
        String notinsql="select fertilizerid from sn_crop_fertilizer_disabled where (Fertilizertype='"
        	+Fertilizertype+"' or Fertilizertype=0) and cropid='"+cropid+"'";
        sql=sql+" and fertilizerid not in("+notinsql+")";
        
        if ("0".equals(Fertilizertype)){
        	sql=sql+" order by RECOMMEND desc";
        }else{
        	sql=sql+" and (Fertilizertype="+Fertilizertype+" or Fertilizertype=0) " +
			" order by RECOMMEND desc";
        }
		String[] fields=new String[1];
		fields[0]=countyid;
		return getFertilizerInfoListBySql(sql,fields);
		
    }
    
	
	/**
	 * 获得肥料列表,去总市级查找同名作物的公共配方肥
	 * @param Fertilizertype　肥料类型0 不限　1底肥　2追肥
	 * @param Dedicated　是否专用肥　0 非专用肥　1 专用肥
	 * @param cropname　专用作物id
	 * @return
	 */
    public List  getPublicFertilizers(String Fertilizertype,String Dedicated,String cropname){
    	
    	//查找得到市级的countyid以及市级同名的作物id
    	String countyid;
    	//这里获得主要城市代码
    	countyid=getMainCountyid();
    	String cropid;
    	//这里获得主要城市同名作物代码
    	cropid=getCropidByName(cropname,countyid);
    	//查找对应的肥料
    	return getFertilizerInfoList(countyid,Fertilizertype,Dedicated,cropid);
    	
    }
    
    
	/**
	 * 获得肥料列表,与上个函数不同的地址就是一个 条件中固化了县名称和作物名称
	 * 这里这种写法不是非常友好的写法，将来需要重写
	 * @param Fertilizertype　肥料类型0 不限　1底肥　2追肥
	 * @param Dedicated　是否专用肥　0 非专用肥　1 专用肥
	 * @return
	 */
    public List getFertilizerInfoList(String Fertilizertype,String Dedicated){
    	
    	String sql="";
    	if ("0".equals(Fertilizertype)){
        	//所有肥料
        	sql="SELECT * FROM sn_fertilizer WHERE COUNTYNAME='邯郸市' and DEDICATED="+Dedicated;
        	if("1".equals(Dedicated) ){
        		//专用肥时，增加专用作物筛选
        		sql=sql+" and cropname='玉米'";
        	}
        	sql=sql+" order by RECOMMEND desc";
        }else{
        	//0代码不限的肥料，加上某类底肥或者不限的肥料
        	sql="SELECT * FROM sn_fertilizer WHERE COUNTYNAME='邯郸市' and DEDICATED="+Dedicated;
        	if("1".equals(Dedicated)){
        		//专用肥时，增加专用作物筛选
        		sql=sql+" and cropname='玉米'";
        	}
        	sql=sql+" and (Fertilizertype="+Fertilizertype+" or Fertilizertype=0) " +
        					" order by RECOMMEND desc";
        }
    	return getFertilizerInfoListBySql(sql,null);
    }

    /**
     * 根据肥料id数组返回肥料信息列表
     * @param fertilizers
     * @return
     */
    public List getFertilizerInfoList(String[] fertilizers){
    	ArrayList list=new ArrayList();
    	for (int i=0;i<fertilizers.length;i++){
    		if (fertilizers[i]==null || "".equals(fertilizers[i])){
    			
    		}else{
	    		FertilizerInfo fertilizer=new FertilizerInfo();
	        	fertilizer=this.getFertilizerInfo(fertilizers[i]);
	        	if (fertilizer!=null){
	        		list.add(fertilizer);
	        	}
    		}
    	}
    	return list;
    }
    /**
     * 返回根据作物ID返回作物信息
     * @return
     * @throws
     */
    public FertilizerInfo getFertilizerInfo(String fertilizerid){
    	
    	String sql="";
    	sql="SELECT * FROM sn_fertilizer WHERE FERTILIZERID=? ";
		String[] fields=new String[1];
		fields[0]=fertilizerid;
		Cursor cur=null;
		try {
			SQLiteDatabase database = DbManager.getInstance().getLocalDatabase();//DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
				FertilizerInfo fertilizer=new FertilizerInfo();
				for (int i = 0; i <cur.getColumnCount(); i++) {
					String name =cur.getColumnName(i).trim();
					if (name.equalsIgnoreCase("FERTILIZERID"))
                    	fertilizer.setFertilizerid(cur.getString(i));
                    else if (name.equalsIgnoreCase("FERTILIZERNAME"))
                    	fertilizer.setFertilizername(cur.getString(i));
                    else if (name.equalsIgnoreCase("SHORTNAME"))
                    	fertilizer.setShortname(cur.getString(i));
                    else if (name.equalsIgnoreCase("COMPANY"))
                    	fertilizer.setCompany(cur.getString(i));
                    else if (name.equalsIgnoreCase("N"))
                    	fertilizer.setN(cur.getInt(i));
                    else if (name.equalsIgnoreCase("P"))
                    	fertilizer.setP(cur.getInt(i));	
                    else if (name.equalsIgnoreCase("K"))
                    	fertilizer.setK(cur.getInt(i));
                    else if (name.equalsIgnoreCase("USERATION"))
                    	fertilizer.setUseration(cur.getInt(i));
                    else if (name.equalsIgnoreCase("USERATIOP"))
                    	fertilizer.setUseratiop(cur.getInt(i));
                    else if (name.equalsIgnoreCase("USERATIOK"))
                    	fertilizer.setUseratiok(cur.getInt(i));
                    else if (name.equalsIgnoreCase("RECOMMEND"))
                    	fertilizer.setRecommend(cur.getInt(i));
                    else if (name.equalsIgnoreCase("FERTILIZERTYPE"))
                    	fertilizer.setFertilizertype(cur.getInt(i));
                    else if (name.equalsIgnoreCase("DEDICATED"))
                    	fertilizer.setDedicated(cur.getInt(i));
                    else if (name.equalsIgnoreCase("COMPOUND"))
                    	fertilizer.setCompound(cur.getInt(i));
                    else if (name.equalsIgnoreCase("FERTILIZERS"))
                    	fertilizer.setFertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("TOPDRESSING"))
                    	fertilizer.setTopdressing(cur.getString(i));
                    //--其他有用到的字段再写代码
				}
				return fertilizer;
	        	
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
     * 获得主城城市代码
     * @return
     */
	public String getMainCountyid(){
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select countyid from sn_countyinfo where countytype='1'";
		Cursor cur =  database.rawQuery(sql, null);
		try {
			String val = null;
	        if(cur.getCount()>0){
	        	if(cur.moveToNext()){
	        		val = cur.getString(cur.getColumnIndex("countyid"));
	        	}
	        }
	        cur.close();

			return val;
		} catch (Exception e) {
			//throw e;
		} finally {
			if (database != null && database.isOpen()) {
				DbManager.getInstance().closeDatabase();
			}
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
	
    /**
     * 返回根据作物ID返回作物信息
     * @return
     * @throws
     */
    public String getCropidByName(String cropname,String countyid){
    	
    	//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql="SELECT CROPID FROM SN_CROP WHERE CROPNAME=? and countyid=?";
		String[] fields=new String[2];
		fields[0]=cropname;
		fields[1]=countyid;
		Cursor cur =  database.rawQuery(sql, null);
		try {
			String val = null;
	        if(cur.getCount()>0){
	        	if(cur.moveToNext()){
	        		val = cur.getString(cur.getColumnIndex("countyid"));
	        	}
	        }
	        cur.close();

			return val;
		} catch (Exception e) {
			//throw e;
		}
		finally {
			if (database != null && database.isOpen()) {
				DbManager.getInstance().closeDatabase();
			}
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
    
    /**
     * 根据sql语句返回肥料信息列表
     * @param sql 语句
     * @param fields ?填充属性值
     * @return
     */
    private List getFertilizerInfoListBySql(String sql,String[] fields){
    	Cursor cur=null;
		List<FertilizerInfo> list=new ArrayList<FertilizerInfo>();
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			while(cur.moveToNext()){
				FertilizerInfo fertilizer=new FertilizerInfo();
				for (int i = 0; i <cur.getColumnCount(); i++) {
					String name =cur.getColumnName(i).trim();
					if (name.equalsIgnoreCase("FERTILIZERID"))
                    	fertilizer.setFertilizerid(cur.getString(i));
                    else if (name.equalsIgnoreCase("FERTILIZERNAME"))
                    	fertilizer.setFertilizername(cur.getString(i));
                    else if (name.equalsIgnoreCase("SHORTNAME"))
                    	fertilizer.setShortname(cur.getString(i));
                    else if (name.equalsIgnoreCase("COMPANY"))
                    	fertilizer.setCompany(cur.getString(i));
                    else if (name.equalsIgnoreCase("N"))
                    	fertilizer.setN(cur.getInt(i));
                    else if (name.equalsIgnoreCase("P"))
                    	fertilizer.setP(cur.getInt(i));	
                    else if (name.equalsIgnoreCase("K"))
                    	fertilizer.setK(cur.getInt(i));
                    else if (name.equalsIgnoreCase("USERATION"))
                    	fertilizer.setUseration(cur.getInt(i));
                    else if (name.equalsIgnoreCase("USERATIOP"))
                    	fertilizer.setUseratiop(cur.getInt(i));
                    else if (name.equalsIgnoreCase("USERATIOK"))
                    	fertilizer.setUseratiok(cur.getInt(i));
                    else if (name.equalsIgnoreCase("RECOMMEND"))
                    	fertilizer.setRecommend(cur.getInt(i));
                    else if (name.equalsIgnoreCase("FERTILIZERTYPE"))
                    	fertilizer.setFertilizertype(cur.getInt(i));
                    else if (name.equalsIgnoreCase("DEDICATED"))
                    	fertilizer.setDedicated(cur.getInt(i));
                    else if (name.equalsIgnoreCase("COMPOUND"))
                    	fertilizer.setCompound(cur.getInt(i));
                    else if (name.equalsIgnoreCase("FERTILIZERS"))
                    	fertilizer.setFertilizers(cur.getString(i));
                    else if (name.equalsIgnoreCase("TOPDRESSING"))
                    	fertilizer.setTopdressing(cur.getString(i));
                    //--其他有用到的字段再写代码
				}
				list.add(fertilizer);
	        	
	        }

		}
        catch (Exception ex) {
            //throw ex;
        	ex.printStackTrace();
        }
        finally {
			if (database != null && database.isOpen()) {
				DbManager.getInstance().closeDatabase();
			}
            try {
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

}
