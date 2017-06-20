package com.hxsn.library.beans;

/**
 * desc:推送通知消息
 * "title":"农情站",
 "description":"有新鲜水蜜桃出售，价格优惠",
 "notification_builder_id":0,
 "notification_basic_style":7,
 "open_type":2,	//自定义打开方式
 "custom_content":{
 "type":1,	//消息类型：1表示农情站资讯信息
 "id":"40288ca1548020210154802269e30001"	//资讯ID
 */
public class NotifyInfo {
    private String id;
    private int type;
    private int openType;
    private String description;
    private String title;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "NotifyInfo{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", openType=" + openType +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
