package com.snsoft.ctpf.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.hxsn.ssk.TApplication;
import com.snsoft.ctpf.db.dao.FertilizePlanService;
import com.snsoft.ctpf.db.dao.FertilizerService;
import com.snsoft.ctpf.db.dao.ParamService;

import java.util.ArrayList;


//TODO 请相关包命名空间


/**
 * 施肥建议卡对象，session中记录数据
 * @author malq  Feb 15, 2012 4:22:48 PM
 *
 */
public class CardInfo implements Parcelable{

	/**
	 * 作物资料信息
	 */
	private CropInfo cropinfo;

	/**
	 * 是否有专有肥，没有专有肥的，不出方案一
	 */
	private boolean hasSpecial=false;
	
	/**
	 * 是否有缓释肥，有缓释肥的，不选择底肥
	 */
	private boolean hasSlow=false;	
	
	/**
	 * 土壤资料信息
	 */
	private SoilInfo soilinfo;
	
	/**
	 * 选择施用的基肥列表(id)
	 */
	private String[] basef;
	
	/**
	 * 作物施肥方案数量
	 */
	private int fertilizePlanNum;

	/**
	 * 选择施用的追肥列表(id)
	 */
	private String[] topf;
	
	/**
	 * 县代码
	 */
	private String countryid;
	
	/**
	 * 县名称
	 */
	private String country;
	
	/**
	 * 乡名称
	 */
	private String xiangming;
	
	/**
	 * 作物需肥量信息
	 */
	private CropNutrientInfo cropnutrientinfo;
	
	/**
	 * 底肥方案一
	 */
	private FertilizePlanService basefl1;
	
	/**
	 * 底肥方案二
	 */
	private FertilizePlanService basefl2;

	
	/**
	 * 追肥方案一
	 */
	private FertilizePlanService topfl1;
	
	/**
	 * 追肥方案二
	 */
	private FertilizePlanService topfl2;
	
	/**
	 * 底肥方案数组
	 */
	private FertilizePlanService basefl[];
	
	/**
	 * 追肥方案数组
	 */
	private FertilizePlanService topfl[];
	
	
	/**
	 * @return the hasSpecial
	 */
	public boolean isHasSpecial() {
		return hasSpecial;
	}

	/**
	 * @return the fertilizePlanNum
	 */
	public int getFertilizePlanNum() {
		return fertilizePlanNum;
	}

	/**
	 * @param fertilizePlanNum the fertilizePlanNum to set
	 */
	public void setFertilizePlanNum(int fertilizePlanNum) {
		this.fertilizePlanNum = fertilizePlanNum;
	}

	/**
	 * @param hasSpecial the hasSpecial to set
	 */
	public void setHasSpecial(boolean hasSpecial) {
		this.hasSpecial = hasSpecial;
	}

	/**
	 * @return the hasSlow
	 */
	public boolean isHasSlow() {
		return hasSlow;
	}

	/**
	 * @param hasSlow the hasSlow to set
	 */
	public void setHasSlow(boolean hasSlow) {
		this.hasSlow = hasSlow;
	}

