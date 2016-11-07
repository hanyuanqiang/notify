package com.app.model.sqfw;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_jdgl_company {

    private int id;
    private String legalPerson; //公司负责人
    private String licenseInfo; //公司营业执照
    private String phone;   //公司联系方式
    private String location;    //公司地址
    private int status; //0表示该信息还没有审核，1表示审核通过，2表示审核未通过

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLicenseInfo() {
        return licenseInfo;
    }

    public void setLicenseInfo(String licenseInfo) {
        this.licenseInfo = licenseInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
