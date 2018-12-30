package com.sxu.entity;

public class DischargeAmount {
    Float feasibleNoxDischargeAmount;
    Float feasibleSo2DischargeAmount;

    public Float getFeasibleNoxDischargeAmount() {
        return feasibleNoxDischargeAmount;
    }

    public void setFeasibleNoxDischargeAmount(Float feasibleNoxDischargeAmount) {
        this.feasibleNoxDischargeAmount = feasibleNoxDischargeAmount;
    }

    public Float getFeasibleSo2DischargeAmount() {
        return feasibleSo2DischargeAmount;
    }

    public void setFeasibleSo2DischargeAmount(Float feasibleSo2DischargeAmount) {
        this.feasibleSo2DischargeAmount = feasibleSo2DischargeAmount;
    }

    public DischargeAmount(Float feasibleNoxDischargeAmount, Float feasibleSo2DischargeAmount) {
        this.feasibleNoxDischargeAmount = feasibleNoxDischargeAmount;
        this.feasibleSo2DischargeAmount = feasibleSo2DischargeAmount;
    }

    public DischargeAmount() {

    }

    @Override
    public String toString() {
        return "DischargeAmount{" +
                "feasibleNoxDischargeAmount=" + feasibleNoxDischargeAmount +
                ", feasibleSo2DischargeAmount=" + feasibleSo2DischargeAmount +
                '}';
    }
}
