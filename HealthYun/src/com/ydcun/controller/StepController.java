package com.ydcun.controller;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.Extension;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.MessageHandler.Partial;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.RemoteEndpoint.Basic;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.ydcun.common.StringUtil;
import com.ydcun.entity.Step;
import com.ydcun.entity.Steps;
import com.ydcun.exception.MyException;
import com.ydcun.mongodb.service.IStepService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Controller
@RequestMapping("/step")
public class StepController {
	@Autowired
	private IStepService stepServiceImpl;

	@RequestMapping("/addStep")
	public void addStep() {
		Step step = new Step();
		step.setDua_id((long) 861478702);
		step.setDate((long) 20150201);
		List<DBObject> step1 = new ArrayList<DBObject>();
		List<Steps> list = new ArrayList<Steps>();
		Steps steps1 = new Steps(new Long(45), new Long(321),new Long(233),new Long(56), new Long(353),new Long(35),new Long(3432));
		list.add(steps1);
		step.setSteps(list);
		stepServiceImpl.addStep(step);

	}
	@SuppressWarnings("finally")
	@RequestMapping("/findStepList")
	@ResponseBody
	public String findListEntityByCriteria(@RequestBody String dua){  //从客户端接受数据可以使用@RequestParam注解，如@RequestParam String dua_id
		String dua_id="861478702";
		
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			if(StringUtil.isEmpty(dua_id)){
				throw new MyException("dua_id不正确");
			}
			List<Step> list = this.stepServiceImpl.findListByDua_id(new Long(dua_id));
			if(list!=null){
				result.put("step", list);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("error", e.getMessage());
		}finally{
			return new Gson().toJson(result);
		}
	}
	
	
	@SuppressWarnings("finally")
	@RequestMapping("/stepSearch")
	@ResponseBody
	public String stepSearchByDuaidAndDate(@RequestBody String json){
		Step fromJson = new Gson().fromJson(json, new TypeToken<Step>(){}.getType());
		Step list = null; 
//		Map<String,Object> res = new HashMap<String, Object>();
		try{
			if(StringUtil.isEmpty(fromJson.getDua_id())){
				throw new MyException("dua_id错误");
			}
			if(StringUtil.isEmpty(fromJson.getDate())){
				throw new MyException("date错误");
			}
			 list =  this.stepServiceImpl.findOneByDua_id(fromJson.getDua_id(),fromJson.getDate());
//			if(list!=null){
//				res.put("ok", list.getSteps());
//			}
		}catch(Exception e){
			e.printStackTrace();
//			res.put("error", e.getMessage());
		}finally{
//			return new Gson().toJson(res);
			return new Gson().toJson(list);
		}
		
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("findCount")
	@ResponseBody
	public Long findStepCount(Long dua_id,Long date){
		Long c = null;
		try{
			if(StringUtil.isEmpty(dua_id)){
				throw new MyException("dua_id错误");
			}
			if(StringUtil.isEmpty(date)){
				throw new MyException("date错误");
			}
			c = this.stepServiceImpl.findStepCount(new Long(dua_id),new Long(date));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return c;
		}
		
	}
	
	
	@RequestMapping("updateStep") // method = RequestMethod.GET
	@ResponseBody
	public void findAndUpdate(@RequestBody String json){
		
		Step fromJson = new Gson().fromJson(json, new TypeToken<Step>(){}.getType());
		
		try{
			if(StringUtil.isEmpty(fromJson.getDua_id())){
				throw new MyException("dua_id错误");
			}
			if(StringUtil.isEmpty(fromJson.getDate())){
				throw new MyException("date错误");
			}
			this.stepServiceImpl.push(fromJson);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(json);
		
	}
	
	

	
}
