package com.lrw.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
@Data
public class User implements Serializable {
	
   private Integer id; //用户编号
   private String username; //用户名
   private String password; //用户密码
   private String lastLoginTime; //上次登录时间
   private String level;//会员等级
   private String birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//注册日期
   private String status;//状态，0 未锁定，1锁定状态
   private String email;//邮箱
   private int sex; //性别, 0 女 1 男 2 保密
   private String descrition;//用户简介
   private String reason;//封禁理由
   private String lastUseName ;//上次使用的用户名
   
}
