package com.app.model.sqfw;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_jdgl_merchant {
    private int id;
    private String name;
    private String licenseInfo;

    public String getLicenseInfo() {
        return licenseInfo;
    }

    public void setLicenseInfo(String licenseInfo) {
        this.licenseInfo = licenseInfo;
    }

    private String principal;
    private String location;
    private int status; //0表示该信息还没有审核，1表示审核通过，2表示审核未通过

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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
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
