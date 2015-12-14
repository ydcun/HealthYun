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
public class StepDaoImpl extends BaseMgDaoImpl<Step> implements IStepDao{
	@Override
	public void save(Step step) {
		super.insert(step);
	}

	@Override
	public List<Step> findStepList(Step step) {
		if(step==null){
			return null;
		}
		Query query = new Query();
		if(step.getDate()!=null){
			query.addCriteria(Criteria.where("date").is(step.getDate()));
		}
		if(step.getDua_id()!=null){
			query.addCriteria(Criteria.where("dua_id").is(step.getDua_id()));
		}
		//query.addCriteria(Criteria.where("dua_id").is(step.getDua_id()).and("date").is(step.getDate()));
		 List<Step> list =  this.getMongoTemplate().find(query, Step.class);
		 return list;
		
	}

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
	public Long findCount(Step step) {
		if(step==null){
			return null;
		}
		Query query = new Query();
		if(step.getDua_id()!=null&&step.getDate()!=null){
			query.addCriteria(Criteria.where("dua_id").is(step.getDua_id()).and("date").is(step.getDate()));
		}
		
		Long count = this.getMongoTemplate().count(query, Step.class);
		//this.getMongoTemplate().updateFirst(query, new Update().push("steps",steps2), Step.class);
		return count;
	}

	@Override
	public void pushStep(Step step) {
//		Step step = new Step();
//		step.setDua_id(dua_id);
//		step.setDate(date);
		Query query = new Query();
		if(step.getDua_id()!=null&&step.getDate()!=null){
			query.addCriteria(Criteria.where("dua_id").is(step.getDua_id()).and("date").is(step.getDate()));
		}
		for(Steps steps:step.getSteps()){
			this.getMongoTemplate().updateFirst(query, new Update().push("steps", steps), Step.class);
		}
		
	}

	@Override
	public void removeStepEntity(Step step) {
		// TODO Auto-generated method stub
		Query query = new Query();
		if(step.getDua_id()!=null&&step.getDate()!=null){
			query.addCriteria(Criteria.where("dua_id").is(step.getDua_id()).and("date").is(step.getDate()));
		}
		this.getMongoTemplate().remove(query, Step.class);
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
	public List<Step> findStepList(Long dua_id, Long day0, Long day1) {
		if(dua_id==null && day0==null && day1==null){
			return null;
		}
		if(day0>day1){
			return null;
		}
		Query query = new Query();
//		query.addCriteria(Criteria.where("dua_id").is(dua_id).and("date").gte(day0).and("date").lte(day1));//错误
		query.addCriteria(Criteria.where("dua_id").is(dua_id).and("date").gte(day0).lte(day1));
		return this.getMongoTemplate().find(query, Step.class);
	}
	
}
