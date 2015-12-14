package com.ydcun.mongodb.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydcun.entity.Step;
import com.ydcun.entity.YearStep;
import com.ydcun.entity.Steps;
import com.ydcun.mongodb.dao.IStepDao;
import com.ydcun.mongodb.dao.IYearStepDao;
@Service
public class StepServiceImpl implements IStepService{
	@Autowired
	private IStepDao stepDaoImpl;
	@Autowired
	private IYearStepDao yearStepDaoImp ;
	
	@Override
	public void addStep(Step step) {
		stepDaoImpl.save(step);
	}

	@Override
	public List<Step> findListByDua_id(Long dua_id) {
		Step step = new Step();
		step.setDua_id(dua_id);
		List<Step> list = this.stepDaoImpl.findStepList(step);
		return list;
	}
	@Override
	public Step findOneByDua_id(Long dua_id, Long date) {
		Step step = new Step();
		step.setDua_id(dua_id);
		step.setDate(date);
		List<Step> list =this.stepDaoImpl.findStepList(step);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
//		return list;
	}

	@Override
	public Long findStepCount(Long dua_id, Long date) {
		Step step = new Step();
		step.setDua_id(dua_id);
		step.setDate(date);
		Long count = this.stepDaoImpl.findCount(step);
		return count;
	}
	
	public void stepDitribute(Steps steps,Step temp,Float avgCount,Long c){		//这个方法是用于分配一个小时内的步数，被下面的updateOrPush(Step step)方法多次调用
		
		Long t = steps.getT();//得到steps字段中的时间戳
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(new Long(t*1000));
		Integer hour = cl.get(Calendar.HOUR_OF_DAY);//得到时间戳所在的小时,小于10时，前导0被丢弃
		DecimalFormat df = new DecimalFormat("00");//要求小时始终为两位数，小于0时自动补0
		String end_hour = df.format(hour);
		Integer min = cl.get(Calendar.MINUTE);//得到时间戳所在的分钟
		Long start =Math.round(Math.ceil((float)min/15));//开始更新四个数中的哪一个
		
		Long d2 = steps.getD();
		Map<String, Long> map = temp.getHisto();
		if(map==null){
			map = new HashMap<String, Long>();
		}
		Long end_min = min+d2;
		Long end =Math.round(Math.ceil((float)end_min/15));//最后一个被更新的数
		Long n = end - start + 1;
		if(n==1){		//只更新一个区间
			String bianhao = end_hour + start;
			Long sumCount = Math.round(Math.ceil(d2* avgCount));
			steps.setC(sumCount);
			Long tm = map.get("tm"+bianhao);
			if(tm==null){
				map.put("tm"+bianhao ,steps.getC());
			}else{
				tm +=steps.getC();
				map.put("tm"+bianhao , tm);
			}
			temp.setHisto(map);
		}
		if(n==2){	//同时更新2个区间
			Long c1 = Math.round(Math.ceil((start * 15 - min)* avgCount));	//第一个区间所走的步数
			steps.setC(c1);
			String bianhao= end_hour+start;	//第一个区间的的字段名
			System.out.println(bianhao);
			
			for(int i=1;i<=n;i++){
				Long tm = map.get("tm"+bianhao);
			if(tm==null){
				map.put("tm"+bianhao ,steps.getC());
			}else{
				tm +=steps.getC();
				map.put("tm"+bianhao , tm);
			}
			temp.setHisto(map);
			bianhao=end_hour+(start+i);	//第二个区间的字段名
			System.out.println(bianhao);
			c = c- c1;					//第二个区间所走的步数
			System.out.println(c);
			steps.setC(c);
			}
		}
		
		if(n==3){
			Long c2=null;
			Long c1 = Math.round(Math.ceil((start * 15 - min)* avgCount));	//第一个区间所走的步数
			steps.setC(c1);
			String bianhao= end_hour+start;	//第一个区间的的字段名
			System.out.println(bianhao);
			
			for(int i=1;i<=n;i++){
				Long tm = map.get("tm"+bianhao);
			if(tm==null){
				map.put("tm"+bianhao ,steps.getC());
			}else{
				tm +=steps.getC();
				map.put("tm"+bianhao , tm);
			}
			temp.setHisto(map);
			bianhao=end_hour+(start+i);	//第二个区间的字段名
			System.out.println(bianhao);
			if(i==1){
				c2 = Math.round(Math.ceil(15*avgCount));
			}
			if(i==2){
				c2 = c- c1-Math.round(Math.ceil(15*avgCount));
			}
								//第二个区间所走的步数
			System.out.println(c2);
			steps.setC(c2);
			}
		}
		
		if(n==4){
			Long c2=null;
			Long c1 = Math.round(Math.ceil((start * 15 - min)* avgCount));//第一个区间所走的步数
			steps.setC(c1);
			String bianhao= end_hour+start;	//第一个区间的的字段名
			System.out.println(bianhao);
			
			for(int i=1;i<=n;i++){
				Long tm = map.get("tm"+bianhao);
			if(tm==null){
				map.put("tm"+bianhao ,steps.getC());
			}else{
				tm +=steps.getC();
				map.put("tm"+bianhao , tm);
			}
			temp.setHisto(map);
			bianhao=end_hour+(start+i);	//第二个区间的字段名
			System.out.println(bianhao);
			if(i==1){
				c2 = Math.round(Math.ceil(15*avgCount));
			}
			if(i==2){
				c2 = Math.round(Math.ceil(15*avgCount));
			}
			if(i==3){
				c2 = c - c1 - 2*Math.round(Math.ceil(15*avgCount));
			}
			System.out.println(c2);
			steps.setC(c2);
			}
		}
	}
	