	public CardInfo(){
		soilinfo=new SoilInfo();
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

	/**
	 * @return the soilinfo
	 */
	public SoilInfo getSoilinfo() {
		return soilinfo;
	}

	/**
	 * @param soilinfo the soilinfo to set
	 */
	public void setSoilinfo(SoilInfo soilinfo) {
		this.soilinfo = soilinfo;
	}

	/**
	 * @return the basefl
	 */
	public FertilizePlanService[] getBasefl() {
		return basefl;
	}

	/**
	 * @param basefl the basefl to set
	 */
	public void setBasefl(FertilizePlanService[] basefl) {
		this.basefl = basefl;
	}

	/**
	 * @return the topfl
	 */
	public FertilizePlanService[] getTopfl() {
		return topfl;
	}

	/**
	 * @param topfl the topfl to set
	 */
	public void setTopfl(FertilizePlanService[] topfl) {
		this.topfl = topfl;
	}

	/**
	 * @return the basef
	 */
	public String[] getBasef() {
		return basef;
	}

	/**
	 * @param basef the basef to set
	 */
	public void setBasef(String[] basef) {
		this.basef = basef;
	}

	/**
	 * @return the topf
	 */
	public String[] getTopf() {
		return topf;
	}

	/**
	 * @param topf the topf to set
	 */
	public void setTopf(String[] topf) {
		this.topf = topf;
	}
	
	/**
	 * @return the countryid
	 */
	public String getCountryid() {
		return countryid;
	}

	/**
	 * @return the countryid
	 */
	public String getCountryname() {
		if(	this.country==null){
			ParamService ps=new ParamService();
			String county=ps.getParameter("CountyName");
			return county;
		}else{
			return this.country;
		}
	}
	
	/**
	 * @param country the country to set
	 */
	public void setCountryname(String country) {
		this.country = country;
	}

	/**
	 * @param countryid the countryid to set
	 */
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	/**
	 * @return the basefl1
	 */
	public FertilizePlanService getBasefl1() {
		return basefl1;
	}

	/**
	 * @return the basefl2
	 */
	public FertilizePlanService getBasefl2() {
		return basefl2;
	}

	/**
	 * @param basefl1 the basefl1 to set
	 */
	public void setBasefl1(FertilizePlanService basefl1) {
		this.basefl1 = basefl1;
	}

	/**
	 * @param basefl2 the basefl2 to set
	 */
	public void setBasefl2(FertilizePlanService basefl2) {
		this.basefl2 = basefl2;
	}

	/**
	 * @return the topfl1
	 */
	public FertilizePlanService getTopfl1() {
		return topfl1;
	}

	/**
	 * @param topfl1 the topfl1 to set
	 */
	public void setTopfl1(FertilizePlanService topfl1) {
		this.topfl1 = topfl1;
	}

	/**
	 * @return the topfl2
	 */
	public FertilizePlanService getTopfl2() {
		return topfl2;
	}

	/**
	 * @param topfl2 the topfl2 to set
	 */
	public void setTopfl2(FertilizePlanService topfl2) {
		this.topfl2 = topfl2;
	}

	/**
	 * @return the cropnutrientlinfo
	 */
	public CropNutrientInfo getCropnutrientinfo() {
		return this.cropnutrientinfo;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the xiangming
	 */
	public String getXiangming() {
		return xiangming;
	}

	/**
	 * @param xiangming the xiangming to set
	 */
	public void setXiangming(String xiangming) {
		this.xiangming = xiangming;
	}

	/**
	 * @param  cropnutrientinfo the cropnutrientlinfo to set
	 */
	public void setCropnutrientinfo(CropNutrientInfo cropnutrientinfo) {
		this.cropnutrientinfo = cropnutrientinfo;
	}

	/**
	 * 根据施肥建议卡中所有条件完成计算过程，获得其他需要计算的结果
	 * 本计算方法在计算前需要完成的前提条件
	 * 1.己经初始化Cropinfo作物信息，以获得要计算的施肥作物需肥量等一系列作物相关信息。
	 * 2.己经初始化Soilinfo土壤资料信息，以获得土壤养分含量等相关信息。
	 * 3.已经初始化Basef和Topf相关施用肥料相关信息。
	 * 系统会自动完成以下计算过程
	 * 1.计算用户缺肥量
	 * 2.计算方案一所需要的施肥量，计算方案二所需要的施肥量
	 * 
	 * @return
	 */
	public boolean Calculation() {
		
		double[] buttom=new double[16];
		basefl=new FertilizePlanService[3];
		topfl=new FertilizePlanService[3];
		basefl[0]=new FertilizePlanService();
		basefl[1]=new FertilizePlanService();
		basefl[2]=new FertilizePlanService();
		topfl[0]=new FertilizePlanService();
		topfl[1]=new FertilizePlanService();
		topfl[2]=new FertilizePlanService();
		
		//肥料底肥缺肥量
		buttom[NutrientType.N]=getCropnutrientinfo().getNb();
		buttom[NutrientType.P]=getCropnutrientinfo().getPb();
		buttom[NutrientType.K]=getCropnutrientinfo().getKb();
        
        //添加底肥
        addBaseFertilizers(buttom);		//从第0个方案逐个查询是否被选用，同时找到被选用的方案并添加对应方案的肥料，同时返回方案序号
        
        boolean isNoBase=false;
        if (buttom[NutrientType.N]+buttom[NutrientType.P]+buttom[NutrientType.K]<1){
        	isNoBase=true;
		}

        for(int i=0;i<=this.fertilizePlanNum;i++){
        	//所有施肥方案均计算，底肥差异值，然后以便于让追肥量进行平衡
        	double[] dif=getDif(buttom,basefl[i]);   //此方法非常重要，即完成了底肥方案运算，同时又完成了差异肥量运算
        	double[] newd=new double[16];
        	//对肥料进行养分追肥平衡

			boolean isAddFer = TApplication.sysParam.isAddFer();
        	if (isAddFer){
        		//如果需要追肥平衡
        		newd[NutrientType.N]=getCropnutrientinfo().getNt()+dif[0];
            	newd[NutrientType.P]=getCropnutrientinfo().getPt();
            	newd[NutrientType.K]=getCropnutrientinfo().getKt()+dif[2];
        	}else{
        		//不需追肥平衡的话,可以直接施肥
        		newd[NutrientType.N]=getCropnutrientinfo().getNt();
            	newd[NutrientType.P]=getCropnutrientinfo().getPt();
            	newd[NutrientType.K]=getCropnutrientinfo().getKt();
        	}

        	addTopFertilizers(newd,isNoBase,i);
        	topfl[i].calculate(newd);
        }
		return true;
	}

/**
 * 根据施肥方案以及对应需肥，计算施肥方案与施肥的差值量
 * @param d　   需要养分含量
 * @param fp　施肥方案
 * @return
 */
private double[] getDif(double[] d,FertilizePlanService fp){
	//首先将底施肥进行施肥计算
	fp.calculate(d);
	double[] dif=new double[3]; 
	dif[0]=d[NutrientType.N];
	dif[1]=d[NutrientType.P];   
	dif[2]=d[NutrientType.K];
	while(fp.hasMore()){
    	FertilizerInfo mo=(FertilizerInfo)fp.next();
        dif[0]=dif[0]-(mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
        if (mo.getPercent(NutrientType.P)>=99){
        	dif[1]=0;
        }else{
        	dif[1]=dif[1]-(mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
        }
        dif[2]=dif[2]-mo.getDosage() * mo.getPercent(NutrientType.K) / 100;
    }
	fp.First();
	return dif;
}

	/**
	 * 从第i个方案逐个查询是否被选用，同时找到被选用的方案并添加对应方案的肥料，同时返回方案序号
	 * @param d  施肥所需肥料量
	 * @param isNoBase	是否无底施肥，那么追肥按底施来计算
	 * @param index 　　当前第几个施肥方案的序号
	 */
	private void addTopFertilizers(double[] d, boolean isNoBase,int index) {
		ArrayList fl=new ArrayList();
		FertilizerService fs=new FertilizerService();
		ParamService ps=new ParamService();
		int k=0;	//当前需要添加肥料编号
		if (this.cropinfo.getSchemeByBit(0)){
			//说明是公共配方肥方案，去公共库里查询公共肥料
			if (k==index){
				//说明是此方案增加追肥量
				if (isNoBase){
					//未底肥时，需要用配方底肥施用
					fl=(ArrayList) fs.getPublicFertilizers("1", "1",this.cropinfo.getCropname());
					for (int i=0;i<fl.size();i++){
						FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
						topfl[k].addFertilizerInfo(mo1);
					}
				}
				fl=(ArrayList) fs.getFertilizerInfoList(this.topf);
				//如果有底肥施用，那么采用追肥
				for (int i=0;i<fl.size();i++){
					FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
					topfl[k].addFertilizerInfo(mo1);
				}
			}
			k++;		//施肥方案数加一
		}
		
		if (this.cropinfo.getSchemeByBit(1)){
			//说明是县级配方肥方案，去县库里查询配方肥料
			if (k==index){
				//说明是此方案增加追肥量
				if (isNoBase){
					fl=(ArrayList) fs.getFertilizerInfoList(this.countryid, "1", "1",this.cropinfo.getCropid());
					for (int i=0;i<fl.size();i++){
						FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
						topfl[k].addFertilizerInfo(mo1);
					}
				}else{
					//这里的else是增加了追肥专用肥的施用计算
					fl=(ArrayList) fs.getFertilizerInfoList(this.countryid, "2", "1",this.cropinfo.getCropid());
					for (int i=0;i<fl.size();i++){
						FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
						topfl[k].addFertilizerInfo(mo1);
					}
				}
				fl=(ArrayList) fs.getFertilizerInfoList(this.topf);
				//如果有底肥施用，那么采用追肥
				for (int i=0;i<fl.size();i++){
					FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
					topfl[k].addFertilizerInfo(mo1);
				}
			}
			k++;
		}
		if (this.cropinfo.getSchemeByBit(3)){
			if (k==index){
				//说明是此方案增加追肥量
				if (isNoBase){
					//自制配方肥
					FertilizerInfo mo1=makeFertilizerInfo(d,40,50,40,50);
					topfl[k].addFertilizerInfo(mo1);
				}
				fl=(ArrayList) fs.getFertilizerInfoList(this.topf);
				for (int i=0;i<fl.size();i++){
					FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
					topfl[k].addFertilizerInfo(mo1);
				}
			}
			k++;
		}
		if (this.cropinfo.getSchemeByBit(2)){
			//自选肥料
			if (k==index){
				//说明是此方案增加追肥量
				fl=(ArrayList) fs.getFertilizerInfoList(this.topf);
				for (int i=0;i<fl.size();i++){
					FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
					topfl[k].addFertilizerInfo(mo1);
				}
			}
			k++;
		}

		return ;
	}

	/**
	 * 从第i个方案逐个查询是否被选用，同时找到被选用的方案并添加对应方案的肥料，同时返回方案序号
	 * @param d  需要施用的肥料量
	 * 
	 * @return
	 */
	private void addBaseFertilizers(double[] d) {
		ArrayList fl;
		FertilizerService fs=new FertilizerService();
		int k=0;	//当前需要添加肥料编号
		Log.i("CardInfo","bit0="+this.cropinfo.getSchemeByBit(0));
		Log.i("CardInfo","bit1="+this.cropinfo.getSchemeByBit(1));
		Log.i("CardInfo","bit2="+this.cropinfo.getSchemeByBit(2));
		Log.i("CardInfo","bit3="+this.cropinfo.getSchemeByBit(3));
		if (this.cropinfo.getSchemeByBit(0)){
			//说明是公共配方肥方案，去公共库里查询公共肥料
			fl=(ArrayList) fs.getPublicFertilizers("1", "1",this.cropinfo.getCropname());
			Log.i("CardInfo","fl0.size="+fl.size());
			for (int i=0;i<fl.size();i++){
				FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
				basefl[k].addFertilizerInfo(mo1);
			}
			k++;
		}
		
		if (this.cropinfo.getSchemeByBit(1)){
			//说明是县级配方肥方案，去县库里查询配方肥料
			fl=(ArrayList) fs.getFertilizerInfoList(this.countryid, "1", "1",this.cropinfo.getCropid());
			Log.i("CardInfo","fl1.size="+fl.size());
			for (int i=0;i<fl.size();i++){
				FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
				basefl[k].addFertilizerInfo(mo1);
			}
			k++;
		}
		if (this.cropinfo.getSchemeByBit(3)){
			//自制配方肥
			FertilizerInfo mo1=makeFertilizerInfo(d,45,50,40,50);

			basefl[k].addFertilizerInfo(mo1);
			k++;
		}
		if (this.cropinfo.getSchemeByBit(2)){
			//自选肥料
			fl=(ArrayList) fs.getFertilizerInfoList(this.basef);
			Log.i("CardInfo","fl2.size="+fl.size());
			for (int i=0;i<fl.size();i++){
				FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
				basefl[k].addFertilizerInfo(mo1);
			}
			k++;
		}
		if (k>1) this.hasSpecial=true;

		//将总的施肥计划数量写入
		setFertilizePlanNum(k);
		return ;
	}

	/**

	 * 根据需肥量，自动创建配方肥，创建配方肥的范围
	 * @param d  需肥量
	 * @param a  与下面的参数b共同组成配方肥比例区间（一般为40-50)
	 * @param b
	 * @param x  与下面施肥量范围区间一般为40-50
	 * @param y
	 * @return 返回一个配方肥
	 */
	private FertilizerInfo makeFertilizerInfo(double[] d,int a,int b,int x,int y) {
		double N=d[NutrientType.N];
		double P=d[NutrientType.P];
		double K=d[NutrientType.K];
		double V=N+P+K;		//需肥总量
		double Z;			//最终的配方肥比例
		if (V<=Double.valueOf(a)*x/100){
			//如果肥料过少，直接用最小配方肥比例
			Z=a;
		}else if(V>=Double.valueOf(b)*y/100){
			//如果肥料需求量过大，直接用最大配方肥比例，同时适当降低N肥比例
			N=N-(V-Double.valueOf(b)*y/100);
			if (N<0)N=0;
			Z=b;
		}else{
			//其他中间情况的配方比例取中间折中比例算法
			Z=getValueBy5Numbers(Double.valueOf(a)*x/100,Double.valueOf(b)*y/100,a,b,V);
		}
		FertilizerInfo fi=new FertilizerInfo();
		
		fi.setP((int)(Z* P/V+0.5 ));
		fi.setK((int)(Z* K/V+0.5 ));
		fi.setN((int)Z-fi.getP()-fi.getK());
		fi.setDedicated(1);
		String finame="配方肥("+fi.getN()+"-"+fi.getP()+"-"+fi.getK()+")";
		fi.setFertilizername(finame);
		fi.setShortname(finame);
		return fi;
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

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeParcelable(cropinfo, flags);
		dest.writeString(country);
		dest.writeString(countryid);
		dest.writeParcelable(soilinfo, flags);
		dest.writeStringArray(basef);
		dest.writeStringArray(topf);
	}
	
	public static final Creator<CardInfo> CREATOR = new Creator<CardInfo>(){

		@Override
		public CardInfo createFromParcel(Parcel parcel)
		{
			CardInfo cardInfo = new CardInfo();
			cardInfo.cropinfo = parcel.readParcelable(CropInfo.class.getClassLoader());
			cardInfo.country = parcel.readString();
			cardInfo.countryid = parcel.readString();
			cardInfo.soilinfo = parcel.readParcelable(SoilInfo.class.getClassLoader());
			cardInfo.basef = parcel.createStringArray();//parcel.readStringArray(cardInfo.basef);
			cardInfo.topf =parcel.createStringArray();//parcel.readStringArray(cardInfo.getTopf());
			
			return cardInfo;
		}

		@Override
		public CardInfo[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new CardInfo[size];
		}
		
	};
	
	
	
//	
//	/**
//	 * 根据施肥建议卡中所有条件完成计算过程，获得其他需要计算的结果
//	 * 本计算方法在计算前需要完成的前提条件
//	 * 1.己经初始化Cropinfo作物信息，以获得要计算的施肥作物需肥量等一系列作物相关信息。
//	 * 2.己经初始化Soilinfo土壤资料信息，以获得土壤养分含量等相关信息。
//	 * 3.已经初始化Basef和Topf相关施用肥料相关信息。
//	 * 系统会自动完成以下计算过程
//	 * 1.计算用户缺肥量
//	 * 2.计算方案一所需要的施肥量，计算方案二所需要的施肥量
//	 * 
//	 * @return
//	 */
//	public boolean Calculation() {
//		
//		
//		double[] d=new double[16];
//		
//		FertilizerService fs=new FertilizerService();
//		ParamService ps=new ParamService();
//		
//		ArrayList fl=new ArrayList();
//
//		
//		//肥料底肥缺肥量
//		d[NutrientType.N]=getCropnutrientinfo().getNb();
//        d[NutrientType.P]=getCropnutrientinfo().getPb();
//        d[NutrientType.K]=getCropnutrientinfo().getKb();
//        
//        boolean isNoBase=false;
//        if (d[NutrientType.N]+d[NutrientType.P]+d[NutrientType.K]<1){
//        	isNoBase=true;
//		}
//        
//        topfl2=new FertilizePlanService();
//        topfl1=new FertilizePlanService();
//        //以下查询所有指定作物的专用底肥 ,相当于专用肥方案的所有底肥
//		fl=(ArrayList) fs.getFertilizerInfoList(this.countryid, "1", "1",this.cropinfo.getCropid());
//		//以下是对专用肥底肥方案添加肥料并进行施肥决策计算
//		basefl1=new FertilizePlanService();
//		for (int i=0;i<fl.size();i++){
//			FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
//			basefl1.addFertilizerInfo(mo1);
//		}
//		if (fl.size()<1){
//			//说明没有专有肥,则不显示推荐方案一
//			hasSpecial=false;
//		}else{
//			hasSpecial=true;
//		}
//		
//		basefl2=new FertilizePlanService();
//		//如果是玉米作物的话，去邯郸市大库去寻找玉米复混肥
//		if ("玉米".equals(this.cropinfo.getCropname())){
//			hasSlow=true;
//			fl=(ArrayList) fs.getFertilizerInfoList("1", "1");
//			for (int i=0;i<fl.size();i++){
//				FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
//				basefl2.addFertilizerInfo(mo1);
//			}
//		}
//		
//		
//		//玉米的特殊操作处理完毕
//		
//		//以下查询所有指定作物的专用底肥 ,相当于选择肥方案的所有底肥
//		fl=(ArrayList) fs.getFertilizerInfoList(this.basef);
//		
//		for (int i=0;i<fl.size();i++){
//			FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
//			basefl2.addFertilizerInfo(mo1);
//			basefl1.addFertilizerInfo(mo1);
//			//如果底肥用量为0，那么将底肥做为追肥添加
//			if (isNoBase){
//				topfl2.addFertilizerInfo(mo1);
//			}
//
//		}
//		
//		
//		basefl1.calculate(d);
//		basefl2.calculate(d);
//		
//		//这里对施肥方案一中的氮肥、磷肥和钾肥进行了计算，对施肥方案一（复合肥）不准确的肥量从追施补齐。
//		double[] dif=new double[3]; 
//		dif[0]=d[NutrientType.N];
//		dif[1]=d[NutrientType.P];   //因为磷肥已经补满不再计算了
//		dif[2]=d[NutrientType.K];
//		while(basefl1.hasMore()){
//        	FertilizerInfo mo=(FertilizerInfo)basefl1.next();
//            dif[0]=dif[0]-(mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
//            if (mo.getPercent(NutrientType.P)>=99){
//            	dif[1]=0;
//            }else{
//            	dif[1]=dif[1]-(mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
//            }
//            dif[2]=dif[2]-mo.getDosage() * mo.getPercent(NutrientType.K) / 100;
//        }
//		basefl1.First();
//		
//		//肥料追肥缺肥量
//		d[NutrientType.N]=getCropnutrientinfo().getNt()+dif[0];
//		
//		/*
//		 * 以下注释了追释的底限性要求
//		if (d[NutrientType.N]<(getCropnutrientinfo().getNt()/2)){
//			//如果氮肥因为底肥使追肥小于原追肥量的一半，施用量修改为一需求量的一半
//			d[NutrientType.N]=getCropnutrientinfo().getNt()/2;
//		}
//		if (d[NutrientType.N]>(getCropnutrientinfo().getNt()*2)){
//			//如果氮肥因为底肥使追肥大于原追肥量的一倍，施用量修改为一需求量的一倍
//			d[NutrientType.N]=getCropnutrientinfo().getNt()*2;
//		}
//		*/
//			
//        d[NutrientType.P]=getCropnutrientinfo().getPt();
//        d[NutrientType.K]=getCropnutrientinfo().getKt();
//
//        
//		//以下查询所有指定作物的专用追肥 ,相当于专用肥方案的所有追肥
//		fl=(ArrayList) fs.getFertilizerInfoList(this.countryid, "2", "1",this.cropinfo.getCropid());
//		//以下是对专用肥追肥方案添加肥料并进行施肥决策计算
//
//		for (int i=0;i<fl.size();i++){
//			FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
//			topfl1.addFertilizerInfo(mo1);
//		}
//		
//		//以下查询所有指定作物的专用追肥 ,相当于选择肥方案的所有追肥
//		fl=(ArrayList) fs.getFertilizerInfoList(this.topf);
//		if (!isNoBase){
//			//如果有底肥施用，那么采用追肥
//			for (int i=0;i<fl.size();i++){
//				FertilizerInfo mo1=(FertilizerInfo)fl.get(i);
//				topfl1.addFertilizerInfo(mo1);
//				topfl2.addFertilizerInfo(mo1);
//			}
//		}
//		topfl1.calculate(d);
//		
//		
//		//在追施之前，先要根据底施氮肥量在追肥进行补齐。
//		d[NutrientType.N]=getCropnutrientinfo().getNb();
//        d[NutrientType.P]=getCropnutrientinfo().getPb();
//        d[NutrientType.K]=getCropnutrientinfo().getKb();
//		dif[0]=d[NutrientType.N];
//		dif[1]=d[NutrientType.P];   //因为磷肥已经补满不再计算了
//		dif[2]=d[NutrientType.K];
//		while(basefl2.hasMore()){
//        	FertilizerInfo mo=(FertilizerInfo)basefl2.next();
//            dif[0]=dif[0]-(mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
//            if (mo.getPercent(NutrientType.P)>=99){
//            	dif[1]=0;
//            }else{
//            	dif[1]=dif[1]-(mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
//            }
//            dif[2]=dif[2]-mo.getDosage() * mo.getPercent(NutrientType.K) / 100;
//        }
//		basefl2.First();
//
//		
//		//肥料追肥缺肥量
//		//d[NutrientType.N]=getCropnutrientinfo().getNt();
//		d[NutrientType.N]=getCropnutrientinfo().getNt()+dif[0];
//        d[NutrientType.P]=getCropnutrientinfo().getPt();
//        d[NutrientType.K]=getCropnutrientinfo().getKt();
//        
//		topfl2.calculate(d);
//		
////		
////		//复合肥
////		FertilizerInfo mo1=new FertilizerInfo();
////        mo1.setFertilizername("磷酸二铵");
////        mo1.setN(12);
////        mo1.setP(44);
////        fp.addFertilizerInfo(mo1);
////
////        
////        //尿素
////        FertilizerInfo mo2=new FertilizerInfo();
////        mo2.setFertilizername("尿素");
////        mo2.setN(46);
////        fp.addFertilizerInfo(mo2);
////        
////        //氯化钾
////        FertilizerInfo mo3=new FertilizerInfo();
////        mo3.setFertilizername("氯化钾");
////        mo3.setK(60);
////        fp.addFertilizerInfo(mo3);
////
////        d[NutrientType.N]=getCropnutrientinfo().getNb();
////        d[NutrientType.P]=getCropnutrientinfo().getPb();
////        d[NutrientType.K]=getCropnutrientinfo().getKb();
////        
////        //d[NutrientType.K]=0;
////        d[NutrientType.N]=1;
////        d[NutrientType.P]=108.0;
////        d[NutrientType.K]=0;
////        
////        
////        fp.calculate(d);
//		
//		
//		
////        System.out.println("---------------------------------");
////        System.out.println("施肥方案一：");
////        System.out.println("底肥：");
////        while(basefl1.hasMore()){
////        	FertilizerInfo mo=(FertilizerInfo)basefl1.next();
////            System.out.println(mo.getFertilizername()+" 用量："+mo.getDosage());
////            System.out.println("含氮" + mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
////            System.out.println("含磷" + mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
////            System.out.println("含钾" + mo.getDosage() * mo.getPercent(NutrientType.K) / 100);
////        }
////        System.out.println("追肥：");
////        while(topfl1.hasMore()){
////        	FertilizerInfo mo=(FertilizerInfo)topfl1.next();
////            System.out.println(mo.getFertilizername()+" 用量："+mo.getDosage());
////            System.out.println("含氮" + mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
////            System.out.println("含磷" + mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
////            System.out.println("含钾" + mo.getDosage() * mo.getPercent(NutrientType.K) / 100);
////        }
////        
////        System.out.println("---------------------------------");
////        System.out.println("施肥方案二：使用量：");
////        System.out.println("底肥：");
////        while(basefl2.hasMore()){
////        	FertilizerInfo mo=(FertilizerInfo)basefl2.next();
////            System.out.println(mo.getFertilizername()+" 用量："+mo.getDosage());
////            System.out.println("含氮" + mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
////            System.out.println("含磷" + mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
////            System.out.println("含钾" + mo.getDosage() * mo.getPercent(NutrientType.K) / 100);
////        }
////        System.out.println("追肥：");
////        while(topfl2.hasMore()){
////        	FertilizerInfo mo=(FertilizerInfo)topfl2.next();
////            System.out.println(mo.getFertilizername()+" 用量："+mo.getDosage());
////            System.out.println("含氮" + mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
////            System.out.println("含磷" + mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
////            System.out.println("含钾" + mo.getDosage() * mo.getPercent(NutrientType.K) / 100);
////        
////        }
////        
////        while(fp.hasMore()){
////        	FertilizerInfo mo=(FertilizerInfo)fp.next();
////            System.out.println(mo.getFertilizername()+" 用量："+mo.getDosage());
////            System.out.println("含氮" + mo.getDosage() * mo.getPercent(NutrientType.N) / 100);
////            System.out.println("含磷" + mo.getDosage() * mo.getPercent(NutrientType.P) / 100);
////            System.out.println("含钾" + mo.getDosage() * mo.getPercent(NutrientType.K) / 100);
////        }
//        //fp.calculate(d);
//        
//        
//		return false;
//	}
	
	
	
}
