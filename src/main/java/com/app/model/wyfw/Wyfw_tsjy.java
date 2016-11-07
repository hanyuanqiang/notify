package com.app.model.wyfw;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Wyfw_tsjy {

    private int id;
    private int userId;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String complainContent;
    private String complainDate;
    private String wuyeReplay;
    private String shequReplay;
    private int isWuyePush;
    private int isShequPush;

    public int getIsWuyePush() {
        return isWuyePush;
    }

    public void setIsWuyePush(int isWuyePush) {
        this.isWuyePush = isWuyePush;
    }

    public int getIsShequPush() {
        return isShequPush;
    }

    public void setIsShequPush(int isShequPush) {
        this.isShequPush = isShequPush;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComplainContent() {
        return complainContent;
    }

    public void setComplainContent(String complainContent) {
        this.complainContent = complainContent;
    }

    public String getComplainDate() {
        return complainDate;
    }

    public void setComplainDate(String complainDate) {
        this.complainDate = complainDate;
    }

    public String getWuyeReplay() {
        return wuyeReplay;
    }

    public void setWuyeReplay(String wuyeReplay) {
        this.wuyeReplay = wuyeReplay;
    }

    public String getShequReplay() {
        return shequReplay;
    }

    public void setShequReplay(String shequReplay) {
        this.shequReplay = shequReplay;
    }
}
