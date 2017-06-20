package com.snsoft.ctpf.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.DbManager;

import java.util.ArrayList;
import java.util.List;


public class SoilInfoService{

	private SQLiteDatabase database;
	public SoilInfoService(){
		database = DbManager.getInstance().getLocalDatabase();
	}

	/**
	 * 根据经纬度坐标点，去测土土样表中查询几个临近点，并进行均衡计算，
	 * 得出一个土壤养分含量的数据，进行后续决策计算。
	 * 主要的计算思路是根据经续度坐标，去土样表中查询几个临近点的N,P,K数据
	 * 然后根据这几个点的N,P,K数据以及与该点的距离分配权重进行计算。
	 * 
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	public SoilInfo getSoilinfoByPosition(double lng,double lat){
		SoilInfo si=new SoilInfo();
		
		//首先根据经纬度坐标，查询获得五个最近的临近点N,P,K
		List list=getSoilInfoListByPosition(lng,lat,5);
		//获得了所有数据后进行比较计算
		
		if (list==null || list.size()<1){
			//说明未找到相关的邻近的土样点数据，可能没有初始化测土土样数据
			return null;
		}
		
		double distance=0;		//总距离
		
		//本循环获得加权平均算法的总距离
		for (int i=0;i<list.size();i++){
			SoilInfo tmp=new SoilInfo();
			tmp=(SoilInfo)list.get(i);
			distance=distance+tmp.getSi();		//将所有点的距离相加，以用加权法计算各养分含量
			if (i==0){
				//将第一个的土质和地貌进行记录
				si.setLandform(tmp.getLandform());	//地貌
				si.setSoiltype(tmp.getSoiltype());	//土质
				si.setCounty(tmp.getCounty());		//资料表县
				si.setCountyname(tmp.getCountyname());	//导入县名
			}
		}
		
		//内循环根据总距离进行加权平均算法计算，获得所有养分计算值
		//加权平均算法即  X1*(权重1/总权重)+X2*(权重2/总权重)+Xn*(权重n/总权重)
		
		for (int i=0;i<list.size();i++){
			SoilInfo tmp=new SoilInfo();
			tmp=(SoilInfo)list.get(i);
			
			//计算N,P,K和Organic值
			si.setN(si.getN()+tmp.getN()*(tmp.getSi()/distance));
			si.setP(si.getP()+tmp.getP()*(tmp.getSi()/distance));
			si.setK(si.getK()+tmp.getK()*(tmp.getSi()/distance));
			si.setOrganic(si.getOrganic()+tmp.getOrganic()*(tmp.getSi()/distance));
		}
		
		return si;
	}
    
	

	
	/**
	 * 根据经纬度坐标，返回临近该坐标的若干测土土样点列表
	 * 本方法中的数据元素仅返回了有机质、氮、磷、钾、地貌和土质的数据，
	 * 同时返回了与坐标点的距离保存在了Si这个特殊的属性中
	 * 
	 * @param lng 经度
	 * @param lat 纬度
	 * @param number 返回土样点数量
	 * 
	 * @return
	 */
    @SuppressWarnings("unchecked")
	private List getSoilInfoListByPosition(double lng,double lat,long number){
    	String sql="";
    	//下面的语句主要构造了两点距离的平方和
        sql=" ((latitude-"+lat+")*(latitude-"+lat+")";
        sql=sql+"+(longitude-"+lng+")*(longitude-"+lng+")) as T,";
        //查询关键属性信息，并排序
        sql=sql + "Organic,n,p,k,Landform,Soiltype,County from sn_soilsinfo order by T ";
        sql="SELECT " +sql+" limit "+number;
    	return getSoilInfoListBySql(sql,null);
    }
    
    
    
