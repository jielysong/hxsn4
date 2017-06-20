package com.snsoft.ctpf.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snsoft.ctpf.beans.CropInfo;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.DbManager;

import java.util.ArrayList;
import java.util.List;

public class TargetYieldService{

	/**
	 * 土壤资料信息
	 */
	private SoilInfo soilinfo;
	
	/**
	 * 作物资料信息
	 */
	private CropInfo cropinfo;


	//private SQLiteDatabase database;
	public TargetYieldService(){

	}
    
	/**
	 * 目标产量构造方法
	 * @param soilinfo
	 * @param cropinfo
	 */
	public TargetYieldService(SoilInfo soilinfo, CropInfo cropinfo){
		this.soilinfo = soilinfo;
		this.cropinfo=cropinfo;
	}
	
	/**
	 * 根据养分含量和目标产量等信息，根据查表法获得需肥量.
	 * 己知参数土壤N、P、K含量   在soilinfo对象中
	 * 已知参数要计算的目标产量X在cropinfo中。
	 * 
	 * 主要计算思路，从施肥体系中找到对应作物，对应养分类型（N/P/K)养分的下限目标产量X
	 * 找到要达到目标产量X的需肥量下限Fn Fp Fk和他们的养分值下限(Yn Yp Yk)
	 * 
	 * 然后查找对应养分类型(N/P/K)的上一级目标产量Xm
	 * 
	 * 找到要达到目标产量Xm的需肥量下限FMn FMp FMk和他们的养分值下限(YMn YMp YMk)
	 * 
	 * 那么实际需肥量为 (a-Fn)/(FMn-Fn)=(N-Yn)/(Ymn-Yn)
	 * 
	 * 
	 * 
	 * 
	 * @param soilinfo
	 * @param cropinfo
	 * @return
	 */
	public double[] getNutrientrequest(SoilInfo soilinfo, CropInfo cropinfo){
		double[] nr=new double[3];
		System.out.println("sssssssssssssssssss1");
		int yield=0;
		//这里要根据输入的目标产量进行决策，得到最适合他的目标产量
		int [] yieldtmp=new int[3];
		yieldtmp[0]=getYield4ByFertilizerValue(cropinfo.getCropid(),"N",soilinfo.getN(),cropinfo.getYield());
		yieldtmp[1]=getYield4ByFertilizerValue(cropinfo.getCropid(),"P",soilinfo.getP(),cropinfo.getYield());
		yieldtmp[2]=getYield4ByFertilizerValue(cropinfo.getCropid(),"K",soilinfo.getK(),cropinfo.getYield());
		
		yield=Math.min(yieldtmp[0],yieldtmp[1]);
    	yield=Math.min(yield,yieldtmp[2]);
    	
    	System.out.println("sssssssssssssssssss2");
		//此下三行代码能够以表格方式查询对应的目标产量所需要的养分含量了。
		nr[0]=getFertilizerValue(cropinfo.getCropid(),"N",soilinfo.getN(),yield);
		nr[1]=getFertilizerValue(cropinfo.getCropid(),"P",soilinfo.getP(),yield);
		nr[2]=getFertilizerValue(cropinfo.getCropid(),"K",soilinfo.getK(),yield);
		//以上三行代码能够以表格方式实现直接查找，但下面的代码使精确提高到了任意段可查找。
		System.out.println("nr[0]:"+nr[0]+" nr[1]:"+nr[1]+" nr[2]:"+nr[2]);
		System.out.println("sssssssssssssssssss3");
		double r1[]=getNutrientrequestByYield(soilinfo,cropinfo,yield);
		
		System.out.println("r1[0]:"+r1[0]+" r1[1]:"+r1[1]+" r1[2]:"+r1[2]);

		int yield2=0;
		//这里要根据输入的目标产量进行决策，得到最适合他的目标产量
		yieldtmp=new int[3];
		yieldtmp[0]=getHighYield4ByFertilizerValue(cropinfo.getCropid(),"N",soilinfo.getN(),cropinfo.getYield());
		yieldtmp[1]=getHighYield4ByFertilizerValue(cropinfo.getCropid(),"P",soilinfo.getP(),cropinfo.getYield());
		yieldtmp[2]=getHighYield4ByFertilizerValue(cropinfo.getCropid(),"K",soilinfo.getK(),cropinfo.getYield());
		
		System.out.println("yieldtmp[0]:"+yieldtmp[0]+" yieldtmp[1]:"+yieldtmp[1]+" yieldtmp[2]:"+yieldtmp[2]);
		
		System.out.println("sssssssssssssssssss6");
		yield2=Math.min(yieldtmp[0],yieldtmp[1]);
    	yield2=Math.min(yield2,yieldtmp[2]);
    	
    	System.out.println("yieldtmp[0]:"+yieldtmp[0]+" yieldtmp[1]:"+yieldtmp[1]+" yieldtmp[2]:"+yieldtmp[2]);
    	if (yield2<=0){
    		//说明没有向上可达的目标产量，返回下限目标产量对应养分值
    		return r1;
    	}
    	System.out.println("sssssssssssssssssss7");
    	double r2[]=getNutrientrequestByYield(soilinfo,cropinfo,yield2);
    	
    	
    	nr[0]=getValueBy5Numbers(yield,yield2,r1[0],r2[0],cropinfo.getYield());
    	nr[1]=getValueBy5Numbers(yield,yield2,r1[1],r2[1],cropinfo.getYield());
    	nr[2]=getValueBy5Numbers(yield,yield2,r1[2],r2[2],cropinfo.getYield());
		return nr;
	}
	
