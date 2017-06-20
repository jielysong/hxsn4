package com.snsoft.ctpf.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * @author Administrator
 * 描述:作物基本信息表
 */
public class CropInfo implements Parcelable{

	/**
	 * null
	 */
	private String cropid;

	/**
	 * null
	 */
	private String cropname;

	/**
	 * null
	 */
	private String recommendedvarieties;

	/**
	 * 作物的相关系数参数等是否完成,0-资料不完整，1-资料完整
	 */
	private String cropstate;

	/**
	 * null
	 */
	private int lowerlimit;

	/**
	 * null
	 */
	private int toplimit;

	/**
	 * 作物施肥方案
	 */
	private int scheme;
	
	/**
	 * null
	 */
	private int divide;

	/**
	 * null
	 */
	private String fertilizers;
	
	/**
	 * 有机肥施用建议
	 */
	private String otherfertilizers;
	
	/**
	 * 用户选择的目标产量
	 */
	private int yield;

	/**
	 * null
	 */
	private String topdressing;

	/**
	 * 排序值
	 */
	private String sortvalue;
	
	/**
	 * 商品名
	 */
	private String goodsname;
	
	/**
	 * 微肥施用建议
	 */
	private String micro;

	
	/**
	 * 县编号
	 */
	private String countyid;

	/**
	 * 县名称
	 */
	private String countyname;
	
	
	/**
	 * null
	 */
	private String cruser;

	/**
	 * null
	 */
	private Date crtime;

	/**
	 * null
	 */
	private String lmuser;

	/**
	 * null
	 */
	private Date lmtime;

	/**
	 * null
	 */
	private long syncmark;


	/**
	 * 表:sn_crop DTO构造方法
	 */
	public CropInfo(){ 
	}
	
	/**
	 * 属性:cropid getter器
	 */
	public String getCropid(){
		return this.cropid;
	}
	
	
	/**
	 * 属性:cropid setter器
	 */
	public void setCropid(String val){
		this.cropid = val;
	}
	
	
	/**
	 * 属性:cropname getter器
	 */
	public String getCropname(){
		return this.cropname;
	}
	
	
	/**
	 * 属性:cropname setter器
	 */
	public void setCropname(String val){
		this.cropname = val;
	}
	
	
	/**
	 * @return the otherfertilizers
	 */
	public String getOtherfertilizers() {
		return otherfertilizers;
	}

	/**
	 * @param otherfertilizers the otherfertilizers to set
	 */
	public void setOtherfertilizers(String otherfertilizers) {
		otherfertilizers = otherfertilizers;
	}

	/**
	 * 属性:recommendedvarieties getter器
	 */
	public String getRecommendedvarieties(){
		return this.recommendedvarieties;
	}
	
	
	/**
	 * 属性:recommendedvarieties setter器
	 */
	public void setRecommendedvarieties(String val){
		this.recommendedvarieties = val;
	}
	
	
	/**
	 * 属性:cropstate getter器
	 */
	public String getCropstate(){
		return this.cropstate;
	}
	
	
	/**
	 * 属性:cropstate setter器
	 */
	public void setCropstate(String val){
		this.cropstate = val;
	}
	
	
	/**
	 * 属性:lowerlimit getter器
	 */
	public int getLowerlimit(){
		return this.lowerlimit;
	}
	
	
	/**
	 * 属性:lowerlimit setter器
	 */
	public void setLowerlimit(int val){
		this.lowerlimit = val;
	}
	
	
	/**
	 * 属性:toplimit getter器
	 */
	public int getToplimit(){
		return this.toplimit;
	}
	
	
	/**
	 * 属性:toplimit setter器
	 */
	public void setToplimit(int val){
		this.toplimit = val;
	}
	
	
	/**
	 * 属性:divide getter器
	 */
	public int getDivide(){
		return this.divide;
	}
	
	
	/**
	 * 属性:divide setter器
	 */
	public void setDivide(int val){
		this.divide = val;
	}
	
	
	/**
	 * 属性:fertilizers getter器
	 */
	public String getFertilizers(){
		return this.fertilizers;
	}
	
	
	/**
	 * 属性:fertilizers setter器
	 */
	public void setFertilizers(String val){
		this.fertilizers = val;
	}
	
	
	/**
	 * 属性:topdressing getter器
	 */
	public String getTopdressing(){
		return this.topdressing;
	}
	
	
	/**
	 * 属性:topdressing setter器
	 */
	public void setTopdressing(String val){
		this.topdressing = val;
	}
	
	
	/**
	 * 属性:micro getter器
	 */
	public String getMicro(){
		return this.micro;
	}
	
	
	/**
	 * 属性:micro setter器
	 */
	public void setMicro(String val){
		this.micro = val;
	}
	
