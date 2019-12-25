package com.lrw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.vo.Menu;


@Mapper
public interface MenuMapper {

List<Menu> findMenuByUsername(String username);

List<Menu> getMenuByRid(int rid);

List<Menu> findAllMenu();	
	
	
}