	private double[] getNutrientrequestByYield(SoilInfo soilinfo,
											   CropInfo cropinfo, int yield) {
		//f1是肥料底限达到目标产量所需要的需肥量
		double[][] f1=new double[3][2];
		f1[0]=getNeedAndNLoor4Min(cropinfo.getCropid(),"N",soilinfo.getN(),yield);
		f1[1]=getNeedAndNLoor4Min(cropinfo.getCropid(),"P",soilinfo.getP(),yield);
		f1[2]=getNeedAndNLoor4Min(cropinfo.getCropid(),"K",soilinfo.getK(),yield);
		
		//f1是肥料值为上限达到目标产量所需要的需肥量
		double[][] f2=new double[3][2];
		f2[0]=getNeedAndNLoor4Max(cropinfo.getCropid(),"N",soilinfo.getN(),yield);
		f2[1]=getNeedAndNLoor4Max(cropinfo.getCropid(),"P",soilinfo.getP(),yield);
		f2[2]=getNeedAndNLoor4Max(cropinfo.getCropid(),"K",soilinfo.getK(),yield);
		
		//System.out.println("N养分下限:"+f1[0][0]+"N养分上限:"+f2[0][0]+"需肥量下限:"+f1[0][1]+"需肥量上限:"+f2[0][1]+"当前氮肥量："+soilinfo.getN());
		//System.out.println("P养分下限:"+f1[1][0]+"P养分上限:"+f2[1][0]+"需肥量下限:"+f1[1][1]+"需肥量上限:"+f2[1][1]+"当前磷肥量："+soilinfo.getP());
		//System.out.println("K养分下限:"+f1[2][0]+"K养分上限:"+f2[2][0]+"需肥量下限:"+f1[2][1]+"需肥量上限:"+f2[2][1]+"当前钾肥量："+soilinfo.getK());
		//计算结果
		double[] r1=new double[3];
		if (f2[0][1]==-1){
			r1[0]=f1[0][1];
		}else{
			/**
			 * 此条数据的计算结果就是计算当前养分在对应养分区间位置对应的需肥量
			 */
			r1[0]=getValueBy5Numbers(f1[0][0],f2[0][0],f1[0][1],f2[0][1],soilinfo.getN());
		}
		if (f2[1][1]==-1){
			r1[1]=f1[1][1];
		}else{
			/**
			 * 此条数据的计算结果就是计算当前养分在对应养分区间位置对应的需肥量
			 */
			r1[1]=getValueBy5Numbers(f1[1][0],f2[1][0],f1[1][1],f2[1][1],soilinfo.getP());
		}
		if (f2[2][1]==-1){
			r1[2]=f1[2][1];
		}else{
			r1[2]=getValueBy5Numbers(f1[2][0],f2[2][0],f1[2][1],f2[2][1],soilinfo.getK());
		}
		return r1;
	}

	/**
	 * 此条数据的计算结果就是计算当前养分在对应养分区间位置对应的需肥量
	 * (e-d):(g-f)=(n-d):(?-f)
	 * ?=(n-d)*(g-f)/(e-d)+f  
	 * @param d 养分下限
	 * @param e 养分上限
	 * @param f 需肥量下限
	 * @param g 需肥量上限
	 * @param n 当前氮肥量
	 * @return 返回确定需肥量
	 */
    private double getValueBy5Numbers(double d, double e, double f, double g,
			double n) {
    	if (d==e || f==g) return g;
    	return (n-d)*(g-f)/(e-d)+f;
	}

