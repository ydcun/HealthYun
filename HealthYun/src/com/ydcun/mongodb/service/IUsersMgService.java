package com.ydcun.mongodb.service;

import java.util.List;

import com.ydcun.entity.Users;

/**
 */
public interface IUsersMgService {
	
	/**
	 * 获取所有公司列表
	 * @return
	 */
	public List<Users> findUserss();
	
	/**
	 * 获取公司详情
	 * @param id
	 * @return
	 */
	public Users findUsers(String id);
	
	/**
	 * 更新公司详情
	 * @param entity
	 */
	public void updateUsers(Users entity);
	
	/**
	 * 添加公司信息
	 * @param entity
	 */
	public void addUsers(Users entity);
	
	/**
	 * 删除公司根据id
	 * @param id
	 */
	public void delUsers(String id);
	
}
