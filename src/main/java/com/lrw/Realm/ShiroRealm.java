package com.lrw.Realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;



public class ShiroRealm extends  AuthorizingRealm{
    /**
     * 身份认证
     */
  	@Override
  	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken  authenticationToken) throws AuthenticationException {
  		/* System.out.println("身份认证--------------------------------");
  		 UsernamePasswordToken token =(UsernamePasswordToken) authenticationToken;//强转
  		 String username = (String)token.getPrincipal();
  		 AdminService adminservice =SpringBeanFactoryUtils.getBean("adminservice", AdminService.class);
  		 Admin vo = adminservice.findAdminByName(username);//通过用户名找用户
  		 if(vo==null) {
  			 throw new UnknownAccountException();
  		 }else{
  			 ByteSource credentialsSalt = ByteSource.Util.bytes(vo.getName());//盐值
  	  		 SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(vo,vo.getPassword(),credentialsSalt,"123");
  	  		 return authenticationInfo; 
  		 }*/
  		return null;
  	}
    
    
    /**
	  * 权限认证
	  */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
       
			return null;
	} 

}
