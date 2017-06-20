package com.hxsn.ssklf.model;

/**
 *  Created by jiely on 2016/12/9.
 *  病虫害属性
 */
public class PestProperty {
    private int cropId;     //作物类型    0,黄瓜  1，番茄  2，莲藕
    private int seasonIndex;//季节茬下标
    private int phenophaseIndex;//物候期下标
    private int idIndex;    //病虫害下标
    private boolean isInfect;       //是否易感染
    private String description1,description2;//病虫害描述1，病虫害描述2；
    private String factor;//发病条件
    private String behavior;//症状表现
    private String method;//防治方法

    public PestProperty() {
    }

    public PestProperty(int cropId, int seasonIndex, int phenophaseIndex, int idIndex, boolean isInfect) {
        this.cropId = cropId;
        this.seasonIndex = seasonIndex;
        this.phenophaseIndex = phenophaseIndex;
        this.idIndex = idIndex;
        this.isInfect = isInfect;
    }

    public int getIdIndex() {
        return idIndex;
    }

    public void setIdIndex(int idIndex) {
        this.idIndex = idIndex;
    }

    public int getSeasonIndex() {
        return seasonIndex;
    }

    public void setSeasonIndex(int seasonIndex) {
        this.seasonIndex = seasonIndex;
    }

    public int getPhenophaseIndex() {
        return phenophaseIndex;
    }

    public void setPhenophaseIndex(int phenophaseIndex) {
        this.phenophaseIndex = phenophaseIndex;
    }

    public boolean isInfect() {
        return isInfect;
    }

    public void setInfect(boolean infect) {
        isInfect = infect;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    @Override
    public String toString() {
        return "PestProperty{" +
                "idIndex=" + idIndex +
                ", phenophaseIndex=" + phenophaseIndex +
                ", seasonIndex=" + seasonIndex +
                ", cropId=" + cropId +
                '}';
    }
}
