package com.lrw.interceptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
/**
 * 通过拦截器，实现日志功能
 * @author Administrator
 *
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {
	
	private double starttime;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //获取登录日志
		
		return true;
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		double endTime = System.currentTimeMillis();
//		//执行时间
//		System.out.println("执行时间"+(endTime-starttime)/1000+"秒");
//		// TODO Auto-generated method stub
//		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
}
