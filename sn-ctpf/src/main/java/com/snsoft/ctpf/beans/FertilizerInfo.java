package com.snsoft.ctpf.beans;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author Administrator
 * 描述:肥料信息表
 */
public class FertilizerInfo{


	/**
	 * 肥料编号UUID
	 */
	private String Fertilizerid;

	/**
	 * 肥料名称
	 */
	private String Fertilizername;

	/**
	 * 肥料简称
	 */
	private String Shortname;

	/**
	 * 生产厂家相关信息
	 */
	private String Company;

	/**
	 * 肥料氮素含量百分比
	 */
	private int N;

	/**
	 * 肥料五氧化二磷含量
	 */
	private int P;

	/**
	 * 肥料氧化钾含量
	 */
	private int K;

	/**
	 * 氮利用率
	 */
	private int Useration;

	/**
	 * 磷利用率
	 */
	private int Useratiop;

	/**
	 * 钾利用率
	 */
	private int Useratiok;

	/**
	 * 推荐肥1 ，备选肥0
	 */
	private int Recommend;

	/**
	 * 底肥1，追肥2，不限0
	 */
	private int Fertilizertype;

	/**
	 * 专用肥1，非专用肥0
	 */
	private int Dedicated;

	/**
	 * 肥料所属专用作物编号，专用肥时有效
	 */
	private String Cropid;

	/**
	 * null
	 */
	private String Cropname;

	/**
	 * 是否复合肥,由氮磷钾含量自动计算
	 */
	private int Compound;

	/**
	 * 底肥施用方法
	 */
	private String Fertilizers;

	/**
	 * 追肥施用方法
	 */
	private String Topdressing;

	
	/**
	 * 县编号
	 */
	private String Countyid;

	/**
	 * 县名称
	 */
	private String Countyname;
	
	/**
	 * null
	 */
	private String Cruser;

	/**
	 * null
	 */
	private Date Crtime;

	/**
	 * null
	 */
	private String Lmuser;

	/**
	 * null
	 */
	private Date Lmtime;

	/**
	 * null
	 */
	private long Syncmark;

	/**
	 * 肥料的用量信息
	 */
	private double dosage;
	/**
	 * 表:sn_fertilizer DTO构造方法
	 */
	public FertilizerInfo(){ 
	}



	
	/**
	 * 属性:fertilizerid getter器
	 */
	public String getFertilizerid(){
		return this.Fertilizerid;
	}
	
	
	/**
	 * 属性:fertilizerid setter器
	 */
	public void setFertilizerid(String val){
		this.Fertilizerid = val;
	}
	
	
	/**
	 * 属性:fertilizername getter器
	 */
	public String getFertilizername(){
		return this.Fertilizername;
	}
	
	
	/**
	 * 属性:fertilizername setter器
	 */
	public void setFertilizername(String val){
		this.Fertilizername = val;
	}
	
	
	/**
	 * 属性:shortname getter器
	 */
	public String getShortname(){
		return this.Shortname;
	}
	
	
	/**
	 * 属性:shortname setter器
	 */
	public void setShortname(String val){
		this.Shortname = val;
	}
	
	
	/**
	 * 属性:company getter器
	 */
	public String getCompany(){
		return this.Company;
	}
	
	
	/**
	 * 属性:company setter器
	 */
	public void setCompany(String val){
		this.Company = val;
	}
	
	
	/**
	 * 属性:N getter器
	 */
	public int getN(){
		return this.N;
	}
	
	
	/**
	 * 属性:N setter器
	 */
	public void setN(int val){
		this.N = val;
	}
	
	
	/**
	 * 属性:P getter器
	 */
	public int getP(){
		return this.P;
	}
	
	
	/**
	 * 属性:P setter器
	 */
	public void setP(int val){
		this.P = val;
	}
	
	
	/**
	 * 属性:K getter器
	 */
	public int getK(){
		return this.K;
	}
	
	
	/**
	 * 属性:K setter器
	 */
	public void setK(int val){
		this.K = val;
	}
	
	
	/**
	 * 属性:useratioN getter器
	 */
	public int getUseration(){
		return this.Useration;
	}
	
	
	/**
	 * 属性:useratioN setter器
	 */
	public void setUseration(int val){
		this.Useration = val;
	}
	
	
	/**
	 * 属性:useratioP getter器
	 */
	public int getUseratiop(){
		return this.Useratiop;
	}
	
	
	/**
	 * 属性:useratioP setter器
	 */
	public void setUseratiop(int val){
		this.Useratiop = val;
	}
	
	
	/**
	 * 属性:useratioK getter器
	 */
	public int getUseratiok(){
		return this.Useratiok;
	}
	
	
	/**
	 * 属性:useratioK setter器
	 */
	public void setUseratiok(int val){
		this.Useratiok = val;
	}
	
	
	/**
	 * 属性:recommend getter器
	 */
	public int getRecommend(){
		return this.Recommend;
	}
	
	
	/**
	 * 属性:recommend setter器
	 */
	public void setRecommend(int val){
		this.Recommend = val;
	}
	
	
	/**
	 * 属性:fertilizertype getter器
	 */
	public int getFertilizertype(){
		return this.Fertilizertype;
	}
	
	
	/**
	 * 属性:fertilizertype setter器
	 */
	public void setFertilizertype(int val){
		this.Fertilizertype = val;
	}
	
	
	/**
	 * 属性:dedicated getter器
	 */
	public int getDedicated(){
		return this.Dedicated;
	}
	
	
	/**
	 * 属性:dedicated setter器
	 */
	public void setDedicated(int val){
		this.Dedicated = val;
	}
	
	
	/**
	 * 属性:cropid getter器
	 */
	public String getCropid(){
		return this.Cropid;
	}
	
	
	/**
	 * 属性:cropid setter器
	 */
	public void setCropid(String val){
		this.Cropid = val;
	}
	
	
	/**
	 * 属性:cropname getter器
	 */
	public String getCropname(){
		return this.Cropname;
	}
	
	
	/**
	 * 属性:cropname setter器
	 */
	public void setCropname(String val){
		this.Cropname = val;
	}
	
	
	/**
	 * 属性:compound getter器
	 */
	public int getCompound(){
		return this.Compound;
	}
	
	
	/**
	 * 属性:compound setter器
	 */
	public void setCompound(int val){
		this.Compound = val;
	}
	
	
	/**
	 * 属性:fertilizers getter器
	 */
	public String getFertilizers(){
		return this.Fertilizers;
	}
	
	
	/**
	 * 属性:fertilizers setter器
	 */
	public void setFertilizers(String val){
		this.Fertilizers = val;
	}
	
	
	/**
	 * 属性:topdressing getter器
	 */
	public String getTopdressing(){
		return this.Topdressing;
	}
	
	
	/**
	 * 属性:topdressing setter器
	 */
	public void setTopdressing(String val){
		this.Topdressing = val;
	}
	
