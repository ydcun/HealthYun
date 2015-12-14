package com.ydcun.entity;

import java.util.Map;

public class YearStep {
	public Long dua_id;
	public Integer year;
	public Map<String,Long> data;
	public Long getDua_id() {
		return dua_id;
	}
	public void setDua_id(Long dua_id) {
		this.dua_id = dua_id;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Map<String, Long> getData() {
		return data;
	}
	public void setData(Map<String, Long> data) {
		this.data = data;
	}

}