    /**
     * 根据sql语句返回土壤信息列表
     * @param sql
     * @param fields
     * @return
     */
    private List getSoilInfoListBySql(String sql, String[] fields) {
    	Cursor cur=null;
		ArrayList<SoilInfo> list=new ArrayList<SoilInfo>();
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			while(cur.moveToNext()){
				SoilInfo si=new SoilInfo();
            	for (int i = 0; i < cur.getColumnCount(); i++) {
                    String name = cur.getColumnName(i).trim();
                        if (name.equalsIgnoreCase("LANDFORM"))
                        	si.setLandform(cur.getString(i));
                        else if (name.equalsIgnoreCase("SOILTYPE"))
                        	si.setSoiltype(cur.getString(i));
						else if (name.equalsIgnoreCase("COUNTY"))
                        	si.setCounty(cur.getString(i));
                        else if (name.equalsIgnoreCase("ORGANIC"))
                        	si.setOrganic(cur.getDouble(i));
                        else if (name.equalsIgnoreCase("T"))
                        	//注意这里，将距离值放在了Si这个元素里面，没有专门增加新的属性
                        	si.setSi(Math.sqrt(cur.getDouble(i)));
                        else if (name.equalsIgnoreCase("N"))
                        	si.setN(cur.getDouble(i));
                        else if (name.equalsIgnoreCase("P"))
                        	si.setP(cur.getDouble(i));
                        else if (name.equalsIgnoreCase("K"))
                        	si.setK(cur.getDouble(i));
                        //--其他有用到的字段再写代码--
                }
                list.add(si);
	        	
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
	 * 根据农户名称查询农户列表
	 * 
	 * @param   farmer 农户名称
	 * @param number 返回农户的数量
	 * 
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public List getSoilInfoListByFarmer(String farmer,long number){
    	return getSoilInfoListByFarmer(farmer,number,"");
    }
    
    /**
	 * 根据农户名称查询农户列表
	 * 
	 * @param   farmer 农户名称
	 * @param number 返回农户的数量
	 * 
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public List getSoilInfoListByFarmer(String farmer,long number,String cid){
    	Cursor cur=null;
		ArrayList<SoilInfo> list=new ArrayList<SoilInfo>();
		String sql="";
		sql=" * from sn_soilsinfo where farmer like ? ";
        if (cid!=null && !"".equals(cid)){
        	//城市代码不为空时增加县级查询条件
        	sql=sql+" and countyid=? ";
        }
        //下面的语句主要构造了两点距离的平方和
        sql=sql+" order by samplingdate desc,town,village,position,farmer";
        sql="SELECT " +sql+" limit "+number;
        String[] fields=new String[1];
		fields[0]="%"+farmer+"%";
		if(cid!=null&&!"".equals(cid)){
			//城市代码不为空时增加县级查询条件
			fields[1]=cid;
		}
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			while(cur.moveToNext()){
				SoilInfo si=new SoilInfo();
            	for (int i = 0; i < cur.getColumnCount(); i++) {
                    String name = cur.getColumnName(i).trim();
                        if (name.equalsIgnoreCase("organic"))
                        	si.setOrganic(cur.getDouble(i));
                        else if (name.equalsIgnoreCase("n"))
                        	si.setN(cur.getDouble(i));
                        else if (name.equalsIgnoreCase("p"))
                        	si.setP(cur.getDouble(i));
                        else if (name.equalsIgnoreCase("k"))
                        	//注意这里，将距离值放在了Si这个元素里面，没有专门增加新的属性
                        	si.setK(cur.getDouble(i));
                        else if (name.equalsIgnoreCase("landform"))
                        	si.setLandform(cur.getString(i));
                        else if (name.equalsIgnoreCase("soiltype"))
                        	si.setSoiltype(cur.getString(i));
                        else if (name.equalsIgnoreCase("code"))
                        	si.setCode(cur.getString(i));
                        else if (name.equalsIgnoreCase("town"))
                        	si.setTown(cur.getString(i));
                        else if (name.equalsIgnoreCase("county"))
                        	si.setCounty(cur.getString(i));
                        else if (name.equalsIgnoreCase("village"))
                        	si.setVillage(cur.getString(i));
                        else if (name.equalsIgnoreCase("position"))
                        	si.setPosition(cur.getString(i));
                        else if (name.equalsIgnoreCase("farmer"))
                        	si.setFarmer(cur.getString(i));
//                        else if (name.equalsIgnoreCase("samplingdate"))
//                        	si.setSamplingdate(new Date(cur.getString(i)));
                        //--其他有用到的字段再写代码--
                        
                }
                list.add(si);
	        	
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
     * 根据市，查询市下的所有县
     * @param city
     * @return
     */

	public List<String> getCounty(String city){
		ArrayList<String> list=new ArrayList();
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select distinct county from sn_soilsinfo where city=? order by county asc";
		String[] fields=new String[1];
		fields[0]=city;
		Cursor cur =  database.rawQuery(sql, fields);
		try {
			String val = null;
			while(cur.moveToNext()){
				val =cur.getString(0).trim();
				list.add(val);
			}


			return list;
		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
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
     * 根据县，查询县下的所有乡镇
     * @param county
     * @return
     */

	public List<String> getTowns(String county){
		ArrayList<String> list=new ArrayList();
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select distinct town from sn_soilsinfo where county=? order by town asc";
		String[] fields=new String[1];
		fields[0]=county;
		Cursor cur =  database.rawQuery(sql, fields);
		try {
			String val = null;
			while(cur.moveToNext()){
				val =cur.getString(0).trim();
				list.add(val);
			}

			return list;
		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
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
     * 根据不完整的地块编号信息返回完整的地块编号
     * @param code
     * @return
     */

	public List<String> getDKList(String code){
		ArrayList<String> list=new ArrayList();
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select code from sn_soilsinfo where code like ?";
		String[] fields=new String[1];
		fields[0]="%"+code+"%";
		Cursor cur =  database.rawQuery(sql, fields);
		try {
			String val = null;
			while(cur.moveToNext()){
				val =cur.getString(0).trim();
				list.add(val);
			}

			return list;
		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
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
	 * 获取村信息
	 * @return
	 */
	public List<String> getVillages(String county,String town){
		ArrayList<String> list=new ArrayList();
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select distinct village from sn_soilsinfo where county=? and town=? order by village asc";
		String[] fields=new String[2];
		fields[0]=county;
		fields[1]=town;
		Cursor cur =  database.rawQuery(sql, fields);
		try {
			String val = null;
			while(cur.moveToNext()){
				val =cur.getString(0).trim();
				list.add(val);
			}

			return list;
		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
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
	 * 获取方位信息
	 * @return
	 */
	public List<String> getPositions(String county,String town,String village){
		ArrayList<String> list=new ArrayList();
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select distinct position from sn_soilsinfo where county=? and town=? and village=? order by position asc";
		String[] fields=new String[3];
		fields[0]=county;
		fields[1]=town;
		fields[2]=village;
		Cursor cur =  database.rawQuery(sql, fields);
		try {
			String val = null;
			while(cur.moveToNext()){
				val =cur.getString(0).trim();
				list.add(val);
			}

			return list;
		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
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
	 * 获取户主信息
	 * @return
	 */
	public List<String> getFarmers(String county,String town,String village,String position){
		ArrayList<String> list=new ArrayList();
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select distinct farmer from sn_soilsinfo where county=? and town=? and village=? and position=? order by farmer asc";
		String[] fields=new String[4];
		fields[0]=county;
		fields[1]=town;
		fields[2]=village;
		fields[3]=position;
		Cursor cur =  database.rawQuery(sql, fields);
		try {
			String val = null;
			while(cur.moveToNext()){
				val =cur.getString(0).trim();
				list.add(val);
			}

			return list;
		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
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
	 * 获取元素信息
	 * @return
	 */
	public SoilInfo getYuansu(String county,String town,String village,String position,String farmer){
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select * from sn_soilsinfo where county=? and town=? and village=? and position=? and farmer=? order by samplingdate desc";
		String[] fields=new String[5];
		fields[0]=county;
		fields[1]=town;
		fields[2]=village;
		fields[3]=position;
		fields[4]=farmer;
		Cursor cur =  database.rawQuery(sql, fields);
		SoilInfo si = new SoilInfo();
		try {
			while(cur.moveToNext()){
	        	for (int i = 0; i < cur.getColumnCount(); i++) {
	                String name = cur.getColumnName(i).trim();
	                    if (name.equalsIgnoreCase("organic"))
	                    	si.setOrganic(cur.getDouble(i));
	                    else if (name.equalsIgnoreCase("n"))
	                    	si.setN(cur.getDouble(i));
	                    else if (name.equalsIgnoreCase("p"))
	                    	si.setP(cur.getDouble(i));
	                    else if (name.equalsIgnoreCase("k"))
	                    	//注意这里，将距离值放在了Si这个元素里面，没有专门增加新的属性
	                    	si.setK(cur.getDouble(i));
	                    else if (name.equalsIgnoreCase("landform"))
	                    	si.setLandform(cur.getString(i));
	                    else if (name.equalsIgnoreCase("soiltype"))
	                    	si.setSoiltype(cur.getString(i));
	                    else if (name.equalsIgnoreCase("code"))
	                    	si.setCode(cur.getString(i));
	                    else if (name.equalsIgnoreCase("town"))
	                    	si.setTown(cur.getString(i));
	                    else if (name.equalsIgnoreCase("village"))
	                    	si.setVillage(cur.getString(i));
	                    else if (name.equalsIgnoreCase("position"))
	                    	si.setPosition(cur.getString(i));
	                    else if (name.equalsIgnoreCase("farmer"))
	                    	si.setFarmer(cur.getString(i));
	                    //--其他有用到的字段再写代码--
	            }
	        }

		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
                if (cur != null)
                	cur.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
		return si;
	}
	
	
	
	/**
	 * 根据地块编号信息返回地块对象
	 * 
	 * @param dkbh 地块编号
	 * @return
	 */
	public SoilInfo getSoilinfoByDkbh(String dkbh){
		//SQLiteDatabase database = DBManager.getInstance().getDatabase();
		String sql = "select * from sn_soilsinfo where code=?";
		String[] fields=new String[1];
		fields[0]=dkbh;
		Cursor cur =  database.rawQuery(sql, fields);
		SoilInfo so = null;
		try {
			if(cur.moveToNext()){
				so = new SoilInfo();
				so.setOrganic(cur.getDouble(cur.getColumnIndex("organic")));
				so.setN(cur.getDouble(cur.getColumnIndex("N")));
				so.setP(cur.getDouble(cur.getColumnIndex("P")));
				so.setK(cur.getDouble(cur.getColumnIndex("K")));
				so.setLandform(cur.getString(cur.getColumnIndex("landform")));
				so.setSoiltype(cur.getString(cur.getColumnIndex("soiltype")));
				so.setCode(cur.getString(cur.getColumnIndex("code")));
				so.setTown(cur.getString(cur.getColumnIndex("town")));
				so.setVillage(cur.getString(cur.getColumnIndex("village")));
				so.setPosition(cur.getString(cur.getColumnIndex("position")));
				so.setFarmer(cur.getString(cur.getColumnIndex("farmer")));
				so.setCounty(cur.getString(cur.getColumnIndex("county")));
				so.setCountyname(cur.getString(cur.getColumnIndex("countyname")));
			}

		} catch (Exception e) {
			//throw e;
			e.printStackTrace();
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
                if (cur != null)
                	cur.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
		return so;
	}
    
	/**
	 * 根据经纬度坐标点，去测土土样表中查询几个临近点，并进行均衡计算，
	 * 得出一个土壤养分含量的数据，进行后续决策计算。
	 * 主要的计算思路是根据经续度坐标，去土样表中查询几个临近点的N,P,K数据
	 * 然后根据这几个点的N,P,K数据以及与该点的距离分配权重进行计算。
	 * 
	 * @ param
	 * @ param
	 * @ return
	 */
	public SoilInfo getSoilinfoByXzqh(String county,String town,String village,String position){
		SoilInfo si=new SoilInfo();
		
		//首先行政区划，查询获得五个最近的临近点N,P,K
		List list=getSoilInfoListByXzqh(county,town,village,position,15);
		if (list==null || list.size()<1){
			//说明未找到相关的邻近的土样点数据，可能没有初始化测土土样数据
			return null;
		}
		
		double distance=0;		//总距离
		
		//本循环获得加权平均算法的总距离
		for (int i=0;i<list.size();i++){
			SoilInfo tmp=new SoilInfo();
			tmp=(SoilInfo)list.get(i);
			distance=distance+1;		//
			if (i==0){
				//将第一个的土质和地貌进行记录
				si.setLandform(tmp.getLandform());	//地貌
				si.setSoiltype(tmp.getSoiltype());	//土质
			}
		}
		
		//内循环根据总距离进行加权平均算法计算，获得所有养分计算值
		//加权平均算法即  X1*(权重1/总权重)+X2*(权重2/总权重)+Xn*(权重n/总权重)
		
		for (int i=0;i<list.size();i++){
			SoilInfo tmp=new SoilInfo();
			tmp=(SoilInfo)list.get(i);
			si.setTown(town);
			si.setVillage(village);
			si.setPosition(position);
			//计算N,P,K和Organic值
			si.setN(si.getN()+tmp.getN()/distance);
			si.setP(si.getP()+tmp.getP()/distance);
			si.setK(si.getK()+tmp.getK()/distance);
			si.setOrganic(si.getOrganic()+tmp.getOrganic()/distance);
		}
		return si;
	}
	
	
	/**
	 * 县、乡、村和方位查询几个地块列表
	 * 
	 * @param number 返回土样点数量
	 * 
	 * @return
	 */

	private List getSoilInfoListByXzqh(String county,String town,String village,String position,long number){
        String sql="";
        Cursor cur = null;
        //查询关键属性信息，并排序
        sql = " * from sn_soilsinfo where county=? and town=? and village=? and position=? order by samplingdate desc";
    	//sqlite的前几条
    	sql="SELECT " +sql+" limit "+number;
		
        ArrayList list=new ArrayList();
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			String[] fields=new String[4];
			fields[0]=county;
			fields[1]=town;
			fields[2]=village;
			fields[3]=position;
			cur =  database.rawQuery(sql, fields);
			
			while(cur.moveToNext()){
				SoilInfo si=new SoilInfo();
	        	for (int i = 0; i < cur.getColumnCount(); i++) {
	                String name = cur.getColumnName(i).trim();
	                if (name.equalsIgnoreCase("LANDFORM"))
                    	si.setLandform(cur.getString(i));
                    else if (name.equalsIgnoreCase("SOILTYPE"))
                    	si.setSoiltype(cur.getString(i));
                    else if (name.equalsIgnoreCase("COUNTY"))
                    	si.setCounty(cur.getString(i));
                    else if (name.equalsIgnoreCase("ORGANIC"))
                    	si.setOrganic(cur.getDouble(i));
                    else if (name.equalsIgnoreCase("N"))
                    	si.setN(cur.getDouble(i));
                    else if (name.equalsIgnoreCase("P"))
                    	si.setP(cur.getDouble(i));
                    else if (name.equalsIgnoreCase("K"))
                    	si.setK(cur.getDouble(i));
	                    //--其他有用到的字段再写代码--
	            }
	        	list.add(si);
	        	
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
	 * 县、乡、村和方位查询几个地块列表
	 * 
	 * @ param
	 *
	 * @ return
	 */

	public SoilInfo getAvgSoilInfoByXzqh(String county,String town,String village,String position){
        String sql="";
        Cursor cur = null;
        //查询关键属性信息，并排序
        sql = "select avg(n) as n, avg(p) as p,avg(k) as k,avg(organic) as organic";
        sql=sql+" from sn_soilsinfo where county=? and town=? and village=? and position=? ";
        SoilInfo si = new SoilInfo();      
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			String[] fields=new String[4];
			fields[0]=county;
			fields[1]=town;
			fields[2]=village;
			fields[3]=position;
			cur =  database.rawQuery(sql, fields);
			
			while(cur.moveToNext()){
	        	for (int i = 0; i < cur.getColumnCount(); i++) {
	                String name = cur.getColumnName(i).trim();
	                if (name.equalsIgnoreCase("LANDFORM"))
                    	si.setLandform(cur.getString(i));
                    else if (name.equalsIgnoreCase("SOILTYPE"))
                    	si.setSoiltype(cur.getString(i));
                    else if (name.equalsIgnoreCase("ORGANIC"))
                    	si.setOrganic(cur.getDouble(i));
                    else if (name.equalsIgnoreCase("N"))
                    	si.setN(cur.getDouble(i));
                    else if (name.equalsIgnoreCase("P"))
                    	si.setP(cur.getDouble(i));
                    else if (name.equalsIgnoreCase("K"))
                    	si.setK(cur.getDouble(i));
	                    //--其他有用到的字段再写代码--
	            }
	        }
			si.setTown(town);
			si.setVillage(village);
			si.setPosition(position);

		} catch (Exception e) {
			//throw e;
		}
		finally {
            try {
				if (database != null && database.isOpen()) {
					DbManager.getInstance().closeDatabase();
				}
                if (cur != null)
                	cur.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
		return si;
    }
	
}
