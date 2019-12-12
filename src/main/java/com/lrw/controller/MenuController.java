package com.lrw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.vo.Menu;
import com.lrw.vo.Result;

@RestController
@RequestMapping("/Menu")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class MenuController {
	
	@PostMapping("/getMenuByUid")
	public Result getMenuByUid() {
		Result result = new Result();
		Menu menu = new Menu();
		menu.setTitle("题库列表");
		menu.setSpread(false);
		menu.setHref("page/news/newsList.html");
		menu.setIcon("icon-text");
		List<Menu> list = new ArrayList<>();
		list.add(menu);
		result.setContentManagement(list);
		Menu memberCenterMenu = new Menu();
		memberCenterMenu.setTitle("用户列表");
		memberCenterMenu.setIcon("&#xe612");
		memberCenterMenu.setHref("page/user/userList/userList.html");
		memberCenterMenu.setSpread(false);
		
//		//作为用户列表的两个子模块
//		Menu memberListFirst = new Menu();
//		memberListFirst.setTitle("封禁用户");
//		memberListFirst.setIcon("&#xe612");
//		memberListFirst.setHref("page/user/userList/notokuserList.html");
//		memberListFirst.setSpread(false);
//		Menu memberListSecond = new Menu();
//		memberListSecond.setTitle("正常用户");
//		memberListSecond.setIcon("&#xe612");
//		memberListSecond.setHref("page/user/userList/okuserList.html");
//		memberListSecond.setSpread(false);
//		//将两个子菜单装给父菜单
//		List<Menu> memberCenterMenuChildren = new ArrayList<>();
//		memberCenterMenuChildren.add(memberListFirst);
//		memberCenterMenuChildren.add(memberListSecond);
//		memberCenterMenu.setChildren(memberCenterMenuChildren);
		List<Menu> memberCenter = new ArrayList<>();
		memberCenter.add(memberCenterMenu);
		result.setMemberCenter(memberCenter);
		return result;
	}
	
	
	

}
