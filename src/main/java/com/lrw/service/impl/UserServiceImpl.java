package com.lrw.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrw.mapper.RoleMapper;
import com.lrw.mapper.UserMapper;
import com.lrw.util.Md5Utils;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.User;
@Service
public class UserServiceImpl implements com.lrw.service.UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public void createUser(User user) {
		userMapper.createUser(user);
	}

	@Override
	public PageListRes findAllUser(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<User> list = this.userMapper.findAllUser(qv);
		if(null!=list&&list.size()>0) {
			Iterator<User> iterator = list.iterator();
			while(iterator.hasNext()) {
				User user = iterator.next();
				//查询用户是否只有普通教师权限
				List<Map> adminOrSuperAdmin = this.roleMapper.isAdminOrSuperAdmin(user.getId());
				if(adminOrSuperAdmin!=null&&adminOrSuperAdmin.size()>0) {
					list.remove(user);
				}
			}
		}
		PageInfo<User> page = new PageInfo<User>(list);
		res.setData(page.getList());
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
		if(null!=list&&list.size()>0) {
			Iterator<User> iterator = list.iterator();
			while(iterator.hasNext()) {
				User user = iterator.next();
				//查询用户是否只有普通教师权限
				List<Map> adminOrSuperAdmin = this.roleMapper.isAdminOrSuperAdmin(user.getId());
				if(adminOrSuperAdmin!=null&&adminOrSuperAdmin.size()>0) {
					iterator.remove();
				}
			}
		}
		PageInfo<User> page = new PageInfo<User>(list);
		res.setData(list);
		res.setTotal(Long.parseLong(String.valueOf(list.size())));
		res.setNumber(Long.parseLong(String.valueOf(list.size())));
		res.setCount(Long.parseLong(String.valueOf(list.size())));
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

	@Override
	public void updateUserLastLoginTime(String username,String time) {
		userMapper.updateUserLastLoginTime(username,time);
	}

	@Override
	public Boolean changePwd(String username, String oldPwd) {
		//将传过来的密码加密
		oldPwd = Md5Utils.MD5Encode(oldPwd);
		User user = userMapper.findUserByUsernameAndPassword(username, oldPwd);
		return user==null;
	}

	@Override
	public void changePassword(String username, String password) {
		userMapper.changePassword(username,password);
	}

	@Override
	public boolean isRepateUser(String username) {
		return userMapper.isRepateUser(username)==null;
	}

	@Override
	public PageListRes findAllAdmin(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<User> list = this.userMapper.findAllAdmin(qv);
		if(null!=list&&list.size()>0) {
			Iterator<User> iterator = list.iterator();
			while(iterator.hasNext()) {
				User user = iterator.next();
				//查询用户是否只有普通教师权限
				List<Map> adminOrSuperAdmin = this.roleMapper.isSuperAdmin(user.getId());
				if(adminOrSuperAdmin!=null&&adminOrSuperAdmin.size()>0) {
					iterator.remove();
				}
			}
		}
		PageInfo<User> page = new PageInfo<User>(list);
		res.setData(list);
		res.setTotal(Long.parseLong(String.valueOf(list.size())));
		res.setNumber(Long.parseLong(String.valueOf(list.size())));
		res.setCount(Long.parseLong(String.valueOf(list.size())));
		return res;
	}

}
