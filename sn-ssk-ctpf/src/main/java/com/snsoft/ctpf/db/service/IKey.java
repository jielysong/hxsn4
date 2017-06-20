package com.snsoft.ctpf.db.service;

public interface IKey{

	/** 查询Build*/
	public static final String CHAXUN_BUNDLE_KEY = "yangfen_key";
	
	/** 已知养分key*/
	public static final int YANGFEN_DATA = 0x001;
	/** 地图key*/
	public static final int DITU_DATA = 0x002;
	/** 二维码key*/
	public static final int ERWEIMA_DATA = 0x003;
	/** 行政区域key*/
	public static final int QUYU_DATA = 0x004;
	/** 姓名key*/
	public static final int FARMER_DATA = 0x005;
	
	/** 获取土壤养分*/
	public static final String SINFO_GET = "sinfo_get_key";
	/** 获取建议卡*/
	public static final String CARD_GET = "card_get_key";
	
	
	public static final int CITY_REQUEST_CODE = 0x101;
}
