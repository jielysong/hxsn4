package com.hxsn.ssklf.model;

/**
 * 各参数的阈值范围
 *  Created by jiely on 2016/12/20.
 *  	"upTemp":15,
 "downTemp":15,
 "upHumidity":45,
 "downHumidity":45,
 "upTemp15cm":14,
 "downTemp15cm":14,
 "upSunlight":600,
 "downSunlight":600,
 */
public class Threshold {
    /**
     * 空气温度上限
     */
    private int upTemp;
    /**
     * 空气温度下限
     */
    private int downTemp;
    /**
     * 空气湿度上限
     */
    private int upHumidity;
    /**
     * 空气湿度下限
     */
    private int downHumidity;
    /**
     * 土壤温度上限
     */
    private int upTemp15cm;
    /**
     * 土壤温度下限
     */
    private int downTemp15cm;
    /**
     * 光照上限
     */
    private int upSunlight;
    /**
     * 光照下限
     */
    private int downSunlight;


    public int getUpTemp() {
        return upTemp;
    }

    public void setUpTemp(int upTemp) {
        this.upTemp = upTemp;
    }

    public int getDownTemp() {
        return downTemp;
    }

    public void setDownTemp(int downTemp) {
        this.downTemp = downTemp;
    }

    public int getUpHumidity() {
        return upHumidity;
    }

    public void setUpHumidity(int upHumidity) {
        this.upHumidity = upHumidity;
    }

    public int getDownHumidity() {
        return downHumidity;
    }

    public void setDownHumidity(int downHumidity) {
        this.downHumidity = downHumidity;
    }

    public int getUpTemp15cm() {
        return upTemp15cm;
    }

    public void setUpTemp15cm(int upTemp15cm) {
        this.upTemp15cm = upTemp15cm;
    }

    public int getDownTemp15cm() {
        return downTemp15cm;
    }

    public void setDownTemp15cm(int downTemp15cm) {
        this.downTemp15cm = downTemp15cm;
    }

    public int getUpSunlight() {
        return upSunlight;
    }

    public void setUpSunlight(int upSunlight) {
        this.upSunlight = upSunlight;
    }

    public int getDownSunlight() {
        return downSunlight;
    }

    public void setDownSunlight(int downSunlight) {
        this.downSunlight = downSunlight;
    }
}
