package com.sxu.entity;

import java.io.Serializable;

public class EnterpriseOutletInfo implements Serializable {

    private String enterpriseName;
    private String outletName;
    private String createDate = String.valueOf(System.currentTimeMillis());

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public EnterpriseOutletInfo(String enterpriseName, String outletName, String createDate) {
        this.enterpriseName = enterpriseName;
        this.outletName = outletName;
        this.createDate = createDate;
    }

    public EnterpriseOutletInfo() {

    }

    @Override
    public String toString() {
        return "EnterpriseOutletInfo{" +
                "enterpriseName='" + enterpriseName + '\'' +
                ", outletName='" + outletName + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
