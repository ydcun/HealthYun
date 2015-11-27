package com.ydcun.mongodb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ydcun.entity.Users;
@Repository
public class UsersMgDaoImpl extends BaseMgDaoImpl<Users> implements IUsersMgDao{

	@Override
	public List<Users> findAll() {
		return (List<Users>) super.findAll(Users.class);
	}

	@Override
	public Users findOne(String id) {
		return super.findOne(id, Users.class);
	}

	@Override
	public void updateEntity(Users users) {
		super.updateEntity(users);
	}

	@Override
	public void insert(Users users) {
		super.insert(users);
	}

	@Override
	public void removeOne(String id) {
		super.removeOne(id,Users.class);
	}

}
