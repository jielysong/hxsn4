package com.snsoft.ctpf.beans;

import java.io.Serializable;
import java.util.List;

public class HistoryNote implements Serializable{
	private int id;
	private String mName;
	private String mhistory;


	private String mYield;
	private List<String> mDFOne;
	private List<String> mZFOne;
	private List<String> mDFTwo;
	private List<String> mZFTwo;
	private String mFertilizers;
	private String mTopdressing;
	private String mMicro;
	private String mCountry;
	private String mTime;
	private double o;
	private double n;
	private double k;
	private double p;
	
	public HistoryNote() {
		super();
	}

	public String getMhistory() {
		return mhistory;
	}

	public void setMhistory(String mhistory) {
		this.mhistory = mhistory;
	}
	
	public HistoryNote(int id, String mName, String mYield,
					   List<String> mDFOne, List<String> mZFOne, List<String> mDFTwo,
					   List<String> mZFTwo, String mFertilizers, String mTopdressing,
					   String mMicro, String mCountry, String mTime) {
		super();
		this.id = id;
		this.mName = mName;
		this.mYield = mYield;
		this.mDFOne = mDFOne;
		this.mZFOne = mZFOne;
		this.mDFTwo = mDFTwo;
		this.mZFTwo = mZFTwo;
		this.mFertilizers = mFertilizers;
		this.mTopdressing = mTopdressing;
		this.mMicro = mMicro;
		this.mCountry = mCountry;
		this.mTime = mTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmYield() {
		return mYield;
	}

	public void setmYield(String mYield) {
		this.mYield = mYield;
	}

	public List<String> getmDFOne() {
		return mDFOne;
	}

	public void setmDFOne(List<String> mDFOne) {
		this.mDFOne = mDFOne;
	}

	public List<String> getmZFOne() {
		return mZFOne;
	}

	public void setmZFOne(List<String> mZFOne) {
		this.mZFOne = mZFOne;
	}

	public List<String> getmDFTwo() {
		return mDFTwo;
	}

	public void setmDFTwo(List<String> mDFTwo) {
		this.mDFTwo = mDFTwo;
	}

	public List<String> getmZFTwo() {
		return mZFTwo;
	}

	public void setmZFTwo(List<String> mZFTwo) {
		this.mZFTwo = mZFTwo;
	}

	public String getmFertilizers() {
		return mFertilizers;
	}

	public void setmFertilizers(String mFertilizers) {
		this.mFertilizers = mFertilizers;
	}

	public String getmTopdressing() {
		return mTopdressing;
	}

	public void setmTopdressing(String mTopdressing) {
		this.mTopdressing = mTopdressing;
	}

	public String getmMicro() {
		return mMicro;
	}

	public void setmMicro(String mMicro) {
		this.mMicro = mMicro;
	}

	public String getmCountry() {
		return mCountry;
	}

	public void setmCountry(String mCountry) {
		this.mCountry = mCountry;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	@Override
	public String toString() {
		return "HistoryNote [id=" + id + ", mName=" + mName + ", mYield="
				+ mYield + ", mDFOne=" + mDFOne + ", mZFOne=" + mZFOne
				+ ", mDFTwo=" + mDFTwo + ", mZFTwo=" + mZFTwo
				+ ", mFertilizers=" + mFertilizers + ", mTopdressing="
				+ mTopdressing + ", mMicro=" + mMicro + ", mCountry="
				+ mCountry + ", mTime=" + mTime + ", k="+k+", o="+o+", n="+n+", p="+p+"]"  ;
	}

	public double getO() {
		return o;
	}

	public void setO(double o) {
		this.o = o;
	}

	public double getN() {
		return n;
	}

	public void setN(double n) {
		this.n = n;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

}
