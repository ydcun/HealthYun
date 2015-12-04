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
	public void push(Step step) {
		
		Query query = new Query();
		if(step.getDua_id()!=null&&step.getDate()!=null){
			query.addCriteria(Criteria.where("dua_id").is(step.getDua_id()).and("date").is(step.getDate()));
		}
		for(Steps steps:step.getSteps()){
			this.getMongoTemplate().updateFirst(query, new Update().push("steps", steps), Step.class);
		}
		
	}

	@Override
	public void update(Step temp) {
		super.updateEntity(temp);
	}
	
}
