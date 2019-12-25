package com.lrw.service;

import java.util.List;

import com.lrw.vo.Menu;
import com.lrw.vo.Result;

public interface MenuService {

	
	Result getMenuByUsername(String username);
	
	List<Menu> getMenuByRid(int rid);

	List<Menu> findAllMenu();
}