	public String getCountyid() {
		return Countyid;
	}
	public void setCountyid(String countyid) {
		Countyid = countyid;
	}
	public String getCountyname() {
		return Countyname;
	}
	public void setCountyname(String countyname) {
		Countyname = countyname;
	}
	
	
	/**
	 * 属性:cruser getter器
	 */
	public String getCruser(){
		return this.Cruser;
	}
	
	
	/**
	 * 属性:cruser setter器
	 */
	public void setCruser(String val){
		this.Cruser = val;
	}
	
	
	/**
	 * 属性:crtime getter器
	 */
	public Date getCrtime(){
		return this.Crtime;
	}
	
	
	/**
	 * 属性:crtime setter器
	 */
	public void setCrtime(Date val){
		this.Crtime = val;
	}
	
	
	/**
	 * 属性:lmuser getter器
	 */
	public String getLmuser(){
		return this.Lmuser;
	}
	
	
	/**
	 * 属性:lmuser setter器
	 */
	public void setLmuser(String val){
		this.Lmuser = val;
	}
	
	
	/**
	 * 属性:lmtime getter器
	 */
	public Date getLmtime(){
		return this.Lmtime;
	}
	
	
	/**
	 * 属性:lmtime setter器
	 */
	public void setLmtime(Date val){
		this.Lmtime = val;
	}
	
	
	/**
	 * 属性:syncmark getter器
	 */
	public long getSyncmark(){
		return this.Syncmark;
	}
	
	
	/**
	 * 属性:syncmark setter器
	 */
	public void setSyncmark(long val){
		this.Syncmark = val;
	}



	/**
	 * @return the dosage
	 */
	public double getDosage() {
		return dosage;
	}

	public String getFmtDosage() {
		DecimalFormat df=new DecimalFormat("0.0"); 
		return df.format(dosage);
	}


	/**
	 * @param dosage the dosage to set
	 */
	public void setDosage(double dosage) {
		this.dosage = dosage;
	}




	/**
	 * 本方法主要用于计算肥料养分数
	 * @return
	 */
	public int getNutrientCount() {
		// TODO Auto-generated method stub
		int count=0;
		if (this.P>0){
			count++;
		}
		if (this.N>0){
			count++;
		}
		if (this.K>0){
			count++;
		}
		return 0;
	}



	/**
	 * 本方法主要用于返回当前肥料是否已经参与了计算
	 * @return
	 */
	public boolean isCalculated() {
		// TODO Auto-generated method stub
		return (dosage!=0.0);
	}



