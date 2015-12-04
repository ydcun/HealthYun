package com.ydcun.mongodb.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.ydcun.entity.Step;

public interface IStepService {
	/**
	 * 
	 * 向数据表中插入step数据
	 * @param step
	 */
	public void addStep(Step step);
	/**
	 * 根据dua_id 查询
	 * @param dua_id
	 * @return
	 */
	public List<Step> findListByDua_id(Long dua_id);
	/**
	 * 根据dua_id and  date查询
	 * @param dua_id
	 * @return
	 */
	public Step findOneByDua_id(Long dua_id,Long date);
	
	/**
	 * 
	 * 根据dua_id和date查询数据库中满足条件的记录数
	 * @param dua_id
	 * @param date
	 * @return
	 */
	public Long findStepCount(Long dua_id, Long date);
	/***
	 * 
	 * 根据steps中的详细信息进行push
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 * @param g
	 */
	public void push(Step step);
	
	/**
	 * 
	 * 每次接收到数据都要向step日表中更新history，即一天中以15min为粒度的数据，共96个点
	 * @param fromJson
	 */
	public void upadteHistory(Step fromJson);
}
