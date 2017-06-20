package com.snsoft.ctpf.beans;

/**
 *  系统参数
 */
public class SysParam {
    private String area;    //地区，以县区为单位
    private String url;     //服务器地址
    private String phone;   //短信号码
    private boolean isJumpFer = false;//是否跳过肥料选择
    private boolean isAddFer = true; //是否追肥

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isJumpFer() {
        return isJumpFer;
    }

    public void setJumpFer(boolean jumpFer) {
        isJumpFer = jumpFer;
    }

    public boolean isAddFer() {
        return isAddFer;
    }

    public void setAddFer(boolean addFer) {
        isAddFer = addFer;
    }

    @Override
    public String toString() {
        return "SysParam{" +
                "area='" + area + '\'' +
                ", url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                ", isJumpFer=" + isJumpFer +
                ", isAddFer=" + isAddFer +
                '}';
    }
}
