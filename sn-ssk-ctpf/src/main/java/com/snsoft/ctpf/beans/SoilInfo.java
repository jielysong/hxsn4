package com.snsoft.ctpf.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * 土壤养分资料信息
 * @author malq  Feb 13, 2012 2:43:54 PM
 *
 */
public class SoilInfo implements Parcelable{
		
	/**
	 * 信息编号
	 */
	private String Infoid;

	/**
	 * 统一编号
	 */
	private String Code;

	/**
	 * 省(市)名称
	 */
	private String Province;

	/**
	 * 地(市)名称
	 */
	private String City;

	/**
	 * 县(旗)名称
	 */
	private String County;

	/**
	 * 乡(镇)名称
	 */
	private String Town;

	/**
	 * 村名称
	 */
	private String Village;

	/**
	 * 别名(用于区分合并区)
	 */
	private String alias;
	
	/**
	 * 邮政编码
	 */
	private String Zipcode;

	/**
	 * 农户名称
	 */
	private String Farmer;

	/**
	 * 地块位置
	 */
	private String Position;

	/**
	 * 距村距离
	 */
	private int Distance;

	/**
	 * 北纬
	 */
	private double Latitude;

	/**
	 * 东经
	 */
	private double Longitude;

	/**
	 * 地貌类型
	 */
	private String Landform;

	/**
	 * 土类
	 */
	private String Soiltype;

	/**
	 * 有机质1
	 */
	private double Organic;

	/**
	 * 碱解氮1
	 */
	private double N;

	/**
	 * 有效磷1
	 */
	private double P;

	/**
	 * 速效钾1
	 */
	private double K;

	/**
	 * 有效铁1
	 */
	private double Fe;

	/**
	 * 有效锰1
	 */
	private double Mn;

	/**
	 * 有效铜1
	 */
	private double Cu;

	/**
	 * 有效锌1
	 */
	private double Zn;

	/**
	 * 水溶态硼1
	 */
	private double B;

	/**
	 * 有效钼1
	 */
	private double Mo;

	/**
	 * 有效硫1
	 */
	private double S;

	/**
	 * 有效硅1
	 */
	private double Si;
	
	/**
	 * 采样日期
	 */
	private Date Samplingdate;

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


	
	public SoilInfo(){ 
	}


	@Override
	public String toString() {
		return "SoilInfo{" +
				"Infoid='" + Infoid + '\'' +
				", Code='" + Code + '\'' +
				", Province='" + Province + '\'' +
				", City='" + City + '\'' +
				", County='" + County + '\'' +
				", Town='" + Town + '\'' +
				", Village='" + Village + '\'' +
				", alias='" + alias + '\'' +
				", Zipcode='" + Zipcode + '\'' +
				", Farmer='" + Farmer + '\'' +
				", Position='" + Position + '\'' +
				", Distance=" + Distance +
				", Latitude=" + Latitude +
				", Longitude=" + Longitude +
				", Landform='" + Landform + '\'' +
				", Soiltype='" + Soiltype + '\'' +
				'}';
	}

	/**
	 * @return the infoid
	 */
	public String getInfoid() {
		return Infoid;
	}



	/**
	 * @param infoid the infoid to set
	 */
	public void setInfoid(String infoid) {
		Infoid = infoid;
	}



