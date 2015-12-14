package com.ydcun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ydcun.common.StringUtil;
import com.ydcun.entity.Step;
import com.ydcun.entity.Steps;
import com.ydcun.entity.YearStep;
import com.ydcun.exception.MyException;
import com.ydcun.mongodb.service.IStepService;

@Controller
@RequestMapping("/step")
public class StepController {
	@Autowired
	private IStepService stepServiceImpl;

	@RequestMapping("/addStep")
	public void addStep() {			//初始化一些简单数据，向mongo数据库中插入，用于测试
		Step step = new Step();
		step.setDua_id((long) 861478702);
		step.setDate((long) 20150201);
		List<Steps> list = new ArrayList<Steps>();
		Steps steps1 = new Steps(new Long(45), new Long(321),new Long(233),new Long(56), new Long(353),new Long(35),new Long(3432));
		list.add(steps1);
		step.setSteps(list);
		stepServiceImpl.addStep(step);

	}
	@SuppressWarnings("finally")
	@RequestMapping("/findStepList")
	@ResponseBody
	public String findListEntityByCriteria(@RequestBody String dua){  //从客户端接受数据可以使用@RequestParam注解，如@RequestParam String dua_id，获取满足条件的多条记录的数据
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
	public String stepSearchByDuaidAndDate(@RequestBody String json){	//json串种包括date和dua_d，获取满足条件的一跳记录的数据
		Step fromJson = new Gson().fromJson(json, new TypeToken<Step>(){}.getType());
		Step list = null; 
		try{
			if(StringUtil.isEmpty(fromJson.getDua_id())){
				throw new MyException("dua_id错误");
			}
			if(StringUtil.isEmpty(fromJson.getDate())){
				throw new MyException("date错误");
			}
			 list =  this.stepServiceImpl.findOneByDua_id(fromJson.getDua_id(),fromJson.getDate());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return new Gson().toJson(list);
		}
		
	}
	
	
	@SuppressWarnings("finally")
	@RequestMapping("findCount")
	@ResponseBody
	public Long findStepCount(Long dua_id,Long date){			//根据dua_id、date查找step表中符合条件的记录数
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
	
	
	
	@RequestMapping("/stepDayHistoSearch")
	@ResponseBody
	public String stepDayHisto(@RequestBody String json){	//客户端传来的参数包括day0、day1、dua_id，获取用户一段时间内每天的细粒度数据
		Gson gs =  new Gson();
		Map<String,Long> map = gs.fromJson(json, new TypeToken<Map<String, Long>>() { }.getType());//将json串转化成map集合，以获取字符串中的值
		Long dua_id = map.get("dua_id");
		Long day0 = map.get("day0");
		Long day1 = map.get("day1");
		List<Step> list = this.stepServiceImpl.findListByDua_id(dua_id,day0,day1);
		System.out.println(list);
		List<Object> res2 = new ArrayList<Object>();
		if(list==null){
			return null;
		}
		for(Step step:list){
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put(String.valueOf(step.getDate()), new Gson().toJson(step.getHisto()));
			res2.add(map2);
		}
		System.out.println(new Gson().toJson(res2));
		return new Gson().toJson(res2);
		
	}
	
	
	@RequestMapping("/stepYearHistoSearch")
	@ResponseBody
	public String yearStepSearch(@RequestBody String json){	//由客户端传来的json字符串，包括dua-id、year，获取yearStep表中用户每年的steps数据
		Gson gs =  new Gson();
		Map<String,Long> map = gs.fromJson(json, new TypeToken<Map<String, Long>>() { }.getType());//将json串转化成map集合，以获取字符串中的值
		Long dua_id = map.get("dua_id");
		Long year = map.get("year");
		YearStep res = this.stepServiceImpl.findYearStep(dua_id,year);
//		System.out.println(res);
		Map<String, Long> res2 = res.getData();
//		System.out.println(res2);
//		System.out.println(new Gson().toJson(res2));
		
		return new Gson().toJson(res2);
		
	}
	
	

	
	
	@SuppressWarnings("finally")
	@RequestMapping("updateStep") // method = RequestMethod.GET		//这个方法将同时更新dua_id、date、steps、每天的细粒度数据、每年的steps数据
	@ResponseBody
	public String findAndUpdate(@RequestBody String json){
		
		Step fromJson = new Gson().fromJson(json, new TypeToken<Step>(){}.getType());//这个客户端传过来的json串包括dua_id、date、steps【t,c,d,p,s,w,j】
//		Map<String,Object> fromJson = new Gson().fromJson(json, new TypeToken<Map<String,Object>>(){}.getType());//这个客户端传过来的json串包括dua_id、date、steps【t,c,d,p,s,w,j】
//		List<Map<String,Object>> fromJson2 = new Gson().fromJson((String) fromJson.get("steps"), new TypeToken<List<Map<String,Object>>>(){}.getType());//这个客户端传过来的json串包括dua_id、date、steps【t,c,d,p,s,w,j】
//		List<Map<String,Object>> fromJson2 =(List<Map<String,Object>>) fromJson.get("steps");//这个客户端传过来的json串包括dua_id、date、steps【t,c,d,p,s,w,j】
		
		Map<String, Object> map = new HashMap<String, Object>();
//		return json;
		
		

		
		try{
			//对客户端传来的各个数据进行判断
//			if(!StringUtils.isNumeric(String.valueOf(fromJson.getDua_id()))){
//				throw new MyException("BAD_DUAID");
//			}
//			if(!Long.isValidLong(fromJson.getDua_id())){
//				
//			}
			if(!StringUtils.isNumeric(String.valueOf(fromJson.getDate()))){
				throw new MyException("BAD_DATE");
			}
			

			for(Steps steps:fromJson.getSteps()){
				if(!StringUtils.isNumeric(String.valueOf(steps.getC()))){
					throw new MyException("BAD_COUNT");
				}
				if(!StringUtils.isNumeric(String.valueOf(steps.getT()))){
					throw new MyException("BAD_TIME");
				}
				if(!StringUtils.isNumeric(String.valueOf(steps.getD()))){
					throw new MyException("BAD_DURATION");
				}
				if(!StringUtils.isNumeric(String.valueOf(steps.getP()))){
					throw new MyException("BAD_POSITION");
				}
				if(!StringUtils.isNumeric(String.valueOf(steps.getS()))){
					throw new MyException("BAD_STATUS");
				}
				if(!StringUtils.isNumeric(String.valueOf(steps.getJ()))){
					throw new MyException("BAD_LONGITUDE");
				}
				if(!StringUtils.isNumeric(String.valueOf(steps.getW()))){
					throw new MyException("BAD_LATITUDE");
				}
			}
			
			if(StringUtil.isEmpty(fromJson.getDua_id())){
				throw new MyException("dua_id错误");
			}
			if(StringUtil.isEmpty(fromJson.getDate())){
				throw new MyException("date错误");
			}
			this.stepServiceImpl.updateOrPush(fromJson);
			map.put("ok", "steps upload success");
		}catch(MyException e2){
			map.put("error", e2.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return new Gson().toJson(map);
		}
		
		
		
	}
}
