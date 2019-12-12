package com.lrw.config;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.lrw.Realm.ShiroRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

//shiro的配置
@Configuration
public class ShiroConfig {
    @Bean("shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shirofilterfactorybean= new ShiroFilterFactoryBean();
		//必须设置secutityManger
		shirofilterfactorybean.setSecurityManager(securityManager);
		//setLoginUrl如果不设置值，默认会自动寻找根目录下的/login.jsp或者/login的映射
		shirofilterfactorybean.setLoginUrl("/adminLogin");
		//设置无权限时跳转的url
	   	shirofilterfactorybean.setUnauthorizedUrl("/noPerssion");
	   
	   	//设置拦截器，
	    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		//游客，不需要登录
	    filterChainDefinitionMap.put("/guest/**", "anon");
        //登录接口不需要认证
        filterChainDefinitionMap.put("/login", "anon");
        //注册接口不需要认证
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/Manger", "anon");
        //用户，需要登录
        filterChainDefinitionMap.put("/user/**", "roles[users]");
        
        //管理员
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        filterChainDefinitionMap.put("/orders", "authc");
        //静态资源都不需要拦截
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        //剩下的页面都需要拦截
        filterChainDefinitionMap.put("/**", "anon");
        //将拦截器注入ShiroFilterFactoryBean
        shirofilterfactorybean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shirofilterfactorybean;
	}
    
    //注入securityManger
    @Bean
    public DefaultWebSecurityManager securityManager() {
    	DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    	//设置realm
    	securityManager.setRealm(this.shiroRealm());
    	
    	return securityManager;
    }
    /**
     * 自己的realm
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
    	ShiroRealm shiroRealm = new ShiroRealm();
    	shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    	return shiroRealm;
    }
    
    //密码加密
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用md5算法;
        hashedCredentialsMatcher.setHashIterations(1024);//加密1024次
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//是否存储为16进制
        return hashedCredentialsMatcher;
    }
   

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return cacheManager;
    }


  /**
   *  开启shiro aop注解支持.
   *  使用代理方式;所以需要开启代码支持;否则@RequiresRoles等注解无法生效
   * @param securityManager
   * @return
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
      AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
      authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
      return authorizationAttributeSourceAdvisor;
  }

  /**
   * Shiro生命周期处理器
   * @return
   */
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
      return new LifecycleBeanPostProcessor();
  }

  @Bean
  public ShiroDialect shiroDialect(){
      return new ShiroDialect();
  }

  
  /**
   * 自动创建代理
   * @return
   */
  @Bean
  public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
      DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
      advisorAutoProxyCreator.setProxyTargetClass(true);
      return advisorAutoProxyCreator;
  }
   
    
    
    
}

