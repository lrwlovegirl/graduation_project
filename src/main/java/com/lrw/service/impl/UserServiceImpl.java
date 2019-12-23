package com.lrw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrw.mapper.UserMapper;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.User;
@Service
public class UserServiceImpl implements com.lrw.service.UserService {

	@Autowired
	private UserMapper userMapper;
	
	
	@Override
	public void createUser(User user) {
		userMapper.createUser(user);
	}

	@Override
	public PageListRes findAllUser(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<User> list = this.userMapper.findAllUser(qv);
		PageInfo<User> page = new PageInfo<User>(list);
		res.setData(list);
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public PageListRes findAllOkUser(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<User> list = this.userMapper.findAllOkUser(qv);
		PageInfo<User> page = new PageInfo<User>(list);
		res.setData(list);
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public PageListRes findAllNotOkUser(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<User> list = this.userMapper.findAllNotOkUser(qv);
		PageInfo<User> page = new PageInfo<User>(list);
		res.setData(list);
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public User findUserById(int id) {
	  return userMapper.findUserById(id);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public Integer findUserStatusById(int id) {
		return userMapper.findUserStatusById(id);
	}

	@Override
	public void changeStatusToOk(int[] array) {
		userMapper.changeStatusToOk(array);
		
	}

	@Override
	public void changeStatusToNotOk(int[] array) {
		// TODO Auto-generated method stub
		userMapper.changeStatusToNotOk(array);
	}

	@Override
	public PageListRes queryUserByKeyword(QueryVo qv) {
		
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<User> list = this.userMapper.queryUserByKeyword(qv);
		PageInfo<User> page = new PageInfo<User>(list);
		res.setData(list);
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return userMapper.findUserByUsernameAndPassword(username, password);
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userMapper.findUserByUsername(username);
	}

}
