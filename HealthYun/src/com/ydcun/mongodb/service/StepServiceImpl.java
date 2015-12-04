package com.ydcun.mongodb.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.ydcun.entity.Step;
import com.ydcun.entity.Steps;
import com.ydcun.mongodb.dao.IStepDao;
@Service
public class StepServiceImpl implements IStepService{
	@Autowired
	private IStepDao stepDaoImpl;
	
	@Override
	public void addStep(Step step) {
		stepDaoImpl.save(step);
	}

	@Override
	public List<Step> findListByDua_id(Long dua_id) {
		Step step = new Step();
		step.setDua_id(dua_id);
		List<Step> list = this.stepDaoImpl.findStepList(step);
		return list;
	}
	@Override
	public Step findOneByDua_id(Long dua_id, Long date) {
		Step step = new Step();
		step.setDua_id(dua_id);
		step.setDate(date);
		List<Step> list =this.stepDaoImpl.findStepList(step);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
//		return list;
	}

	@Override
	public Long findStepCount(Long dua_id, Long date) {
		Step step = new Step();
		step.setDua_id(dua_id);
		step.setDate(date);
		Long count = this.stepDaoImpl.findCount(step);
		return count;
	}

	@Override
	public void push(Step step) {
		if(step==null){
			return;
		}
		if(step.getDate()==null){
			return;
		}
//		this.stepDaoImpl.push(step);
		List<Step> list = this.stepDaoImpl.findStepList(step);
		Step temp=null;
//		if(list==null || list.size()<=0){
//			this.stepDaoImpl.save(step);
//			temp = step;
//		}else{
//			temp = list.get(0);
//			temp.getSteps().addAll(step.getSteps());
//		}
		//this.stepDaoImpl.save(step);
		temp = step;
		for(Steps steps:step.getSteps()){
			//计算机
			String jsjg="001";
			Map<String, Long> map = temp.getHisto();
			if(map==null){
				map = new HashMap<String, Long>();
			}
			Long tm = map.get("tm"+jsjg);
			if(tm==null){
				map.put("tm"+jsjg ,steps.getC());
			}else{
				tm +=steps.getC();
				map.put("tm"+jsjg , steps.getC());
			}
			temp.setHisto(map);
		}
		this.stepDaoImpl.update(temp);
	}

	@Override
	public void upadteHistory(Step step) {
//		Steps steps = step.getSteps();
		
	}
	
}
