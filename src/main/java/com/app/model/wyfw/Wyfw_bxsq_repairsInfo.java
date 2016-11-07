package com.app.model.wyfw;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Wyfw_bxsq_repairsInfo {

    private int id;
    private String title;   //标题
    private String content;     //内容
    private String picUrl_beforeRepairs;    //报修之前的图片
    private String picUrl_afterRepairs;     //报修之后的图片
    private String repairsDate;     //预约的维修时间
    private String comment;     //报修后用户的评论
    private int isComment;       //记录该条报修信息是否被评价，0表示未评价，1表示已经评价
    private int userId;     //保修的用户的id
    private int star;       //用户给该次报修的评价星级
    private String userName;       //本次报修的用户名
    private int isRepairsFinish;    //表示维修是否已经完成,0表示还没维修，1表示已经维修完成
    private String phone;   //保修者的联系方式
    private String bxTime;  //用户报修的时间

    public String getBxTime() {
        return bxTime;
    }

    public void setBxTime(String bxTime) {
        this.bxTime = bxTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsRepairsFinish() {
        return isRepairsFinish;
    }

    public void setIsRepairsFinish(int isRepairsFinish) {
        this.isRepairsFinish = isRepairsFinish;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPicUrl_beforeRepairs() {
        return picUrl_beforeRepairs;
    }

    public void setPicUrl_beforeRepairs(String picUrl_beforeRepairs) {
        this.picUrl_beforeRepairs = picUrl_beforeRepairs;
    }

    public String getPicUrl_afterRepairs() {
        return picUrl_afterRepairs;
    }

    public void setPicUrl_afterRepairs(String picUrl_afterRepairs) {
        this.picUrl_afterRepairs = picUrl_afterRepairs;
    }

    public String getRepairsDate() {
        return repairsDate;
    }

    public void setRepairsDate(String repairsDate) {
        this.repairsDate = repairsDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
