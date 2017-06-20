package com.snsoft.ctpf.db.service;


import com.snsoft.ctpf.beans.CropInfo;
import com.snsoft.ctpf.beans.SoilInfo;
import com.snsoft.ctpf.db.dao.ParamService;
import com.snsoft.ctpf.db.dao.SoilInfoService;

import java.util.List;

public class DBSearch{
	private CtpfFacade facade;
	
	private static DBSearch dbSearch;
	
	public static DBSearch getInstance(){
		if(null == dbSearch){
			dbSearch = new DBSearch();
		}
		return dbSearch;	
	}
	
	private DBSearch(){
		facade=new CtpfFacade();
	}
	
	/**
	 * 	根据城市代码返回作物信息列表
	 * @param countyid
	 * @return
	 */
	public List<CropInfo> getCropInfoList(String countyid){
		
		//返回城市对应的编号列表
		return facade.getCropInfoList(countyid);
	}
	
	/**
	 * 	根据城市代码和乡镇名称返回作物信息列表
	 * @param countyid
	 * @param xiangming
	 * @return
	 */
	public List<CropInfo> getCropInfoList(String countyid,String xiangming){
		//返回城市对应的编号列表
		return facade.getCropInfoList(countyid,xiangming);
	}
	
	
	/**
	 * 根据土壤养分对象和作物对象获得对应情况下的目标产量
	 * @param si
	 * @param crop
	 * @return
	 */
    public List getTargetYield(SoilInfo si,CropInfo crop){
    	return facade.getTargetYield(si,crop);
    }
    
    
	/**
	 * 获得肥料列表
	 * @param Fertilizertype　肥料类型0 不限　1底肥　2追肥
	 * @param Dedicated　是否专用肥　0 非专用肥　1 专用肥
	 * @return list
	 */
    public List getFertilizerInfoList(String Fertilizertype,String Dedicated,String countyid,String cropid){
    	//return facade.getFertilizerInfoList("1","0",countyid,card.getCropinfo().getCropid());   //获得底肥非专用肥。
    	//facade.getFertilizerInfoList("2","0",countyid,card.getCropinfo().getCropid());    //获得追肥非专用肥。
    	return facade.getFertilizerInfoList(Fertilizertype,Dedicated,countyid,cropid);   //获得底肥非专用肥。
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
		return facade.getSoilinfoByPosition(lng,lat);
	}
	
	
	/**
	 * 根据地块编号信息返回地块对象
	 * 
	 * @param dkbh 地块编号
	 * @return
	 */
	public SoilInfo getSoilinfoByDkbh(String dkbh){
		return facade.getSoilinfoByDkbh(dkbh);

	}
	
	/**
	 * 根据不完整的地块编号信息返回完整的地块编号
	 * 
	 * @param code 地块编号
	 * @return
	 */
	public List<String> getDKList(String code){
		SoilInfoService si=new SoilInfoService();
		return si.getDKList(code);
	}
	
	/**
	 * 城市类型
	 */
	public String getCountyType(String countyname){
		ParamService ps = new ParamService();
		return ps.getCountyTypeByname(countyname);
	}
	
	/**
     * 根据市，查询市下的所有县
     * @param city
     * @return
     */

	public List<String> getCountys(String city){
		SoilInfoService si=new SoilInfoService();
		return si.getCounty(city);
    }
	
	/**
     * 根据县，查询县下的所有乡镇
     * @param county
     * @return
     */

	public List<String> getTowns(String county){
		SoilInfoService si=new SoilInfoService();
		return si.getTowns(county);
    }


	/**
	 * 根据县、乡，获取村信息
	 * @return
	 */
	public List<String> getVillages(String county,String town){
		SoilInfoService si=new SoilInfoService();
		return si.getVillages(county,town);
    }
	
	/**
	 * 根据县、乡，村获取方位信息
	 * @return
	 */
	public List<String> getPositions(String county,String town,String village){
		SoilInfoService si=new SoilInfoService();
		return si.getPositions(county,town,village);
    }
	
	/**
	 * 根据县、乡，村、方位获取农户信息
	 * @return
	 */
	public List<String> getFarmers(String county,String town,String village,String position){
		SoilInfoService si=new SoilInfoService();
		return si.getFarmers(county,town,village,position);
	}
	
	/**
	 *  根据县、乡，村、方位和农户信息，获取土壤养分元素信息
	 * @return
	 */
	public SoilInfo getYuansu(String county,String town,String village,String position,String farmer){
		SoilInfoService si=new SoilInfoService();
		return si.getYuansu(county,town,village,position,farmer);
	}
	
	/**
	 * 根据县、乡、村和方位，获取土壤养分元素信息
	 */
	public SoilInfo getSoilElement(String county,String town,String village,String position){
		SoilInfoService si = new SoilInfoService();
		return si.getSoilinfoByXzqh(county,town,village,position);
	}

	
	 /**
     * 返回城市编码，主要从参数表中获得当前设置的参数
     * @return
     */
	public String getCountyName(){
		ParamService ps=new ParamService();
		//获得当前城市代码
		String county=ps.getParameter("CountyName");
		return county;
	}
	
	 /**
     * 返回城市编码，主要从参数表中获得当前设置的参数
     * @return
     */
	public String getCountyidByname(String county){
		ParamService ps=new ParamService();
		//获得当前城市代码
		return ps.getCountyidByname(county);
	}
	
	public List getSoilInfoListByFarmer(String farmer,long number,String cid){
		SoilInfoService si = new SoilInfoService();
		return si.getSoilInfoListByFarmer(farmer,number,cid);
	}
	
}
