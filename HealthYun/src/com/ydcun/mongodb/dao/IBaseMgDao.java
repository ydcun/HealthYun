/**
 * AbstractRepository.java
 * 版权所有(C) 2012 
 * 创建: 2012-12-12 11:40:40
 */
package com.ydcun.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author 
 */
public interface IBaseMgDao<T> {

	/**
	 * 添加对象
	 * @author 
	 */
	public void insert(Object entity);

	/**
	 * 根据ID查找对象
	 * @author 
	 */
	public T findOne(String id, Class<T> entity);

	public List<T> findByRegex(String regex,Class<T> o);

	/**
	 * 删除指定的ID对象
	 * @author 
	 */
	public void removeOne(String id, Class<T> entity);
	
	/**
	 * 根据制定的条件删除数据库中的对象
	 * 
	 */
	public void removeEntity(Class<T> entity);
 
	/**
	 * 通过ID找到并修改
	 * @author 
	 */
	public void findAndModify(String id);

	public List<T> findAll(Class<T> entity);

	/**
	 * 修改实体
	 */
	public void updateEntity(Object entity);
	
	/**
	 * 条件查询
	 * @param <T>
	 * @param criteria
	 * @param entity
	 * @return
	 */
	public <T> T findEntityByCriteria(Criteria criteria,Class<T> entity);

}
