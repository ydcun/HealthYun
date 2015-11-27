package com.ydcun.mongodb.dao;

import java.util.List;

import com.ydcun.entity.Users;

public interface IUsersMgDao {

	/**
	 * 获取所有用户数据
	 * @return
	 */
	public List<Users> findAll();

	public Users findOne(String id);

	public void updateEntity(Users entity);

	public void insert(Users entity);

	public void removeOne(String id);

}
