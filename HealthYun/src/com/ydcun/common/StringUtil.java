package com.ydcun.common;

public class StringUtil {

	public static boolean isEmpty(String dua_id) {
		if(dua_id==null || dua_id.equals("")){
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Long dua_id) {
		if(dua_id==null){
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

}
