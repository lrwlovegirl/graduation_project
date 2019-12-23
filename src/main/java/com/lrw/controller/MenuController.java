package com.lrw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.MenuService;
import com.lrw.vo.Menu;
import com.lrw.vo.Result;

@RestController
@RequestMapping("/Menu")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class MenuController {
	
	@Autowired
	private MenuService menuServiceImpl;
	
	
	//根据用户名查询出对应的角色
	@PostMapping("/getMenuByUsername")
	public Result getMenuByUid(String username) {
		Result result = new Result();
		if(null==username||"".equals(username)) {
			return result;
		}else {
		  return menuServiceImpl.getMenuByUsername(username);
//			Menu menu = new Menu();
//			menu.setTitle("题库列表");
//			menu.setSpread(false);
//			menu.setHref("page/question/questionList.html");
//			menu.setIcon("icon-text");
//			Menu questionTest = new Menu();
//			questionTest.setTitle("在线考试");
//			questionTest.setHref("page/questionTest/questionTest.html");
//			questionTest.setIcon("icon-xiugai");
//			List<Menu> list = new ArrayList<>();
//			list.add(menu);
//			list.add(questionTest);
//			result.setContentManagement(list);
//			Menu memberCenterMenu = new Menu();
//			memberCenterMenu.setTitle("用户列表");
//			memberCenterMenu.setIcon("&#xe612");
//			memberCenterMenu.setHref("page/user/userList/userList.html");
//			memberCenterMenu.setSpread(false);
//			List<Menu> memberCenter = new ArrayList<>();
//			memberCenter.add(memberCenterMenu);
//			result.setMemberCenter(memberCenter);
//			return result;
		}
	}
	
	
	

}
