package com.ydcun.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.ydcun.entity.Step;
import com.ydcun.entity.Steps;
import com.ydcun.entity.Users;

public interface IStepDao {
	public void save(Step step);
	/**
	 * 根据 对象某些字段来筛选
	 * @param step
	 */
	public List<Step> findStepList(Step step);
	/**
	 * 
	 * 根据对象的dua_id和date字段来查找记录条数
	 * @param step
	 * @return
	 */
	public Long findCount(Step step);
	/**
	 * 
	 * 根据对象中steps的详细信息push
	 * @param step
	 */
	public void push(Step step);
	/**
	 * 更新对象
	 * @param temp
	 */
	public void update(Step temp);
}
