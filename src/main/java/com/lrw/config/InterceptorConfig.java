package com.lrw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.lrw.interceptor.RequestInterceptor;
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
	@Autowired
	private RequestInterceptor requestInterceptor;
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		
		//addPathPatterns 设置要拦截的路径
		//excludePathPatterns 设置不拦截的路径
		
		registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
       // .excludePathPatterns("/stuInfo/getAllStuInfoA","/account/register");    

		super.addInterceptors(registry);
	}

}
