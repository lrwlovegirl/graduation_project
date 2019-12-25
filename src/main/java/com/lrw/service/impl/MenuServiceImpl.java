package com.lrw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrw.mapper.MenuMapper;
import com.lrw.service.MenuService;
import com.lrw.service.UserService;
import com.lrw.vo.Menu;
import com.lrw.vo.Result;
import com.lrw.vo.User;
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private MenuMapper menuMapper;
	@Override
	public Result getMenuByUsername(String username) {
		//根据username查询出对应的角色
		User user = userServiceImpl.findUserByUsername(username);
		if(user == null) {
			return null ;
		}else {
			//同过用户名将该用户对应的菜单返回
			List<Menu> menuList = menuMapper.findMenuByUsername(username);
			Result result = new Result();
			result.setContentManagement(menuList);
			return result;
		}
	}
	@Override
	public List<Menu> getMenuByRid(int rid) {
		// TODO Auto-generated method stub
		return menuMapper.getMenuByRid(rid);
	}
	@Override
	public List<Menu> findAllMenu() {
		// TODO Auto-generated method stub
		return menuMapper.findAllMenu();
	}
}
