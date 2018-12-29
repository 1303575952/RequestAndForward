package com.sxu.entity;

public class Location {
	private Float lon;
	private Float lat;

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Location(Float lon, Float lat) {
		super();
		this.lon = lon;
		this.lat = lat;
	}

	public Location() {
	}

	@Override
	public String toString() {
		return "Location [lon=" + lon + ", lat=" + lat + "]";
	}

}
