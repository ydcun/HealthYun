package com.ydcun.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import javafx.scene.chart.PieChart.Data;

public class Util {

	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	public static void  main(String[] args) {
		Date date = new Date();
		System.out.println(date.getTime());
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(new Long("1449231639"));
//		System.out.println(cl.get(Calendar.YEAR));
//		System.out.println(cl.get(Calendar.MONTH));
//		System.out.println(cl.get(Calendar.DAY_OF_MONTH));
		System.out.println(Math.ceil((1.1)));
		System.out.println((Math.ceil((float)28/15)));
		int a = 123;
		String b = String.format("%02d", a);
		System.out.println(b);
		
		 int str1=0;
		 DecimalFormat df=new DecimalFormat("00");
		     String str2=df.format(str1);
		     System.out.println(str2);
		     String  str3 = str2+1;
		     System.out.println(str3);
		     
		     Long start =Math.round(Math.ceil((float)17/15));
		     System.out.println(start);
		     
		     Integer bianhao=1;
		     DecimalFormat df2 = new DecimalFormat("00");
		     
		     System.out.println(df2.format(bianhao));
		     System.out.println("tm"+df2.format(bianhao)+1);
		     
		     System.out.println(70/60);
		     
		     Integer N = new Long((56+15)/60+1).intValue();
		     System.out.println(N);
		     
		     if(StringUtils.isNumeric(String.valueOf(966478702))){
		    	 System.out.println("true");
				}
		     else
		     System.out.println("false");
	}
}
