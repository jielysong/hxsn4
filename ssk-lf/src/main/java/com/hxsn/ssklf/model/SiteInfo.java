package com.hxsn.ssklf.model;

/**
 * Created by jiely on 2016/12/20.
 * 气象哨的信息，包括名称和UUID
 */
public class SiteInfo {
    private String name;
    private String uuid;

    public SiteInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SiteInfo(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