	@Override
	public void updateOrPush(Step step) {
		if(step==null){
			return;
		}
		if(step.getDate()==null){
			return;
		}
		this.stepDaoImpl.pushStep(step);
		YearStep yearStep = null ;
		YearStep temp2=null;
		List<Step> list = this.stepDaoImpl.findStepList(step);
		Step temp=null;
		if(list==null || list.size()<=0){
			temp = step;
		}else{
			temp = list.get(0);
			this.stepDaoImpl.removeStepEntity(list.get(0));
		}
		
		for(Steps steps:step.getSteps()){
			//计算
			Long t = steps.getT();//得到steps字段中的时间戳
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(new Long(t*1000));
			Integer year = cl.get(Calendar.YEAR);
			Integer month = cl.get(Calendar.MONTH)+1;
			Integer day = cl.get(Calendar.DAY_OF_MONTH);
			Integer hour = cl.get(Calendar.HOUR_OF_DAY);//得到时间戳所在的小时,小于10时，前导0被丢弃
			DecimalFormat df = new DecimalFormat("00");//要求小时始终为两位数，小于0时自动补0
			String end_month = df.format(month);
			String end_day = df.format(day);
			
			Integer min = cl.get(Calendar.MINUTE);//得到时间戳所在的分钟，分钟小于10时，直接将前导0去了
			Long c = steps.getC();
			Long d = steps.getD();
			
			
			//下面处理年数据
			Long dua_id = step.getDua_id();
			if(yearStep==null){
				yearStep = new YearStep();
			}
			yearStep.setDua_id(dua_id);
			yearStep.setYear(year);
			
			List<YearStep> list2 = this.stepDaoImpl.findYearStepList(yearStep);
			if(list2==null || list2.size()<=0){
				temp2 = yearStep;
			}else{
				temp2 = list2.get(0);
				this.stepDaoImpl.removeYearStepEntity(list2.get(0));
			}
			
			Map<String,Long> map2 = temp2.getData();
			if(map2==null){
				map2= new HashMap<String, Long>();
			}
			Long md = map2.get("md"+end_month+end_day);
			if(md==null){
				map2.put("md"+end_month+end_day, steps.getC());
			}else{
				Long uu=md +steps.getC();
				map2.put("md"+end_month+end_day,uu);
				
			}
			temp2.setData(map2);
			this.yearStepDaoImp.updateEntity(temp2);
			
			
			//下面处理细粒度数据（96个点）
			
//			Long avgCount = Math.round(Math.ceil((float)c/d));//每分钟走的步数,这样可能会使最终的步数之和大于传来的步数,误差较大，所以不进行处理，只需得到float类型的值
			Float avgCount = (float)c/d;//每分钟走的步数
			
			Integer N = new Long((min+d)/60+1).intValue();//横跨N个小时
			System.out.println(N);
			
			Map<String, Long> map = temp.getHisto();
			if(map==null){
				map = new HashMap<String, Long>();
			}
				if(N==1){
					
					stepDitribute(steps,temp,avgCount,c);
				}
				if(N==2){
					Long d3 = Long.parseLong(String.valueOf(60- min));
					steps.setD(d3);
					c=Math.round(Math.ceil(d3*avgCount));
					stepDitribute(steps,temp,avgCount,c);
					hour+=1;
					SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd-hh-mm");
				    Date date = null;
					try {
						date = simpleDateFormat.parse(year+"-"+month+"-"+day+"-"+hour+"-"+1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				    Long timeStemp = date.getTime();//得到第二个小时零分时的时间戳
				    steps.setT(timeStemp/1000);
					Long d4 = d-d3; 
					steps.setD(d4);
					c = Math.round(Math.ceil(d4*avgCount));
					System.out.println(d4);
					
					stepDitribute(steps,temp,avgCount,c);
					
				}
				if(N==3){
					Long d3 = Long.parseLong(String.valueOf(60- min));
					steps.setD(d3);
					c = Math.round(Math.ceil(d3*avgCount));
					stepDitribute(steps,temp,avgCount,c);
					SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd-hh-mm");
				    Date date = null;
				    Long timeStemp;
				    Long d4 = null;
				    
				    for(int i=1;i<=N-1;i++){
				    	hour+=1;
						try {
							date = simpleDateFormat.parse(year+"-"+month+"-"+day+"-"+hour+"-"+1);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					    timeStemp = date.getTime();//得到第二个小时零分时的时间戳
					    steps.setT(timeStemp/1000);
					    if(i==1){
					    	d4 = Long.parseLong(String.valueOf(59)); 
					    }
					    if(i==2){
					    	d4 = d-d3-60;
					    }
						steps.setD(d4);
						c = Math.round(Math.ceil(d4*avgCount));
						System.out.println(d4);						
						stepDitribute(steps,temp,avgCount,c);
				    }
				    
				}
			
		}
		
		this.stepDaoImpl.updateEntity(temp);
	}

	@Override
	public void upadteHistory(Step step) {
		
	}

	@Override
	/**
	 * 根据dua_id和开始日期、结束日期查找每天的历史数据
	 */
	public List<Step> findListByDua_id(Long dua_id, Long day0, Long day1) {
		List<Step> list = this.stepDaoImpl.findStepList(dua_id,day0,day1);
		return list;
	}

	@Override
	public YearStep findYearStep(Long dua_id, Long year) {
		YearStep res = this.yearStepDaoImp.findYearStepHisto(dua_id,year);
		return res;
	}
	
	
}
