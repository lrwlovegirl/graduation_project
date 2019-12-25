package com.lrw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.MenuService;
import com.lrw.vo.Menu;
import com.lrw.vo.Result;

import io.swagger.annotations.ApiOperation;

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
		}
	}
	
	@PostMapping("/findMenuByRid")
	public List<Menu> getMenuByRid(Integer rid){
		return null==rid? null:menuServiceImpl.getMenuByRid(rid);
	}
	
	//用于角色配置菜单选项
	//通过rid查询某个角色拥有的菜单，将全部角色查询出来，然后将已有的菜单标记
	@ApiOperation("通过rid查询某个角色拥有的菜单，将全部角色查询出来，然后将已有的菜单标记")
	@PostMapping("/queryMenuByRid")
	public List<Menu> queryMenuByRid(@RequestParam("rid")Integer rid){
		List<Menu> allMenuList = menuServiceImpl.findAllMenu();
		if(null==rid) {
			return allMenuList;
		}
		if(allMenuList==null) {
			allMenuList = new ArrayList();
		}
		List<Menu> roleMenuList = menuServiceImpl.getMenuByRid(rid);
		if(roleMenuList==null) {
			roleMenuList = new ArrayList();
		}
		for(int x=0;x<allMenuList.size();x++) {
			Menu menu = allMenuList.get(x);
			for(int y=0;y<roleMenuList.size();y++) {
				if(menu.getMid().equals(roleMenuList.get(y).getMid())) {//该角色有这个标记
					menu.setMark(true);
				}
			}
		}
		return allMenuList;
	}
	

}
