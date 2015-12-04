package com.ydcun.entity;

import java.util.List;
import java.util.Map;

/**
 * step upload
 */
public class Step {
	public Long dua_id;
	public Long date;
	public List<Steps> steps;
	public Map<String,Long> histo;

	public Long getDua_id() {
		return dua_id;
	}

	public void setDua_id(Long dua_id) {
		this.dua_id = dua_id;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public List<Steps> getSteps() {
		return steps;
	}

	public void setSteps(List<Steps> steps) {
		this.steps = steps;
	}

	public Map<String, Long> getHisto() {
		return histo;
	}

	public void setHisto(Map<String, Long> histo) {
		this.histo = histo;
	}


}
