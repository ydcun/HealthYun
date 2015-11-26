package com.ydcun.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ydcun.entity.Users;
import com.ydcun.service.UserManageImpl;

@Controller
@RequestMapping("/test")
public class TestResource {
	@Autowired
    private UserManageImpl  userManageImpl;
	@Autowired
    private InternalResourceViewResolver viewResolver;

	@RequestMapping("/getText")
	public String getText() {
		// 实例化 
		userManageImpl.getAllUser(); 
		return "hello lucky";
	}

	@RequestMapping("/getAllUserfromJson")
	@ResponseBody
	public List<Users> getJson() {
		System.out.println("获取用户");
		//return this.userManage.getAllUser();
		Users u = new Users("ddd", 3, 3);
		List<Users> list = new ArrayList<Users>();
		list.add(u);
		return list;
	}
	@RequestMapping("/getXml")
	public List<Users> getXml() {
		System.out.println("获取用户");
		//return this.userManage.getAllUser();
		Users u = new Users("ddd", 3, 3);
		List<Users> list = new ArrayList<Users>();
		list.add(u);
		return list;
	}
}
