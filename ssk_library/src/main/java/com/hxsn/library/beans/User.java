package com.hxsn.library.beans;

/**
 * "username":"jiwl",	//用户名
 * "realname":"",	//姓名
 * "nickname":"",	//昵称
 * "email":"",	//Email地址
 * "phone":"",	//手机号码
 * "address":""	//地址
 */
public class User {
    private String userId;
    private String userName;
    private String password;
    private String realName;//昵称
    private String nickName;//昵称
    private String code;
    private String address;
    private String sex;  //性别
    private String headUrl;//头像的url地址
    private String email;
    private String phone;
    private String status;
    private String controlPwd;

    public String getControlPwd() {
        return controlPwd;
    }

    public void setControlPwd(String controlPwd) {
        this.controlPwd = controlPwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void clear(){
        this.userId = "";
        this.userName = "";
        this.password = "";
        this.realName = "";//昵称
        this.nickName = "";//昵称
        this.code = "";
        this.address = "";
        this.sex = "";  //性别
        this.headUrl = "";//头像的url地址
        this.email = "";
        this.phone = "";
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", controlPwd='" + controlPwd + '\'' +
                '}';
    }
}
