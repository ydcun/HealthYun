package com.ydcun.mongodb.dao;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ydcun.entity.Users;

@Repository
public class BaseMgDaoImpl<T> implements IBaseMgDao<T> {
    @Autowired
	private MongoTemplate mongoTemplate;
    
    public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/***
     * 查找所有对象；
     */
	@Override
	public List<T> findAll(Class<T> entity) {
		return mongoTemplate.findAll(entity);

	}

	/**
	 * 
	 */
	public void findAndModify(String id) {
		//mongoTemplate.find(new Query(Criteria.where("id").is(id)), new Update().inc("age", 3));
	}

	@Override
	public List<T> findByRegex(String regex,Class<T> o) {
		 Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);   
	      Criteria criteria = new Criteria("name").regex(pattern.toString());   
	        return mongoTemplate.find(new Query(criteria), o);   

	}

	/***
	 * 根据ID查找对象；
	 */
	@Override
	public T findOne(String id,Class<T> entity) {
		 return (T) mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), entity);   
	}

	/***
	 * 添加一个实体；
	 */
	@Override
	public void insert(Object entity) {
		mongoTemplate.save(entity);   
	}


	/***
	 * 根据ID删除一个对象；
	 */
	@Override
	public void removeOne(String id,Class<T> entity) {
		Criteria criteria = Criteria.where("id").in(id);   
        if(criteria != null){   
             Query query = new Query(criteria);   
             if(query != null && mongoTemplate.findOne(query, entity) != null)   
                 mongoTemplate.remove(mongoTemplate.findOne(query, entity));   
        }   

	}

	/***
	 * 更新实体；
	 */
	@Override
	public void updateEntity(Object entity) {
		mongoTemplate.save(entity);
	}

	/***
	 * 根据Criteria查询一个实体；
	 */
	@SuppressWarnings("hiding")
	@Override
	public <T> T findEntityByCriteria(Criteria criteria, Class<T> entity) {
		Query query=new Query();
		query.addCriteria(criteria);
		return  mongoTemplate.findOne(query, entity);
	}

	@Override
	public void removeEntity(Class<T> entity) {
		// TODO Auto-generated method stub
		Criteria criteria = new Criteria();
		if(criteria != null){   
            Query query = new Query(criteria);   
            if(query != null && mongoTemplate.findOne(query, entity) != null)   
            	mongoTemplate.remove(mongoTemplate.findOne(query, entity));   
        }   
		
		
	}
	


}
