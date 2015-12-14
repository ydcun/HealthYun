package com.ydcun.mongodb.dao;

import java.util.List;

import com.ydcun.entity.YearStep;

public interface IYearStepDao extends IBaseMgDao<YearStep> {
	
	public List<YearStep> findYearStepList(YearStep yearStep);
	
	
	
	
	public void removeYearStepEntity(YearStep yearStep);
	
	
	public Long findYearCount(YearStep yearStep);
	
	
	public YearStep findYearStepHisto(Long dua_id,Long year);
}
