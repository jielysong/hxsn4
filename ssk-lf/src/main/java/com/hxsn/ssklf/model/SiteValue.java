package com.hxsn.ssklf.model;

/**
 *  Created by jiely on 2016/12/13.
 *  气象站数据值
 */
public class SiteValue {
    private String timeInfo;//时间参数 小时或周
    private double temperature;
    private double humidity;
    private double soilTemp;
    private double illu;

    public SiteValue() {
    }

    public SiteValue(String timeInfo, double temperature, int humidity, double soilTemp, int illu) {
        this.timeInfo = timeInfo;
        this.temperature = temperature;
        this.humidity = humidity;
        this.soilTemp = soilTemp;
        this.illu = illu;
    }

    public SiteValue(double temperature, int humidity, double soilTemp, int illu) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.soilTemp = soilTemp;
        this.illu = illu;
    }

    public String getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(String timeInfo) {
        this.timeInfo = timeInfo;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSoilTemp() {
        return soilTemp;
    }

    public void setSoilTemp(double soilTemp) {
        this.soilTemp = soilTemp;
    }

    public double getIllu() {
        return illu;
    }

    public void setIllu(double illu) {
        this.illu = illu;
    }

    @Override
    public String toString() {
        return "SiteValue{" +
                "timeInfo='" + timeInfo + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", soilTemp=" + soilTemp +
                ", illu=" + illu +
                '}';
    }
}
