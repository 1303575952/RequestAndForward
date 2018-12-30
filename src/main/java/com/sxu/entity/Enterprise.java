package com.sxu.entity;

public class Enterprise {
    private String name;
    private String industry;
    private Location location;
    private Integer cellId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
    }

    public Enterprise(String name, String industry, Location location, Integer cellId) {
        this.name = name;
        this.industry = industry;
        this.location = location;
        this.cellId = cellId;
    }

    public Enterprise() {

    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                ", location=" + location +
                ", cellId=" + cellId +
                '}';
    }
}
