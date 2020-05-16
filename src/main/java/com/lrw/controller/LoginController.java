package com.lrw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.UserService;
import com.lrw.util.CookieUtils;
import com.lrw.util.GetTime;
import com.lrw.util.Md5Utils;
import com.lrw.util.ReturnRes;
import com.lrw.vo.User;

@RestController
@RequestMapping("/login")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class LoginController {
	
	@Autowired
	private UserService userServiceImpl;
	//还要将这个用户对应的菜单返回去
    @PostMapping("/userLogin")
	public ReturnRes login(String username,String password) {
    	ReturnRes res = new ReturnRes();
    	try {
    		password = Md5Utils.MD5Encode(password);
    		User user = userServiceImpl.findUserByUsernameAndPassword(username, password);
    		if(user!=null) {
    			if("1".equals(user.getStatus())) {
    				String nowtime = GetTime.getFormatTime();//获取当前时间
    				userServiceImpl.updateUserLastLoginTime(username, nowtime);
    				res.setMsg("登录成功");
        			res.setSuccess(true);
        			res.setUrl("../../index.html");
    			}else {
    				res.setSuccess(false);
        			res.setMsg("您已被封禁，请联系管理员");
        			res.setUrl("#");
    			}
    		}else {
    			res.setSuccess(false);
    			res.setMsg("密码或用户名错误，请重试");
    			res.setUrl("#");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMsg("系统错误，请稍后再试");
		}
		return res;
	}
	
	
	
}
