package com.app.model.djfw;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Djfw_fcdjyg {

    private int id;
    private String title;
    private String content;
    private String type;
    private String picUrl;
    private String publishDate;

    private String whoLook; //设置该条信息谁可见，0表示所有人可见，1表示党员可见，2表示指定用户可见

    private int userId; //如果该信息只对某个用户可见时起作用

    private String userName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWhoLook() {
        return whoLook;
    }

    public void setWhoLook(String whoLook) {
        this.whoLook = whoLook;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
