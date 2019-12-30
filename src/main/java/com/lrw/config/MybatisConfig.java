//package com.lrw.config;
//
//import java.util.Properties;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.github.pagehelper.PageHelper;
//
//@Configuration
//public class MybatisConfig {
//	
//	/**
//	 * @Description:配置mybatis的分页插件pageHelper
//	 */
//	@Bean
//	public PageHelper pageHelper() {
//		PageHelper pageHelper = new PageHelper();
//		Properties properties = new Properties();
//		//分页参数合理化，默认false禁用
//        //启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
//        //禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
//		properties.setProperty("reasonable", "true");
//		//配置oracle数据库的方言,4.0.0以后版本可以不设置该参数
//		//properties.setProperty("dialect", "oracle"); 
//		pageHelper.setProperties(properties);
//		return pageHelper;
//	}
//}