	public String getCountyid() {
		return countyid;
	}
	public void setCountyid(String countyid) {
		countyid = countyid;
	}
	public String getCountyname() {
		return countyname;
	}
	public void setCountyname(String countyname) {
		countyname = countyname;
	}
	
	/**
	 * 属性:cruser getter器
	 */
	public String getCruser(){
		return this.cruser;
	}
	
	
	/**
	 * 属性:cruser setter器
	 */
	public void setCruser(String val){
		this.cruser = val;
	}
	
	
	/**
	 * 属性:crtime getter器
	 */
	public Date getCrtime(){
		return this.crtime;
	}
	
	
	/**
	 * 属性:crtime setter器
	 */
	public void setCrtime(Date val){
		this.crtime = val;
	}
	
	
	/**
	 * 属性:lmuser getter器
	 */
	public String getLmuser(){
		return this.lmuser;
	}
	
	
	/**
	 * 属性:lmuser setter器
	 */
	public void setLmuser(String val){
		this.lmuser = val;
	}
	
	
	/**
	 * 属性:lmtime getter器
	 */
	public Date getLmtime(){
		return this.lmtime;
	}
	
	
	/**
	 * 属性:lmtime setter器
	 */
	public void setLmtime(Date val){
		this.lmtime = val;
	}
	
	
	/**
	 * 属性:syncmark getter器
	 */
	public long getSyncmark(){
		return this.syncmark;
	}
	
	
	/**
	 * 属性:syncmark setter器
	 */
	public void setSyncmark(long val){
		this.syncmark = val;
	}




	/**
	 * @return the yield
	 */
	public int getYield() {
		return yield;
	}




	/**
	 * @param yield the yield to set
	 */
	public void setYield(int yield) {
		this.yield = yield;
	}

	public String getSortvalue() {
		return sortvalue;
	}

	public void setSortvalue(String sortvalue) {
		this.sortvalue = sortvalue;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	/**
	 * @return the scheme
	 */
	public int getScheme() {
		return scheme;
	}

	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme(int scheme) {
		this.scheme = scheme;
	}
	
	/**
	 * 根据施肥方案序号0 1 2 3来获取得到是否此作物拥有此方案
	 * 0  公共配方肥
	 * 1 县级配方肥
	 * 2 自选肥料
	 * 3 自动配方肥
	 */
	public boolean getSchemeByBit(int i) {

		return (scheme & (int) Math.pow(2, i)) > 0;
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i){
		parcel.writeString(countyid);
		parcel.writeString(countyname);
		parcel.writeString(cropid);
		parcel.writeString(cropname);
		parcel.writeString(cropstate);
	}
	
	public static final Creator<CropInfo> CREATOR = new Creator<CropInfo>(){

		@Override
		public CropInfo createFromParcel(Parcel parcel)
		{
			CropInfo cropInfo = new CropInfo();
			cropInfo.countyid = parcel.readString();
			cropInfo.countyname = parcel.readString();
			cropInfo.cropid = parcel.readString();
			cropInfo.cropname = parcel.readString();
			cropInfo.cropstate = parcel.readString();
			
			return cropInfo;
		}

		@Override
		public CropInfo[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new CropInfo[size];
		}
		
	};
}
