package com.sxu.entity;

import java.io.Serializable;

public class EnterpriseOutletInfo{

    private String enterpriseName;
    private String outletName;

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


    public EnterpriseOutletInfo(String enterpriseName, String outletName) {
        this.enterpriseName = enterpriseName;
        this.outletName = outletName;
    }

    public EnterpriseOutletInfo() {

    }

    @Override
    public String toString() {
        return "EnterpriseOutletInfo{" +
                "enterpriseName='" + enterpriseName + '\'' +
                ", outletName='" + outletName + '\'' +
                '}';
    }
}
