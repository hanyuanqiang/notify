package com.app.model;

/**
 * Created by Administrator on 2016/6/26.
 */
public class User {

    private int id;
    private String name;
    private String phone;
    private String idCard;
    private String password;
    private String address;
    private String alias;
    private String headPicUrl;
    private int isDangYuan; //是否为党员，0表示非党员，1表示党员
    private String tag;     //对党员进行推送的tag

    public int getIsDangYuan() {
        return isDangYuan;
    }

    public void setIsDangYuan(int isDangYuan) {
        this.isDangYuan = isDangYuan;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