	/**
	 * @return the code
	 */
	public String getCode() {
		return Code;
	}



	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		Code = code;
	}



	/**
	 * @return the province
	 */
	public String getProvince() {
		return Province;
	}



	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		Province = province;
	}



	/**
	 * @return the city
	 */
	public String getCity() {
		return City;
	}



	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		City = city;
	}



	/**
	 * @return the county
	 */
	public String getCounty() {
		return County;
	}



	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		County = county;
	}



	/**
	 * @return the town
	 */
	public String getTown() {
		return Town;
	}



	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		Town = town;
	}



	/**
	 * @return the village
	 */
	public String getVillage() {
		return Village;
	}



	/**
	 * @param village the village to set
	 */
	public void setVillage(String village) {
		Village = village;
	}



	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return Zipcode;
	}



	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		Zipcode = zipcode;
	}



	/**
	 * @return the farmer
	 */
	public String getFarmer() {
		return Farmer;
	}



	/**
	 * @param farmer the farmer to set
	 */
	public void setFarmer(String farmer) {
		Farmer = farmer;
	}



	/**
	 * @return the position
	 */
	public String getPosition() {
		return Position;
	}



	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		Position = position;
	}



	/**
	 * @return the distance
	 */
	public int getDistance() {
		return Distance;
	}



	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		Distance = distance;
	}



	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return Latitude;
	}



	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}



	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return Longitude;
	}



	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}



	/**
	 * @return the landform
	 */
	public String getLandform() {
		return Landform;
	}



	/**
	 * @param landform the landform to set
	 */
	public void setLandform(String landform) {
		Landform = landform;
	}



	/**
	 * @return the soiltype
	 */
	public String getSoiltype() {
		return Soiltype;
	}



	/**
	 * @param soiltype the soiltype to set
	 */
	public void setSoiltype(String soiltype) {
		Soiltype = soiltype;
	}



	/**
	 * @return the organic
	 */
	public double getOrganic() {
		return Organic;
	}



	/**
	 * @param organic the organic to set
	 */
	public void setOrganic(double organic) {
		Organic = organic;
	}



	/**
	 * @return the n
	 */
	public double getN() {
		return N;
	}



	/**
	 * @param n the n to set
	 */
	public void setN(double n) {
		N = n;
	}



	/**
	 * @return the p
	 */
	public double getP() {
		return P;
	}



	/**
	 * @param p the p to set
	 */
	public void setP(double p) {
		P = p;
	}



	/**
	 * @return the k
	 */
	public double getK() {
		return K;
	}



	/**
	 * @param k the k to set
	 */
	public void setK(double k) {
		K = k;
	}



	/**
	 * @return the fe
	 */
	public double getFe() {
		return Fe;
	}



	/**
	 * @param fe the fe to set
	 */
	public void setFe(double fe) {
		Fe = fe;
	}



	/**
	 * @return the mn
	 */
	public double getMn() {
		return Mn;
	}



	/**
	 * @param mn the mn to set
	 */
	public void setMn(double mn) {
		Mn = mn;
	}



	/**
	 * @return the cu
	 */
	public double getCu() {
		return Cu;
	}



	/**
	 * @param cu the cu to set
	 */
	public void setCu(double cu) {
		Cu = cu;
	}



	/**
	 * @return the zn
	 */
	public double getZn() {
		return Zn;
	}



	/**
	 * @param zn the zn to set
	 */
	public void setZn(double zn) {
		Zn = zn;
	}



	/**
	 * @return the b
	 */
	public double getB() {
		return B;
	}



	/**
	 * @param b the b to set
	 */
	public void setB(double b) {
		B = b;
	}



	/**
	 * @return the mo
	 */
	public double getMo() {
		return Mo;
	}



	/**
	 * @param mo the mo to set
	 */
	public void setMo(double mo) {
		Mo = mo;
	}



	/**
	 * @return the s
	 */
	public double getS() {
		return S;
	}



	/**
	 * @param s the s to set
	 */
	public void setS(double s) {
		S = s;
	}



	/**
	 * @return the si
	 */
	public double getSi() {
		return Si;
	}



	/**
	 * @param si the si to set
	 */
	public void setSi(double si) {
		Si = si;
	}



	/**
	 * @return the samplingdate
	 */
	public Date getSamplingdate() {
		return Samplingdate;
	}



	/**
	 * @param samplingdate the samplingdate to set
	 */
	public void setSamplingdate(Date samplingdate) {
		Samplingdate = samplingdate;
	}



	/**
	 * @return the countyname
	 */
	public String getCountyname() {
		return Countyname;
	}



	/**
	 * @param countyname the countyname to set
	 */
	public void setCountyname(String countyname) {
		Countyname = countyname;
	}



	/**
	 * @return the cruser
	 */
	public String getCruser() {
		return Cruser;
	}



	/**
	 * @param cruser the cruser to set
	 */
	public void setCruser(String cruser) {
		Cruser = cruser;
	}



	/**
	 * @return the crtime
	 */
	public Date getCrtime() {
		return Crtime;
	}



	/**
	 * @param crtime the crtime to set
	 */
	public void setCrtime(Date crtime) {
		Crtime = crtime;
	}



	/**
	 * @return the lmtime
	 */
	public Date getLmtime() {
		return Lmtime;
	}



	/**
	 * @param lmtime the lmtime to set
	 */
	public void setLmtime(Date lmtime) {
		Lmtime = lmtime;
	}



	/**
	 * @return the syncmark
	 */
	public long getSyncmark() {
		return Syncmark;
	}



	/**
	 * @param syncmark the syncmark to set
	 */
	public void setSyncmark(long syncmark) {
		Syncmark = syncmark;
	}



	public String getAlias() {
		return alias;
	}



	public void setAlias(String alias) {
		this.alias = alias;
	}



	@Override
	public int describeContents(){
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeDouble(N);
		dest.writeDouble(P);
		dest.writeDouble(K);
		dest.writeDouble(Organic);
		dest.writeDouble(Latitude);
		dest.writeDouble(Longitude);
	}
	
	public static final Creator<SoilInfo> CREATOR = new Creator<SoilInfo>(){

		@Override
		public SoilInfo createFromParcel(Parcel parcel)
		{
			SoilInfo soilInfo = new SoilInfo();
			soilInfo.N = parcel.readDouble();
			soilInfo.P = parcel.readDouble();
			soilInfo.K = parcel.readDouble();
			soilInfo.Organic = parcel.readDouble();
			soilInfo.Latitude = parcel.readDouble();
			soilInfo.Longitude = parcel.readDouble();
			
			return soilInfo;
		}

		@Override
		public SoilInfo[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new SoilInfo[size];
		}
		
	};

}
