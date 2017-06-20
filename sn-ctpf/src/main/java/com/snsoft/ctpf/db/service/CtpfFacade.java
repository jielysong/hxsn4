package com.snsoft.ctpf.db.service;


import com.snsoft.ctpf.beans.CardInfo;
import com.snsoft.ctpf.beans.CropInfo;
import com.snsoft.ctpf.beans.CropNutrientInfo;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.dao.CropNutrientService;
import com.snsoft.ctpf.db.dao.CropService;
import com.snsoft.ctpf.db.dao.FertilizerService;
import com.snsoft.ctpf.db.dao.ParamService;
import com.snsoft.ctpf.db.dao.SoilInfoService;
import com.snsoft.ctpf.db.dao.TargetYieldService;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CtpfFacade {
	private CropService service;
	public CtpfFacade(){
		service=new CropService();
	}
    
	
	
    /**
     * 返回根据作物ID返回作物信息
     * @return
     * @throws SQLException 
     */
    public CropInfo getCropInfoByCropid(String cropid){
    	//CropService service=new CropService();
    	return service.getCropInfo(cropid);
    }
    
    /**
     * 获得本县所有作物列表
     * @return
     */
    public List getCropInfoList(){
    	CropService service=new CropService();
    	return service.getCropInfoList(getCountyid());
    }
    
    /**
     * 获得本县所有作物列表
     * @return
     */
    public List getCropInfoList(String countyid){
    	CropService service=new CropService();
    	if (countyid==null || "".equals(countyid)) {
    		//调 用系统参数获得本地县编码
    		return service.getCropInfoList(getCountyid());
    	}
    	return service.getCropInfoList(countyid);
    }
    
    /**
     * 获得本县、指定乡所有作物列表
     * @return
     */
    public List getCropInfoList(String countyid,String xiangming){
    	CropService service=new CropService();
    	List list=new ArrayList();
    	if (countyid==null || "".equals(countyid)) {
    		//调用系统参数获得本地县编码
    		list=service.getCropInfoList(getCountyid(),xiangming);
    	}
    	list=service.getCropInfoList(countyid,xiangming);
    	if (list.size()>0){
    		return list;
    	}else{
    		//如果按乡未找到作物，那么取消按乡查询条件
    		return getCropInfoList(countyid);
    	}
    }
    
    
    
	/**
	 * 获得肥料列表
	 * @param Fertilizertype　肥料类型0 不限　1底肥　2追肥
	 * @param Dedicated　是否专用肥　0 非专用肥　1 专用肥
	 * @return
	 */
    public List getFertilizerInfoList(String Fertilizertype,String Dedicated,String cropid){
    	FertilizerService service=new FertilizerService();
    	return service.getFertilizerInfoList(getCountyid(),Fertilizertype,Dedicated,cropid);
    }
    
	/**
	 * 获得肥料列表
	 * @param Fertilizertype　肥料类型0 不限　1底肥　2追肥
	 * @param Dedicated　是否专用肥　0 非专用肥　1 专用肥
	 * @return list
	 */
    public List getFertilizerInfoList(String Fertilizertype,String Dedicated,String countyid,String cropid){
    	if (countyid==null || "".equals(countyid)){
    		return this.getFertilizerInfoList(Fertilizertype, Dedicated,cropid);
    	}else{
    		FertilizerService service=new FertilizerService();
    		return service.getFertilizerInfoList(countyid,Fertilizertype,Dedicated,cropid);
    	}
    }
    
    public List getTargetYield(SoilInfo si,CropInfo crop){
    	TargetYieldService sr=new TargetYieldService(si,crop);
    	return sr.getTargetYield();
    }
    
    
	/**
	 * 获得作物需肥信息,关键的数据运算方法
	 * @return
	 */
    public CropNutrientInfo getCropNutrientInfo(SoilInfo si,CropInfo crop){
    	CropNutrientService cns=new CropNutrientService();
    	CropNutrientInfo cni=cns.getCropNutrientInfo(crop.getCropid(),crop.getYield());
    	//需肥量信息如果是查表方式需要再进行一次调整。
    	ParamService ps=new ParamService();
    	String method=ps.getParameter("ctpfmethod");
    	if ("table".equals(method)){
    		//经验查表法需要重新定位需肥量。
    		TargetYieldService tys=new TargetYieldService();
    		double[] npk=tys.getNutrientrequest(si, crop);
    		//在这里进行了修改，如果是输入养分方式查询，那么直接按输入养分方式查表计算，
    		//但如果是按地图的插值得出的养分含量，那么则需要用设置了需肥量信息进行平衡，如果未设置需肥量，继续用原查表法计算。
    		//
    		if (cni.getNb()<=0){
    			//说明未设置保障需肥量。
    			cni.setN(npk[0]);
    		}else{
    			
    		}
    		
    		cni.setN(npk[0]);
    		cni.setP(npk[1]);
    		cni.setK(npk[2]);
    		
    	}else{
    		//否则需肥量为原需肥量乘以目标产量，然后减去土壤养分含量（含系数计算）
    		//cni=getCropNutrient4C
    		
    	}

        //return null;
    	

    	
    	return cni;
    }

    
    /**
	 * 获得作物需肥信息,关键的数据运算方法
     * @param si   土壤养分情况
     * @param crop 作物信息
     * @param useCorrect  是否使用保障施肥量均衡法
     * @return
     */
    public CropNutrientInfo getCropNutrientInfo(SoilInfo si,CropInfo crop,boolean useCorrect){
    	CropNutrientService cns=new CropNutrientService();
    	CropNutrientInfo cni=cns.getCropNutrientInfo(crop.getCropid(),crop.getYield());
    	//需肥量信息如果是查表方式需要再进行一次调整。
    	ParamService ps=new ParamService();
		//经验查表法需要重新定位需肥量。
		TargetYieldService tys=new TargetYieldService();
		double[] npk=tys.getNutrientrequest(si, crop);
		//在这里进行了修改，如果是输入养分方式查询，那么直接按输入养分方式查表计算，
		//但如果是按地图的插值得出的养分含量，那么则需要用设置了需肥量信息进行平衡，如果未设置需肥量，继续用原查表法计算。
		//

		if (!useCorrect){
			//不需要平衡法直接计算
			cni.setN(npk[0]);
			cni.setP(npk[1]);
			cni.setK(npk[2]);
			
		}else{
			//查询获得平衡系数
			//校正系数：当养分含量大于中间值时，体系值所占比例，另外的比例由保障施肥量决定
			double CorrectMin=Double.valueOf(ps.getParameter("CorrectMin"));
			//校正系数：当养分含量小于中间值时，体系值所占比例，另外的比例由保障施肥量决定
			double CorrectMax=Double.valueOf(ps.getParameter("CorrectMax"));
			
			cni.setN(getCalValue(npk[0],cni.getN(),CorrectMin,CorrectMax));
			cni.setP(getCalValue(npk[1],cni.getP(),CorrectMin,CorrectMax));
			cni.setK(getCalValue(npk[2],cni.getK(),CorrectMin,CorrectMax));

		}

        //return null;
    	

    	
    	return cni;
    }
    
    
    /**
     * 根据查表施肥量、保障施肥量，以及施用比例关系获得实际施肥量
     * @param tablev  查表施肥量
     * @param correctv  保障施肥量
     * @param cmin      查表施肥量大于保障施肥量时的施用比例
     * @param cmax      查表施肥量小于保障施肥量时的施用比例
     * @return
     */
    private double getCalValue(double tablev,double correctv,double cmin,double cmax){
    	double result=tablev;
    	if (tablev>=correctv){
			if (correctv>0)
				//说明有保障施肥量
				result=tablev*cmin+correctv*(1-cmin);
		}else{
			if (correctv>0)
				//说明有保障施肥量
				result=tablev*cmax+correctv*(1-cmax);
		}
    	return result;
    }
    
    
    
    private void test(){
    	CardInfo card=new CardInfo();
    	card.getSoilinfo().getOrganic();
    	card.getSoilinfo().getN();
    	card.getSoilinfo().getP();
    	card.getSoilinfo().getK();
    	card.getCropinfo().getCountyname();
    	card.getCropinfo().getRecommendedvarieties();
    	card.getCropinfo().getYield();
    	card.getCropnutrientinfo().getN();
    }
    
    /**
     * 返回城市编码，主要从参数表中获得当前设置的参数
     * @return
     */
	public String getCountyid(){
		ParamService ps=new ParamService();
    	//获得当前城市代码
    	String county=ps.getParameter("CountyName");
		return ps.getCountyidByname(county);
	}
	
	/**
	 * 根据城市名称，返回城市编码
	 * @param countyname
	 * @return
	 */
	public String getCountyidByname(String countyname){
		ParamService ps=new ParamService();
		return ps.getCountyidByname(countyname);
	}
	
	/**
	 * 格式化输入方法，用于输入小数点后一位。
	 * @param v
	 * @return
	 */
	public String FmtNum(double v){
		//if (v<=0){
		//	return "";
		//}else{
			DecimalFormat df=new DecimalFormat("0.0"); 
			return df.format(Math.abs(v));
		//}
	}
	
	/**
	 * 根据经纬度坐标点，去测土土样表中查询几个临近点，并进行均衡计算，
	 * 得出一个土壤养分含量的数据，进行后续决策计算。
	 * 主要的计算思路是根据经续度坐标，去土样表中查询几个临近点的N,P,K数据
	 * 然后根据这几个点的N,P,K数据以及与该点的距离分配权重进行计算。
	 * @param lng
	 * @param lat
	 * @return
	 */
	public SoilInfo getSoilinfoByPosition(double lng,double lat){
		//首先根据经纬度坐标，查询获得五个最近的临近点N,P,K
		SoilInfoService siservice=new SoilInfoService();
		return siservice.getSoilinfoByPosition(lng,lat);
	}
	/**
	 * 根据地块编号信息返回地块对象
	 * 
	 * @param dkbh 地块编号
	 * @return
	 */
	public SoilInfo getSoilinfoByDkbh(String dkbh){
		//首先根据经纬度坐标，查询获得五个最近的临近点N,P,K
		SoilInfoService siservice=new SoilInfoService();
		return siservice.getSoilinfoByDkbh(dkbh);

	}
	
	/**
	 * 根据农户名称返回地块列表
	 * @param farmer
	 * @return
	 */
	public List getSoilInfoListByFarmer(String farmer){
		SoilInfoService siservice=new SoilInfoService();
		return siservice.getSoilInfoListByFarmer(farmer,5);

	}
	
	/**
	 * 根据农户名称返回地块列表
	 * @param farmer
	 * @return
	 */
	public List getSoilInfoListByFarmer(String farmer,String cid){
		SoilInfoService siservice=new SoilInfoService();
		return siservice.getSoilInfoListByFarmer(farmer,5,cid);
	}
}