	/**
     * 返回目标产量列表
     * @return
     */
    public List getTargetYield(){
    	if (this.soilinfo==null || this.cropinfo==null) return null;
    	
    	double yield[]=getYield(this.getNutrientYield(soilinfo,cropinfo),
    						this.getRegionYield(soilinfo, cropinfo),
    						this.getSoilYield(soilinfo, cropinfo));
    	if (yield[0]==-1){
    		yield[0]=cropinfo.getLowerlimit();
    	}
    	if (yield[1]==-1){
    		yield[1]=cropinfo.getToplimit();
    	}
    	
    	ParamService ps=new ParamService();
    	
    	//获得当前采用的计算算法
    	String method=ps.getParameter("ctpfmethod");
    	if ("table".equals(method)){
    		//经验查表法还要结合经验表来进行目标产量确定。
    		//
    		
    		return getYieldList(soilinfo,cropinfo,yield,cropinfo.getDivide());
    		//上面被注释的这种方法会结合施肥体系决策出表格中的目标产量区间
    		//return getYieldList((int)(yield[0]),(int)(yield[1]),cropinfo.getDivide());
    	}else{
    		//否则根据区间法　x-y 以z为间隔自己组装一个。
    		return getYieldList(cropinfo.getLowerlimit(),cropinfo.getToplimit(),cropinfo.getDivide());
    	}
    }

    /**
     * 根据查表法得到目标产量值
     * @param soil
     * @param crop
     * @param yield
     * @return
     */
    private List getYieldList(SoilInfo soil, CropInfo crop, double[] yield, int divide) {
    	
        List rlist=getYieldList(soil,crop,yield);		//保存最终目标产量列表
	    if (rlist==null || rlist.size()<1){
	    	return getYieldList((int)(yield[0]),(int)(yield[1]),divide);
	    }else{
	        int maxyield;
	        int minyield;
	        int tmpyield; 
	        double tmpd=0;
	        tmpd=(Double)(rlist.get(0));
	        maxyield=(int)(tmpd);
	        minyield=(int)(tmpd);
	        for (int i=1;i<rlist.size();i++){
	        	tmpd=(Double)(rlist.get(i));
	        	tmpyield=(int)(tmpd);
	        	if (tmpyield<minyield) minyield=tmpyield;
	        	if (tmpyield>maxyield) maxyield=tmpyield;
	        }
	        maxyield=(int)(Math.min(maxyield,yield[1]));
	        minyield=(int)(Math.min(minyield,yield[0]));
	        return getYieldList(minyield,maxyield,divide);
        }
        
	}
    

    /**
     * 根据查表法得到目标产量值
     * @param soil
     * @param crop
     * @param yield
     * @return
     */
    private List getYieldList(SoilInfo soil, CropInfo crop, double[] yield) {
    	
        List rlist=new ArrayList();		//保存最终目标产量列表
        List list[]=new ArrayList[3];	//获得氮磷钾对应养分情况的目标产量列表
        double tmpyield[]=new double[3];
        double lastyield=-1;
        list[0]=getFertilizerYieldList(crop.getCropid(),"N",soil.getN(),yield);
        list[1]=getFertilizerYieldList(crop.getCropid(),"P",soil.getP(),yield);
        list[2]=getFertilizerYieldList(crop.getCropid(),"K",soil.getK(),yield);
        //从中找到共有的目标产量列表
        if (list[0].size()<1 || list[1].size()<1 || list[2].size()<1) return null;
        boolean issame;
        for (int i=0;i<list[0].size();i++){
        	tmpyield[0]=(Double)(list[0].get(i));
        	issame=false;
        	for (int j=0;j<list[1].size();j++){
        		tmpyield[1]=(Double)(list[1].get(j));
        		if (tmpyield[0]==tmpyield[1]){
        			issame=true;
        			break;
        		}
        	}
        	if (issame){
	        	for (int k=0;k<list[2].size();k++){
	    			tmpyield[2]=(Double)(list[2].get(k));
	        		if (tmpyield[0]==tmpyield[2]){
	        			rlist.add(tmpyield[0]);
	        			break;
	        		}
	    		}
        	}
        }
    	return rlist;
	}

