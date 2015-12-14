package com.ydcun.mongodb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.ydcun.entity.Step;
import com.ydcun.entity.Steps;
import com.ydcun.entity.YearStep;
@Repository
public class YearStepDaoImp extends BaseMgDaoImpl<YearStep> implements IYearStepDao{


	@Override
	public List<YearStep> findYearStepList(YearStep yearStep) {
		if(yearStep==null){
			return null;
		}
		Query query = new Query();
		if(yearStep.getYear()!=null){
			query.addCriteria(Criteria.where("year").is(yearStep.getYear()));
		}
		if(yearStep.getDua_id()!=null){
			query.addCriteria(Criteria.where("dua_id").is(yearStep.getDua_id()));
		}
		//query.addCriteria(Criteria.where("dua_id").is(step.getDua_id()).and("date").is(step.getDate()));
		 List<YearStep> list =  this.getMongoTemplate().find(query, YearStep.class);
		 return list;
		
	}
	
	
	@Override
	public void removeYearStepEntity(YearStep yearStep) {
		// TODO Auto-generated method stub
		Query query = new Query();
		if(yearStep.getDua_id()!=null&&yearStep.getYear()!=null){
			query.addCriteria(Criteria.where("dua_id").is(yearStep.getDua_id()).and("year").is(yearStep.getYear()));
		}
		this.getMongoTemplate().remove(query, YearStep.class);
	}
	
	
	public Long findYearCount(YearStep yearStep){
		if(yearStep==null){
			return null;
		}
		Query query = new Query();
		if(yearStep.getDua_id()!=null&&yearStep.getYear()!=null){
			query.addCriteria(Criteria.where("dua_id").is(yearStep.getDua_id()).and("date").is(yearStep.getYear()));
		}
		
		Long count = this.getMongoTemplate().count(query, YearStep.class);
		//this.getMongoTemplate().updateFirst(query, new Update().push("steps",steps2), Step.class);
		return count;
	}


	@Override
	public YearStep findYearStepHisto(Long dua_id, Long year) {
		if(dua_id==null && year==null){
			return null;
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("dua_id").is(dua_id).and("year").is(year));
		YearStep result = this.getMongoTemplate().findOne(query, YearStep.class);
		return result;
	}
	
}
