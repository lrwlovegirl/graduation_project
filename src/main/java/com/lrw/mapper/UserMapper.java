package com.lrw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.util.QueryVo;
import com.lrw.vo.User;

@Mapper
public interface UserMapper {
  //增加用户
  void createUser(User user); 
  //查询出所有的用户	
  List<User> findAllUser(QueryVo qv);
  //查询出所有的正常登陆的用户
  List<User> findAllOkUser(QueryVo qv);
  //查询出所有的被封禁的用户
  List<User> findAllNotOkUser(QueryVo qv);
  //根据id查询单个用户
  User findUserById(int id);
  //更新用户信息
  void updateUser(User user);
  //查询出某个用户的状态
  Integer findUserStatusById(int id);
  //批量封禁
  void changeStatusToNotOk(int[] array);
   //批量解禁
  void changeStatusToOk(int[] array);
  //模糊查询
  List<User> queryUserByKeyword(QueryVo qv);
  //用户名和密码查询拥护
  User findUserByUsernameAndPassword(String username, String password);
  //用户名查询用户
  User findUserByUsername(String username);
  
  
}
