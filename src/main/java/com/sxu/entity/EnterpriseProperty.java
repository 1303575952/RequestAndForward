package com.sxu.entity;

public class EnterpriseProperty {
    private String industry;
    private Integer cellId;

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public EnterpriseProperty(String industry, Integer cellId) {
        this.industry = industry;
        this.cellId = cellId;
    }
    public EnterpriseProperty(){

    }

    @Override
    public String toString() {
        return "EnterpriseProperty{" +
                "industry='" + industry + '\'' +
                ", cellId=" + cellId +
                '}';
    }
}
