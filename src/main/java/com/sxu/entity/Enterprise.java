package com.sxu.entity;

public class Enterprise {
    private String name;
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Enterprise(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Enterprise() {
    }

    @Override
    public String toString() {
        return "Enterprise [id=" + name + ", location=" + location + "]";
    }
}