	/**
	 * 本方法主要用于计算，是否包括N P K的养分信息
	 * @param nutrient
	 * @return
	 */
	public boolean contain(int nutrient) {
		// TODO Auto-generated method stub
		if (nutrient==NutrientType.N){
			if (this.getN()>0){
				return true;
			}
		}
		if (nutrient==NutrientType.P){
			if (this.getP()>0){
				return true;
			}
		}
		if (nutrient==NutrientType.K){
			if (this.getK()>0){
				return true;
			}
		}
		return false;
	}




    /**
     * 计算肥料的用量，给定决策养分类型
     * 施肥量小于25变为25，大于75变为75（望都）
     * @param nutrient int 养分类型
     * @param goaldosage double 目标养分量
     * @return double 计算后返回的肥料施用量
     * 
     */
    public double calculteDosage(int nutrient,double goaldosage) {
        double percent=getPercent(nutrient);
        if(percent>0 && goaldosage>0){
            double temp = (goaldosage / percent) * 100;
            
//            if(temp < 25)
//            	temp = 25;
//            else if(temp > 75)
//            	temp = 75;
            
            return temp;
        }
        return 0;
    }
    
    /**
     * 返回肥料中指定养分的含量
     * @param nutrient int
     * @return double
     */
    public double getPercent(int nutrient){
    	if (nutrient==NutrientType.N){
			return this.getN();
		}
		if (nutrient==NutrientType.P){
			return this.getP();
		}
		if (nutrient==NutrientType.K){
			return this.getK();
		}
        return 0;
    }
    
    /**
     * 计算肥料的用量，给定决策养分类型
     * @param nutrient int 养分类型
     * @param goaldosage double 目标养分量
     * @return double 计算后返回的肥料施用量
     */
    public double calculte(int nutrient,double goaldosage) {
        this.dosage=calculteDosage(nutrient,goaldosage);
        return this.dosage;
    }

    
    /**
     * 计算肥料的用量，给定决策养分类型
     * @param nutrient int 养分类型
     * @param goaldosage double 目标养分量
     * @return double 计算后返回的肥料施用量
     */
    public double calculteNotChange(int nutrient,double goaldosage) {
    	return calculteDosage(nutrient,goaldosage);
        //return this.dosage;
    }
    
    
    /**
     * 给定 N、P、K 的目标用量，返回决策当前肥料最大施用量养分 ID
     * @param nitrogen double N 素的目标用量
     * @param phosphorus double P2O5 的目标用量
     * @param kalium double K2O 的目标用量
     * @return int 决策施用量最大的养分 ID
     */
    public int calculteMaxDosage(double nitrogen, double phosphorus, double kalium) {
        double[] tempDosage=new double[3];
        tempDosage[0]=calculteDosage(NutrientType.N,nitrogen);
        tempDosage[1]=calculteDosage(NutrientType.P,phosphorus);
        tempDosage[2]=calculteDosage(NutrientType.K,kalium);

        double temp=0.0;
        for(int i=0;i<tempDosage.length;i++){
            if(tempDosage[i]>0 && tempDosage[i]>temp){
                temp=tempDosage[i];
            }
        }

        if(temp==tempDosage[0]){
            return NutrientType.N;
        }

        if(temp==tempDosage[1]){
            return NutrientType.P;
        }

        if(temp==tempDosage[2]){
            return NutrientType.K;
        }

        return -1;
    }
    

    /**
    *
    * 给定 N、P、K 的目标用量，返回决策当前肥料最小施用量养分 ID
    * @param nitrogen double N 素的目标用量
    * @param phosphorus double P2O5 的目标用量
    * @param kalium double K2O 的目标用量
    * @return int 决策施用量最小的养分 ID
    */
   public int calculteMinDosage(double nitrogen, double phosphorus, double kalium) {
       double[] tempDosage=new double[3];
       tempDosage[0]=calculteDosage(NutrientType.N,nitrogen);
       tempDosage[1]=calculteDosage(NutrientType.P,phosphorus);
       tempDosage[2]=calculteDosage(NutrientType.K,kalium);
       double a = ((tempDosage[0] > tempDosage[1]) && (tempDosage[1] != 0) || tempDosage[0] == 0) ? tempDosage[1] : tempDosage[0];
		double temp = ((a > tempDosage[2]) && (tempDosage[2] != 0) || a == 0) ? tempDosage[2] : a;
       if(temp==tempDosage[0]){
           return NutrientType.N;
       }

       if(temp==tempDosage[1]){
           return NutrientType.P;
       }

       if(temp==tempDosage[2]){
           return NutrientType.K;
       }

       return -1;
   }
}
