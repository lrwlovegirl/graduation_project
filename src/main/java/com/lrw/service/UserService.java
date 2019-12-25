package com.lrw.service;

import java.util.List;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.User;

public interface UserService {
	//增加用户
	  void createUser(User user); 
	  //查询出所有的用户	
	  PageListRes findAllUser(QueryVo qv);
	  //查询出所有的正常登陆的用户
	  PageListRes findAllOkUser(QueryVo qv);
	  //查询出所有的被封禁的用户
	  PageListRes findAllNotOkUser(QueryVo qv);
	  //通过id查询出单个用户
	  User findUserById(int id);
	  //更新单个用户
	  void updateUser(User user);
	  //根据id查出用户的状态：处于封禁还是正常
	  Integer findUserStatusById(int id);
	  //批量解禁
	  void changeStatusToOk(int[] array);
	  //批量封禁
	  void changeStatusToNotOk(int[] array);
	  //模糊查询
	  PageListRes queryUserByKeyword(QueryVo qv);
	  //登录调用的方法
	  User findUserByUsernameAndPassword(String username,String password);
	  //根据用户名查询用户
	  User findUserByUsername(String username);
	  
	  void updateUserLastLoginTime(String username,String time);
	  
	  
}