    /**
     * 根据作物编号　养分类型和养分值，获得当前养分能达到的目标产量列表
     * @param cropid
     * @param Fertilizer
     * @param value
     * @return
     */
    private List getFertilizerYieldList(String cropid,String Fertilizer,double value,double[] yield){
    	String sql="SELECT TARGETYIELD FROM SN_FERTILIZATION_INDEX " +
		"WHERE CROPID=? AND FERTILIZER=? AND LACK<>-1 AND " +
		"NUTRIENTFLOOR<=? ORDER BY TARGETYIELD DESC";

		String[] fields=new String[3];
		fields[0]=cropid;
		fields[1]=Fertilizer;
		fields[2]=String.valueOf(value);
		Cursor cur=null;
		List list=new ArrayList();		//保存最终目标产量列表
		double tmpyield;
        double lastyield=-1;

		SQLiteDatabase database = DbManager.getInstance().getLocalDatabase();
		try {

			cur =  database.rawQuery(sql,fields);
			while(cur.moveToNext()){
				tmpyield=cur.getDouble(cur.getColumnIndex("targetyield"));
            	if (tmpyield!=lastyield){
            		//重复的目标产量不添加
            		if (tmpyield>=yield[0] && tmpyield<=yield[1]){
            			//在合法目标产量区间内再添加。
            			list.add(tmpyield);
            		}
            	}
            	lastyield=tmpyield;
	        }
		}
        catch (Exception ex) {
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
    
    
	private double[] getYield (double[] yield1,double[] yield2,double[] yield3){
    	double yield[]=new double[2];
    	yield[0]=getMin(yield1[0],yield2[0]);
    	yield[0]=getMin(yield[0],yield3[0]);
    	yield[1]=getMin(yield1[1],yield2[1]);
    	yield[1]=getMin(yield[1],yield3[1]);
    	return yield;
    }
    
	private double getMin(double x,double y){
		//当为-1时，说明不参与计算
		if (x==-1) return y;
		if (y==-1) return x;
		return Math.min(x,y);
	}
    
    /**
     * 根据作物信息中的养分含量，获得养分决策相关目标产量
     * 未找到时，直接采用作物相关目标产量
     * @param crop
     * @return
     */
    private double[] getNutrientYield (SoilInfo soilinfo, CropInfo crop){
    	
    	double yield[]=new double[2];
    	yield[0]=-1;
    	//crop.getLowerlimit();
    	//crop.getToplimit();
    	yield[1]=-1;
    	if (soilinfo==null || crop==null) return yield;

    	String sql="SELECT LOWERLIMIT,TOPLIMIT FROM SN_NUTRIENT_TARGET_YIELD WHERE CROPID=? " +
		"AND ORGANIC<=? AND N<=? AND P<=? AND K<=? ORDER BY fertilitylevel desc";
		String[] fields=new String[5];
		fields[0]=crop.getCropid();
		fields[1]=String.valueOf(soilinfo.getOrganic());
		fields[2]=String.valueOf(soilinfo.getN());
		fields[3]=String.valueOf(soilinfo.getP());
		fields[4]=String.valueOf(soilinfo.getK());
		Cursor cur=null;
		SQLiteDatabase database = DbManager.getInstance().getLocalDatabase();
		try {
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
            	yield[0]=cur.getDouble(cur.getColumnIndex("lowerlimit"));
            	yield[1]=cur.getDouble(cur.getColumnIndex("toplimit"));
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
    	return yield;
    }
    
    
    /**
     * 根据土质决策相关目标产量
     * 未找到时，直接采用作物相关目标产量
     * @param crop
     * @return
     */
    private double[] getSoilYield (SoilInfo soilinfo, CropInfo crop){

    	double yield[]=new double[2];
    	yield[0]=-1;
    	yield[1]=-1;
    	if (soilinfo==null || crop==null) return yield;
    	if (soilinfo.getSoiltype()==null || soilinfo.getSoiltype().length()==0) return yield;
    	String sql="SELECT LOWERLIMIT,TOPLIMIT FROM SN_SOIL_TARGET_YIELD " +
		"WHERE CROPID=? AND SOIL=?";
		String[] fields=new String[2];
		fields[0]=crop.getCropid();
		fields[1]=soilinfo.getSoiltype();
		Cursor cur=null;
		SQLiteDatabase database = null;
		try {
			database = DbManager.getInstance().getLocalDatabase();
			//SQLiteDatabase database = DBManager.getInstance().getDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
            	yield[0]=cur.getDouble(cur.getColumnIndex("lowerlimit"));
            	yield[1]=cur.getDouble(cur.getColumnIndex("toplimit"));
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
    	return yield;
    }
    
    /**
     * 根据地貌决策相关目标产量
     * 未找到时，直接采用作物相关目标产量
     * @param crop
     * @return
     */
    private double[] getRegionYield (SoilInfo soilinfo, CropInfo crop){
    	double yield[]=new double[2];
    	yield[0]=-1;
    	yield[1]=-1;
    	if (soilinfo==null || crop==null) return yield;
    	if (soilinfo.getLandform()==null || soilinfo.getLandform().length()==0) return yield;
    	String sql="SELECT LOWERLIMIT,TOPLIMIT FROM SN_LANDFORM_TARGET_YIELD " +
		"WHERE CROPID=? AND REGION=?";
		String[] fields=new String[2];
		fields[0]=crop.getCropid();
		fields[1]=soilinfo.getLandform();
		Cursor cur=null;
		SQLiteDatabase database = null;
		try {
			database = DbManager.getInstance().getLocalDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
            	yield[0]=cur.getDouble(cur.getColumnIndex("lowerlimit"));
            	yield[1]=cur.getDouble(cur.getColumnIndex("toplimit"));
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
    	return yield;
    	
    }
    
    
    /**
     * 根据作物编号　养分类型和养分值，获得当前养分能达到的最低目标产量
     * @param cropid
     * @param Fertilizer
     * @param value
     * @return
     */
    public int getYield4ByFertilizerValue(String cropid,String Fertilizer,double value,int yield){
    	
    	
    	String sql="SELECT TARGETYIELD FROM SN_FERTILIZATION_INDEX " +
		"WHERE CROPID=? AND FERTILIZER=? AND LACK<>-1 AND " +
		"NUTRIENTFLOOR<=? AND TARGETYIELD<=? ORDER BY TARGETYIELD DESC";
		String[] fields=new String[4];
		fields[0]=cropid;
		fields[1]=Fertilizer;
		fields[2]=String.valueOf(value);
		fields[3]=String.valueOf(yield);
		Cursor cur=null;
		int result=0;
		SQLiteDatabase database = null;
		try {
			database = DbManager.getInstance().getLocalDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
				result=cur.getInt(cur.getColumnIndex("targetyield"));
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
    	return result;
    }
    

    
    /**
     * 根据作物编号　养分类型和养分值，获得当前养分能达到的最低目标产量的上一个目标产量
     * @param cropid
     * @param Fertilizer
     * @param value
     * @return
     */
    public int getHighYield4ByFertilizerValue(String cropid,String Fertilizer,double value,int yield){
    	
    	
    	//查询所有氮素含量可达目标产量
        String sql="SELECT TARGETYIELD FROM SN_FERTILIZATION_INDEX " +
        		"WHERE CROPID=? AND FERTILIZER=? AND LACK<>-1 AND " +
        		"NUTRIENTFLOOR>=? AND TARGETYIELD>=? ORDER BY TARGETYIELD";
		String[] fields=new String[4];
		fields[0]=cropid;
		fields[1]=Fertilizer;
		fields[2]=String.valueOf(value);
		fields[3]=String.valueOf(yield);
		Cursor cur=null;
		int result=0;
		SQLiteDatabase database = null;
		try {
			database = DbManager.getInstance().getLocalDatabase();
			cur =  database.rawQuery(sql,fields);
			System.out.println("getHighYield4ByFertilizerValue:" + cur.getCount());
			if(cur.moveToNext()){
				result=cur.getInt(cur.getColumnIndex("targetyield"));
				System.out.println("getHighYield4ByFertilizerValue__result:"+result);
	        }

		}
        catch (Exception ex) {
            //throw ex;
        	ex.printStackTrace();
        	System.out.println(ex.getMessage());
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
    	return result;
    }
    
    
    /**
     * 根据作物编号　养分类型和养分值，获得当前养分能达到的目标产量列表
     * @param cropid
     * @param Fertilizer
     * @param value
     * @return
     */
    public double getFertilizerValue(String cropid,String Fertilizer,double value,int yield){
    	//查询所有氮素含量可达目标产量
        String sql="SELECT LACK FROM SN_FERTILIZATION_INDEX " +
        		"WHERE cropid=? AND fertilizer=? AND lack<>-1 AND " +
        		"nutrientfloor<=? AND targetyield=? ORDER BY lack";
		String[] fields=new String[4];
		fields[0]=cropid;
		fields[1]=Fertilizer;
		fields[2]=String.valueOf(value);
		fields[3]=String.valueOf(yield);
		Cursor cur=null;
        double result=-1;
		SQLiteDatabase database = null;
		try {
			database = DbManager.getInstance().getLocalDatabase();
			System.out.println("CROPID:"+cropid+" Fertilizer:"+Fertilizer+" value:"+value+" yield:"+yield);
			
			cur =  database.rawQuery(sql,fields);
			System.out.println("getCount:" + cur.getCount());
			if(cur.moveToNext()){
				result=cur.getDouble(cur.getColumnIndex("lack"));
				//result=cur.getDouble(0);
				
				System.out.println("result:"+result);
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
    	return result;
    }
    
    
    
    /**
     * 根据作物编号　养分类型和养分值，获得当前养分能达到的目标产量列表
     * @param cropid
     * @param Fertilizer
     * @param value
     * @return
     */
    public double[] getNeedAndNLoor4Min(String cropid,String Fertilizer,double value,int yield){
    	
    	//查询所有氮素含量可达目标产量
    	String sql="SELECT NUTRIENTFLOOR,LACK FROM SN_FERTILIZATION_INDEX " +
		"WHERE CROPID=? AND FERTILIZER=? AND LACK<>-1 AND " +
		"NUTRIENTFLOOR<=? AND TARGETYIELD=? ORDER BY LACK";
		String[] fields=new String[4];
		fields[0]=cropid;
		fields[1]=Fertilizer;
		fields[2]=String.valueOf(value);
		fields[3]=String.valueOf(yield);
		Cursor cur=null;
		double result[]=new double[2];
		SQLiteDatabase database = null;
		try {
			database = DbManager.getInstance().getLocalDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
            	result[0]=cur.getDouble(cur.getColumnIndex("nutrientfloor"));
            	result[1]=cur.getDouble(cur.getColumnIndex("lack"));
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
    	return result;
    	
    }
    
    
    /**
     * 根据作物编号　养分类型和养分值，获得当前养分能达到的目标产量列表
     * @param cropid
     * @param Fertilizer
     * @param value
     * @return
     */
    public double[] getNeedAndNLoor4Max(String cropid,String Fertilizer,double value,int yield){

    	//查询所有氮素含量可达目标产量
        String sql="SELECT NUTRIENTFLOOR,LACK FROM SN_FERTILIZATION_INDEX " +
		"WHERE CROPID=? AND FERTILIZER=? AND LACK<>-1 AND " +
		"NUTRIENTFLOOR>=? AND TARGETYIELD=? ORDER BY LACK DESC";
    	
    	
		String[] fields=new String[4];
		fields[0]=cropid;
		fields[1]=Fertilizer;
		fields[2]=String.valueOf(value);
		fields[3]=String.valueOf(yield);
		Cursor cur=null;
		double result[]=new double[2];
		result[0]=-1;
		result[1]=-1;
		SQLiteDatabase database = null;
		try {
			database = DbManager.getInstance().getLocalDatabase();
			cur =  database.rawQuery(sql,fields);
			if(cur.moveToNext()){
            	result[0]=cur.getDouble(cur.getColumnIndex("nutrientfloor"));
            	result[1]=cur.getDouble(cur.getColumnIndex("lack"));
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
    	return result;
    	
    }
    
    
	/**
	 * @return the 土壤资料信息
	 */
	public SoilInfo getSoilinfo() {
		return soilinfo;
	}

	/**
	 * @param soilinfo the 土壤资料信息 to set
	 */
	public void setSoilinfo(SoilInfo soilinfo) {
		this.soilinfo = soilinfo;
	}

	/**
	 * @return the 作物资料信息
	 */
	public CropInfo getCropinfo() {
		return cropinfo;
	}

	/**
	 * @param cropinfo the 作物资料信息 to set
	 */
	public void setCropinfo(CropInfo cropinfo) {
		this.cropinfo = cropinfo;
	}
   
	private List getYieldList(int lowerlimit,int toplimit,int divide){
		//非法数据返回，避免异常数据导致死循环
		if (lowerlimit<0 || toplimit <0 || divide<=0) return null;
		ArrayList list=new ArrayList();
		int t=lowerlimit-divide;
		do {
			t=t+divide;
			if (t>toplimit) {
				list.add(0,toplimit);
			}else{
				list.add(0,t);
			}
		} while (t<toplimit);
		return list;
	}
	
}
