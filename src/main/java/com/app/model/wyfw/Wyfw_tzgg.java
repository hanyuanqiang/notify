package com.app.model.wyfw;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Wyfw_tzgg {

    private int id;
    private String title;
    private String content;
    private String picUrl;
    private String publishDate;
    private int isPush;     //0表示该通知还没有被推送过，1表示该通知已经被推送过了

    public int getIsPush() {
        return isPush;
    }

    public void setIsPush(int isPush) {
        this.isPush = isPush;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
