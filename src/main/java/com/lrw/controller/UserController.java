package com.lrw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.UserService;
import com.lrw.util.Md5Utils;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.User;

@RestController
@RequestMapping("/User")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {
    @Autowired
	private UserService userServiceImpl;
	
    
    
	@PostMapping("/addUser")
	public ReturnRes addUser(User user) {
		String password = Md5Utils.MD5Encode(user.getPassword());
		user.setPassword(password);
		ReturnRes res = new ReturnRes();
		try {
			userServiceImpl.createUser(user);
			res.setSuccess(true);
			res.setMsg("添加成功");
		}catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMsg("添加失败");
		}
		return res;
	}
	
	@GetMapping("/queryAllUser")
	public PageListRes queryAllUser(QueryVo qv){
		PageListRes queryUser =null;
		try {
			queryUser= userServiceImpl.findAllUser(qv);
		}catch (Exception e) {
			queryUser = new PageListRes();
			e.printStackTrace();
		}
		return queryUser;
	} 
	@GetMapping("/queryAllOkUser")
	public PageListRes queryAllOkUser(QueryVo qv) {
		PageListRes queryUser =null;
		try {
			queryUser= userServiceImpl.findAllOkUser(qv);
		}catch (Exception e) {
			queryUser = new PageListRes();
			e.printStackTrace();
		}
		return queryUser;
	}
	@GetMapping("/queryAllNotOkUser")
	public PageListRes queryAllNotOkUser(QueryVo qv) {
		PageListRes queryUser =null;
		try {
			queryUser= userServiceImpl.findAllNotOkUser(qv);
		}catch (Exception e) {
			queryUser = new PageListRes();
			e.printStackTrace();
		}
		return queryUser;
	}
	@PostMapping("/editUser")
	public ReturnRes editUser(User user) {
		ReturnRes res = new ReturnRes();
		int id = user.getId();
		User oldUser = userServiceImpl.findUserById(id);
		if(oldUser==null) {
			res.setMsg("系统异常，请稍后再试");
			return res;
		}else {
			try {
				userServiceImpl.updateUser(user);
				res.setMsg("更新成功");
			}catch(Exception e) {
				e.printStackTrace();
				res.setMsg("更新失败，请稍后再试");
			}
		}
		return res;
	}
	@PostMapping("/changeStatus")
	public ReturnRes changeStatus(@RequestParam("array")int[] array) {
		ReturnRes res = new ReturnRes();
		if(array==null||array.length==0) {
			res.setMsg("系统异常，请稍后再试");
			res.setSuccess(false);
			return res;
		}else {
			Integer status = userServiceImpl.findUserStatusById(array[0]);
			if(status==null) {
				res.setMsg("系统异常，请稍后再试");
				res.setSuccess(false);
				return res;
			}else {//存在状态
				if (status==1) {//被封禁
					userServiceImpl.changeStatusToOk(array);
					res.setMsg("操作成功");
					res.setSuccess(true);
				}else if(status==0){//处于解封状态
					userServiceImpl.changeStatusToNotOk(array);
					res.setSuccess(true);
					res.setMsg("操作成功");
				}
			}
		}
		return res;
	}
	
	
	@PostMapping("/queryUserByKeyword")
	public PageListRes queryUserByKeyword(QueryVo qv) {
		PageListRes queryUser =null;
		try {
			queryUser= userServiceImpl.queryUserByKeyword(qv);
		}catch (Exception e) {
			queryUser = new PageListRes();
			e.printStackTrace();
		}
		return queryUser;
	}
    //检查是否能够修改密码
	@PostMapping("/changePwd")
	public ReturnRes checkPwd(@RequestParam("username")String username,@RequestParam("oldPwd")String oldPwd) {
		ReturnRes res = new ReturnRes();
		try {
		    //
			boolean flag = !userServiceImpl.changePwd(username,oldPwd);
			if(flag) {
				res.setSuccess(true);
			}else {
				res.setSuccess(false);
				res.setMsg("输入密码错误，请重新输入");
			}
		}catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(true);
			res.setMsg("系统异常，请刷新后重试");
		}
		return res ;
	}
	@PostMapping("/changePassword")
	public ReturnRes changePassword(@RequestParam("username")String username,@RequestParam("password")String password) {
		password = Md5Utils.MD5Encode(password);//将新密码重新加密
		ReturnRes res = new ReturnRes();
		try {
			userServiceImpl.changePassword(username,password);
			res.setMsg("更改成功,请重新登录");
			res.setSuccess(true);
			res.setUrl("");
		}catch (Exception e) {
          e.printStackTrace();
          res.setMsg("系统异常，请刷新后再试");
          res.setSuccess(false);
		}
		return res;
	}
	
	@PostMapping("/findUserByUsername")
	public User findUserByUsername(@RequestParam("username")String username) {
		if(!username.isEmpty()) {
			User user = userServiceImpl.findUserByUsername(username);
			return user;
		}
		return null;
	}
	
}
