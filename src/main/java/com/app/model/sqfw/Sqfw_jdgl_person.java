package com.app.model.sqfw;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_jdgl_person {

    private int id;
    private String idCardInfo;
    private String phone;
    private String workContent;
    private int status; //0表示该信息还没有审核，1表示审核通过，2表示审核未通过
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCardInfo() {
        return idCardInfo;
    }

    public void setIdCardInfo(String idCardInfo) {
        this.idCardInfo = idCardInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
