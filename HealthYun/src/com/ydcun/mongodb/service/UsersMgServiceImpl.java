package com.ydcun.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.ydcun.entity.Users;
import com.ydcun.mongodb.dao.IUsersMgDao;

@Service
public class UsersMgServiceImpl implements IUsersMgService {
   
	@Autowired
	private IUsersMgDao usersMgDaoImpl;
    
    /***
     * 得到全部内容
     */
	@SuppressWarnings("unchecked")//该批注的作用是给编译器一条指令，告诉它对被批注的代码元素内部的某些警告保持静默
	@Override
	public List<Users> findUserss() {
		return (List<Users>) usersMgDaoImpl.findAll();
	}

	/***
	 * 通过ID得到对象
	 */
	@Override
	public Users findUsers(String id) {
		return usersMgDaoImpl.findOne(id);
	}

	/***
	 * 更新实体对象
	 */
	@Override
	public void updateUsers(Users entity) {
		 usersMgDaoImpl.updateEntity(entity);
	}

	/***
	 * 增加对象
	 */
	@Override
	public void addUsers(Users entity) {
		
		usersMgDaoImpl.insert(entity);
	}

	/***
	 * 根据ID号删除一个对象
	 */
	@Override
	public void delUsers(String id) {
		usersMgDaoImpl.removeOne(id);
	}

}
